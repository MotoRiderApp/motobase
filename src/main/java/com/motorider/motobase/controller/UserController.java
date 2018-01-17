package com.motorider.motobase.controller;

import com.motorider.motobase.dto.UserDTO;
import com.motorider.motobase.entity.MotoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.motorider.motobase.constant.UserConstant.INSERT_USER;

@RestController
public class UserController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = INSERT_USER, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MotoBase insertUser(@RequestBody UserDTO user) {
        MotoBase motoBase = MotoBase.builder()
                .name(user.getName())
                .idSocialNetwork(user.getIdSocialNetwork())
                .idUser(user.getIdUser())
                .email(user.getEmail())
                .motos(new ArrayList<>())
                .build();
        mongoTemplate.insert(motoBase);

        return motoBase;
    }
}
