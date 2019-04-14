package com.ewaste.garbagecollector.api.models;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class ApiResponse {

    public static final String KEY_CODE = "code";
    public static final String KEY_MESSAGE = "message";

    private String code;
    private String message;

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        if(code.equals("400")) return false; else return true;
    }
}
