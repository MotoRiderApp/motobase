package com.motorider.motobase.controller;

import com.mongodb.WriteResult;
import com.motorider.motobase.dto.MotoDTO;
import com.motorider.motobase.dto.RecordDTO;
import com.motorider.motobase.entity.Moto;
import com.motorider.motobase.entity.MotoBase;
import com.motorider.motobase.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.motorider.motobase.constant.MotoConstant.*;
import static com.motorider.motobase.constant.UserConstant.ID_USER;

@RestController
public class MotoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = INSERT_MOTO, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WriteResult insertMoto(@RequestBody MotoDTO motoDTO, String idUser) {
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

        WriteResult writeResult = mongoTemplate.updateFirst(query, update, MotoBase.class);

        return writeResult;

    }

    @RequestMapping(value = INSERT_RECORD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WriteResult insertRecord(@RequestBody RecordDTO recordDTO, String idUser, String motoName) {
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
        List<MotoBase> motoBaseList = mongoTemplate.find(query, MotoBase.class);
        Update update = new Update();
        update.push("motos.$.records", record);

        WriteResult writeResult = mongoTemplate.updateFirst(query, update, MotoBase.class);

        return writeResult;

    }
}
