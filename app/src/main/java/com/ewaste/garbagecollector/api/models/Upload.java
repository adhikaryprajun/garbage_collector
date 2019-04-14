package com.ewaste.garbagecollector.api.models;

import org.json.JSONObject;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class Upload {

    private String id;
    private String picture;
    private String userId;
    private String uploadTime;
    private String description;
    private String status;
    private String collectorId;
    private String campaignId;
    private String type;

    public Upload(String id, String picture, String userId, String uploadTime, String description, String status, String collectorId, String campaignId, String type) {
        this.id = id;
        this.picture = picture;
        this.userId = userId;
        this.uploadTime = uploadTime;
        this.description = description;
        this.status = status;
        this.collectorId = collectorId;
        this.campaignId = campaignId;
        this.type = type;
    }

    public Upload(JSONObject upload) {
        try {
            this.id = upload.getString("id");
            this.picture = upload.getString("picture");
            this.userId = upload.getString("user_id");
            this.uploadTime = upload.getString("upload_time");
            this.description = upload.getString("description");
            this.status = upload.getString("status");
            if(upload.getString("collector_id")!=null) {
                this.collectorId = upload.getString("collector_id");
            }
            if(upload.getString("campaign_id")!=null) {
                this.campaignId = upload.getString("campaign_id");
            }
            this.type = upload.getString("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
