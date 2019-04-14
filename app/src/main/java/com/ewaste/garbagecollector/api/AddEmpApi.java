package com.ewaste.garbagecollector.api;

import android.content.Context;
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
import com.ewaste.garbagecollector.api.models.User;
import com.ewaste.garbagecollector.api.response.AddEmpApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddEmpApi implements ApiProtocol {
    //    PRODUCTION : http://goodrichpestcontrol.com/ewaste/api/login.php
//    DEVELOPMENT : http://192.168.64.2/ewaste/api/login.php
    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/merchant/add_emp.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_SESSION_HASH = "session_hash";
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD = "password";
    private final String KEY_PHONE = "phone";
    private final String KEY_ADDRESS = "address";
    private final String KEY_NAME = "name";

    private AddEmpApiResponse addEmpApiResponse ;

    private RequestQueue queue;

    private String session_hash = "";
    private String email = "";
    private String password= "";
    private String phone = "";
    private String address = "";
    private String name = "";


    private Context context;
    private TextView tv;

    public AddEmpApi(Context context, String session_hash,String email,String password,String phone,String address,String name, TextView tv) {
        this.context = context;
        this.tv = tv;
        this.session_hash = session_hash;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.name = name;

    }

    public void makeRequest() {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(METHOD, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ADD_EMP API", response);
                        // TODO Success Parsing
                       // parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ADD_EMP API", "Failed "+ error.getMessage());
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
                params.put(KEY_SESSION_HASH,session_hash);
                params.put(KEY_EMAIL,email);
                params.put(KEY_PHONE,phone);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_ADDRESS,address);
                params.put(KEY_NAME,name);
                Log.d("session_hash:"+session_hash, "Hash value");
                Log.d("email:"+email, "username");
                Log.d("password:"+password, "password");
                Log.d("phone:"+phone, "phone");
                Log.d("address:"+address, "address");
                Log.d("name:"+name, "name");
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
            if (r.isValid()) {
                Log.d("ADD_EMP", "VALID SESSION");
                //                {"code":"200","message":"Success",
//                  "employee":[{"id":"8","email":"vinod@gmail.com","phone":"66565165","password":"12345","address":"scsdcasd ","type":"2","company":null,"merchant_id":"2","name":"lbkdfmbkl"}]}
                JSONArray employeesArray = obj.getJSONArray("employee");
                ArrayList<User> employeessss = new ArrayList<>();
                int length = employeesArray.length();
                for (int i=0;i<length;i++) {
                    JSONObject emp1 = (JSONObject) employeesArray.get(i);
                    User u = new User(emp1);
                    employeessss.add(u);

                }
                addEmpApiResponse=new AddEmpApiResponse(r,employeessss);
            } else {
                addEmpApiResponse = new AddEmpApiResponse(r, null);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("ADDEMP",  "Parsing error");
        }
        handleResponse();
    }

    @Override
    public void handleResponse() {
        if(addEmpApiResponse.getResponse().isValid()) {
            //Intent intent = new Intent(context, EmployeeActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // context.startActivity(intent);
        } else {
            Log.d("ADDEMP", "Not able to add employee");
        }

    }


}
