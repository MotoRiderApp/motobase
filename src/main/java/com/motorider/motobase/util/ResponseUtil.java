package com.motorider.motobase.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity getSuccessResponse(Object bodyRespone) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bodyRespone);
    }

    public static ResponseEntity getFailureResponse(Object bodyResponse, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(bodyResponse);
    }
}
