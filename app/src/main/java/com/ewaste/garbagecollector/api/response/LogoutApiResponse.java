package com.ewaste.garbagecollector.api.response;

import com.ewaste.garbagecollector.api.models.ApiResponse;

public class LogoutApiResponse {

    private ApiResponse response;
    public LogoutApiResponse(ApiResponse response) {
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }
}
