package com.ewaste.garbagecollector.api.models;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class Session implements Serializable {

    private String userId;
    private String hashValue;
    private int status;

    public Session() {

    }

    public Session(String userId, String hashValue, int status) {
        this.userId = userId;
        this.hashValue = hashValue;
        this.status = status;
    }

    public Session(JSONObject session) {
        try {
            this.userId = session.getString("user_id");
            this.hashValue = session.getString("hashvalue");
            this.status = session.getInt("status");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LOGIN",  "Parsing error");
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

