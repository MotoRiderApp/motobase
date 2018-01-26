package com.motorider.motobase.repository;

import com.motorider.motobase.dto.UserDTO;
import com.motorider.motobase.entity.MotoRider;
import com.motorider.motobase.enums.TypeAccount;
import com.motorider.motobase.exception.DataBaseResultNotFoundException;
import com.motorider.motobase.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static com.motorider.motobase.constant.UserConstant.EMAIL;
import static com.motorider.motobase.constant.UserConstant.TYPE_ACCOUNT;

@Repository
public class UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ResponseEntity<MotoRider> insertUser(UserDTO user) {
        ResponseEntity response;
        try {
            MotoRider motoRider = MotoRider.builder()
                    .name(user.getName())
                    .idSocialNetwork(user.getIdSocialNetwork())
                    .idUser(user.getIdUser())
                    .email(user.getEmail())
                    .motos(new ArrayList<>())
                    .build();
            mongoTemplate.insert(motoRider);
            response = ResponseUtil.getSuccessResponse(motoRider);
        } catch (Exception e) {
            response = ResponseUtil.getFailureResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<UserDTO> findByEmail(String email, TypeAccount typeAccount) throws DataBaseResultNotFoundException {
        ResponseEntity response;
        try {
            UserDTO user;
            MotoRider motoRider;
            Query query = new Query(Criteria.where(EMAIL).is(email).and(TYPE_ACCOUNT).is(typeAccount.name()));

            motoRider = mongoTemplate.findOne(query, MotoRider.class);
            user = UserDTO.builder()
                    .idUser(motoRider.getIdUser())
                    .email(motoRider.getEmail())
                    .idSocialNetwork(motoRider.getIdSocialNetwork())
                    .typeAccount(motoRider.getTypeAccount())
                    .build();
            response = ResponseUtil.getSuccessResponse(user);
        } catch (Exception e) {
            response = ResponseUtil.getFailureResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }


}
