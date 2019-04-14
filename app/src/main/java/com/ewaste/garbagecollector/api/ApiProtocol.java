package com.ewaste.garbagecollector.api;

public interface ApiProtocol {

    void makeRequest();
    void parseResponse(String response);
    void handleResponse();
}
