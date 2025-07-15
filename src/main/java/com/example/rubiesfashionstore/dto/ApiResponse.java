package com.example.rubiesfashionstore.dto;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse {
    private String message;
    private Object data;
    private String errorMessage;

    public ApiResponse(String message, Object data, String errorMessage) {
        this.message = message;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
