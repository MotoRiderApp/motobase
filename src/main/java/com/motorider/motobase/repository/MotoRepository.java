package com.motorider.motobase.repository;

import com.mongodb.WriteResult;
import com.motorider.motobase.dto.MotoDTO;
import com.motorider.motobase.dto.RecordDTO;
import com.motorider.motobase.entity.Moto;
import com.motorider.motobase.entity.MotoRider;
import com.motorider.motobase.entity.Record;
import com.motorider.motobase.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.motorider.motobase.constant.MotoConstant.MOTOS;
import static com.motorider.motobase.constant.MotoConstant.MOTOS_NAME;
import static com.motorider.motobase.constant.UserConstant.ID_USER;

@Repository
public class MotoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ResponseEntity<WriteResult> insertMoto(MotoDTO motoDTO, String idUser) {
        ResponseEntity response;
        try {
            Moto moto = Moto.builder()
                .name(motoDTO.getName())
                .brand(motoDTO.getBrand())
                .cc(motoDTO.getCc())
                .km(motoDTO.getKm())
                .records(new ArrayList<>())
                .build();

            Query query = new Query(Criteria.where(ID_USER).is(idUser));
            Update update = new Update();
            update.push(MOTOS, moto);

            WriteResult writeResult = mongoTemplate.updateFirst(query, update, MotoRider.class);
            response = ResponseUtil.getSuccessResponse(writeResult);
        } catch (Exception e) {
            response = ResponseUtil.getFailureResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<WriteResult> insertRecord(RecordDTO recordDTO, String idUser, String motoName) {
        ResponseEntity response;
        try {
            Record record = Record.builder()
                    .description(recordDTO.getDescription())
                    .currentKm(recordDTO.getCurrentKm())
                    .value(recordDTO.getValue())
                    .date(recordDTO.getDate())
                    .notification(recordDTO.getNotification())
                    .category(recordDTO.getCategory())
                    .build();

            Query query = new Query(Criteria.where(ID_USER).is(idUser)
                    .and(MOTOS_NAME).is(motoName));
            List<MotoRider> motoRiderList = mongoTemplate.find(query, MotoRider.class);
            Update update = new Update();
            update.push("motos.$.records", record);

            WriteResult writeResult = mongoTemplate.updateFirst(query, update, MotoRider.class);
            response = ResponseUtil.getSuccessResponse(writeResult);
        } catch (Exception e) {
            response = ResponseUtil.getFailureResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }
}
