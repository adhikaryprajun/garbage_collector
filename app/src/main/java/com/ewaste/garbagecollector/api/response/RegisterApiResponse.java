package com.ewaste.garbagecollector.api.response;


import com.ewaste.garbagecollector.api.models.ApiResponse;

public class RegisterApiResponse {

    private ApiResponse response;

    public RegisterApiResponse(ApiResponse response) {
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }

}
