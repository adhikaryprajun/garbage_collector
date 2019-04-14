package com.ewaste.garbagecollector.api.response;

import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.models.Session;
import com.ewaste.garbagecollector.api.models.User;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class LoginApiResponse {

    private ApiResponse response;
    private Session session;
    private User user;

    public LoginApiResponse(ApiResponse response, Session session, User user) {
        this.response = response;
        this.session = session;
        this.user = user;
    }

    public ApiResponse getResponse() {
        return response;
    }

//    public void setResponse(ApiResponse response) {
//        this.response = response;
//    }

    public Session getSession() {
        return session;
    }

//    public void setSession(Session session) {
//        this.session = session;
//    }

    public User getUser() {
        return user;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }
}
