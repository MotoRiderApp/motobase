package com.motorider.motobase.controller;

import com.motorider.motobase.entity.MotoRider;
import com.motorider.motobase.repository.MotoRiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.motorider.motobase.constant.MotoRiderConstant.FIND_ALL;

@RestController
public class MotoRiderController {

    @Autowired
    private MotoRiderRepository motoRiderRepository;

    @RequestMapping(value = FIND_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MotoRider>> findAll() {
        return motoRiderRepository.findAll();
    }
}
