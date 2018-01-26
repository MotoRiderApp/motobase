package com.motorider.motobase.repository;

import com.motorider.motobase.entity.MotoRider;
import com.motorider.motobase.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotoRiderRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ResponseEntity<List<MotoRider>> findAll() {
        ResponseEntity response;
        try {
            List<MotoRider> motoRiderList = mongoTemplate.findAll(MotoRider.class);
            response = ResponseUtil.getSuccessResponse(motoRiderList);
        } catch (Exception e) {
            response = ResponseUtil.getFailureResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
