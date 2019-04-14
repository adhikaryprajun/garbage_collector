package com.ewaste.garbagecollector.api.response;

import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.models.Upload;
import com.ewaste.garbagecollector.api.models.User;

import java.util.ArrayList;

public class UploadStatusApiResponse {

    private ApiResponse response;
    private ArrayList<Upload> uploads;

    public UploadStatusApiResponse(ApiResponse response, ArrayList<Upload> uploads) {
        this.response = response;
        this.uploads = uploads;
    }

    public ApiResponse getResponse() {
        return response;
    }

    public ArrayList<Upload> getUploads() {
        return uploads;
    }


    public ArrayList<Upload> getEmployeeActiveUploads(String userId) {
        int length = uploads.size();
        ArrayList<Upload> activeUploads = new ArrayList<>();
        for(int i=0;i<length;i++){
            Upload currentUpload = uploads.get(i);
            if((!currentUpload.getStatus().equals("3")) && currentUpload.getCollectorId().equals(userId)) {
                activeUploads.add(currentUpload);
            }
        }
        return activeUploads;
    }

    public ArrayList<Upload> getEmployeeClosedUploads(String userId) {
        int length = uploads.size();
        ArrayList<Upload> activeUploads = new ArrayList<>();
        for(int i=0;i<length;i++){
            Upload currentUpload = uploads.get(i);
            if((currentUpload.getStatus().equals("3")) && currentUpload.getUserId().equals(userId)) {
                activeUploads.add(currentUpload);
            }
        }
        return activeUploads;
    }

    public ArrayList<Upload> getAllActiveUploads() {
        int length = uploads.size();
        ArrayList<Upload> activeUploads = new ArrayList<>();
        for(int i=0;i<length;i++){
            Upload currentUpload = uploads.get(i);
            if(currentUpload.getStatus().equals("0")) {
                activeUploads.add(currentUpload);
            }
        }
        return activeUploads;

    }
}
