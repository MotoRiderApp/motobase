package com.motorider.motobase.controller;

import com.mongodb.WriteResult;
import com.motorider.motobase.dto.MotoDTO;
import com.motorider.motobase.dto.RecordDTO;
import com.motorider.motobase.entity.Moto;
import com.motorider.motobase.entity.MotoRider;
import com.motorider.motobase.entity.Record;
import com.motorider.motobase.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private MotoRepository motoRepository;

    @RequestMapping(value = INSERT_MOTO, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WriteResult> insertMoto(@RequestBody MotoDTO motoDTO, String idUser) {
        return motoRepository.insertMoto(motoDTO, idUser);

    }

    @RequestMapping(value = INSERT_RECORD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WriteResult> insertRecord(@RequestBody RecordDTO recordDTO, String idUser, String motoName) {
        return motoRepository.insertRecord(recordDTO, idUser, motoName);
    }
}
