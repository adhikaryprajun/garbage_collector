package com.ewaste.garbagecollector.api.models;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class User {

    private String id;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String type;
    private String company;
    private String merchantId;
    private String name;


    public User(String id, String email, String phone, String password, String address, String type, String company, String merchantId, String name) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.type = type;
        this.company = company;
        this.merchantId = merchantId;
        this.name = name;
    }

    public User(JSONObject user) {
        try {
            this.id = user.getString("id");
            this.email = user.getString("email");
            this.phone = user.getString("phone");
            this.password = user.getString("password");
            this.address = user.getString("address");
            this.type = user.getString("type");
            this.company = user.getString("company");
            this.merchantId = user.getString("merchant_id");
            this.name = user.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LOGIN",  "Parsing error");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
