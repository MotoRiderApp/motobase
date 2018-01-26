package com.motorider.motobase.controller;

import com.motorider.motobase.dto.UserDTO;
import com.motorider.motobase.entity.MotoRider;
import com.motorider.motobase.enums.TypeAccount;
import com.motorider.motobase.exception.DataBaseResultNotFoundException;
import com.motorider.motobase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;

import static com.motorider.motobase.constant.UserConstant.EMAIL;
import static com.motorider.motobase.constant.UserConstant.INSERT_USER;
import static com.motorider.motobase.constant.UserConstant.TYPE_ACCOUNT;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = INSERT_USER, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MotoRider> insertUser(@RequestBody UserDTO user) {
        return userRepository.insertUser(user);
    }

    public ResponseEntity<UserDTO> findByEmail(String email, TypeAccount typeAccount) throws DataBaseResultNotFoundException {
        return userRepository.findByEmail(email, typeAccount);
    }
}
