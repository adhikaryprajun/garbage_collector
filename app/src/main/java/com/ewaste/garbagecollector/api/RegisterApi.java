package com.ewaste.garbagecollector.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.response.RegisterApiResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterApi implements ApiProtocol{
    //    PRODUCTION : http://goodrichpestcontrol.com/ewaste/api/login.php
//    DEVELOPMENT : http://192.168.64.2/ewaste/api/login.php
    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/register.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD = "password";
    private final String KEY_PHONE = "phone";
    private final String KEY_ADDRESS = "address";
    private final String KEY_TYPE = "type";
    private final String KEY_NAME = "name";
    private final String KEY_COMPANY = "company";
    private final String KEY_MERCHANT_ID = "merchant_id";

    private RequestQueue queue;

    private String email = "";
    private String password= "";
    private String phone = "";
    private String address = "";
    private String type = "";
    private String name = "";
    private String company = "";
    private String merchant_id = "";

    private RegisterApiResponse registerApiResponse;

    private Context context;
    private TextView tv;

    public RegisterApi(Context context, String email,String password,String phone,String address,String type,String name,String company,String merchant_id, TextView tv) {
        this.context = context;
        this.tv = tv;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.name = name;
        this.company = company;
        this.merchant_id = merchant_id;
    }

    public void makeRequest() {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(METHOD, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REGISTER API", response);
                        // TODO Success Parsing
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REGISTER API", "Failed "+ error.getMessage());
                // TODO Failed
                tv.setText(error.getMessage());
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_PHONE,phone);
                params.put(KEY_ADDRESS,address);
                params.put(KEY_TYPE,type);
                params.put(KEY_NAME,name);
                if(type.equals("1")) {
                    params.put(KEY_COMPANY, company);
                }
                if(type.equals("2")) {
                    params.put(KEY_MERCHANT_ID, merchant_id);
                }
                Log.d("email:"+email, "username");
                Log.d("password:"+password, "password");
                Log.d("phone:"+phone, "phone");
                Log.d("address:"+address, "address");
                Log.d("type:"+type, "type");
                Log.d("name:"+name, "name");
                Log.d("company:"+company, "company");
                Log.d("merchant_id:"+merchant_id, "merchant_id");
                return params;
            }
        };

        queue.add(stringRequest);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                queue.getCache().clear();
            }
        });
    }

    @Override
    public void parseResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String code = obj.getString(ApiResponse.KEY_CODE);
            String message = obj.getString(ApiResponse.KEY_MESSAGE);
            ApiResponse r = new ApiResponse(code, message);

            registerApiResponse = new RegisterApiResponse(r);
            } catch(Exception e) {
            e.printStackTrace();
            Log.d("REGISTER",  "Parsing error");
        }
        handleResponse();

    }

    @Override
    public void handleResponse() {
        if(registerApiResponse.getResponse().isValid()) {
            //Intent intent = new Intent(context, EmployeeActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // context.startActivity(intent);
        } else {
            Log.d("REGISTER", "Not able to register");
        }
    }


}
