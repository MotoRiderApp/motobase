package com.motorider.motobase.exception;

import static com.motorider.motobase.constant.ExceptionsConstant.DATABASE_RESULT_NOT_FOUND_EXCEPTION_MESSAGE;

public class DataBaseResultNotFoundException extends Exception {

    public DataBaseResultNotFoundException() {
        super(DATABASE_RESULT_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}
