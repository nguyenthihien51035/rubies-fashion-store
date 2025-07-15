package com.example.rubiesfashionstore.exception;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class BusinessException extends RuntimeException {
    private String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
