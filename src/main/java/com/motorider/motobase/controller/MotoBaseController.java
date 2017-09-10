package com.motorider.motobase.controller;

import com.motorider.motobase.entity.Moto;
import com.motorider.motobase.entity.MotoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.motorider.motobase.constant.MotoBaseConstant.FIND_ALL;
import static com.motorider.motobase.constant.MotoBaseConstant.TEST;

@RestController
public class MotoBaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = TEST, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String test() {
        return "Hola MotoRiderApp";
    }

    @RequestMapping(value = FIND_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MotoBase> findAll() {
        List<MotoBase> customList = mongoTemplate.findAll(MotoBase.class);

        return customList;
    }
}
