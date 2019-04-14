package com.ewaste.garbagecollector.api.response;

import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.models.User;

import java.util.ArrayList;

public class RemoveEmpApiResponse {
    private ApiResponse response;
    private ArrayList<User> user;

    public RemoveEmpApiResponse(ApiResponse response, ArrayList<User> user) {
        this.response = response;
        this.user = user;
    }

    public ApiResponse getResponse() {
        return response;
    }

    public ArrayList<User> getUser() {
        return user;
    }
}
