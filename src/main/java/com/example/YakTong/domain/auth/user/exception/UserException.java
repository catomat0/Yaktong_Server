package com.example.YakTong.domain.auth.user.exception;

import com.example.YakTong.global.error.code.ErrorCode;
import com.example.YakTong.global.error.exception.CustomException;

public class UserException extends CustomException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
