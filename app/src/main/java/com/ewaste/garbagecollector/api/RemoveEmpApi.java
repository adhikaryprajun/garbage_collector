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
import com.ewaste.garbagecollector.api.response.RemoveEmpApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemoveEmpApi implements ApiProtocol{
    //    PRODUCTION : http://goodrichpestcontrol.com/ewaste/api/login.php
//    DEVELOPMENT : http://192.168.64.2/ewaste/api/login.php
    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/merchant/remove_emp.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_SESSION_HASH = "session_hash";
    private final String KEY_EMP_ID = "emp_id";
    private final String KEY_TYPE = "type";



    private RequestQueue queue;

    private String session_hash = "";
    private String emp_id = "";
    private String type= "";

    RemoveEmpApiResponse removeEmpApiResponse;

    private Context context;
    private TextView tv;

    public RemoveEmpApi(Context context, String session_hash,String emp_id,String type, TextView tv) {
        this.context = context;
        this.tv = tv;
        this.session_hash = session_hash;
        this.emp_id = emp_id;
        this.type= type;


    }

    public void makeRequest() {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(METHOD, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REMOVE_EMP API", response);
                        // TODO Success Parsing
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REMOVE_EMP API", "Failed "+ error.getMessage());
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
                params.put(KEY_EMP_ID,emp_id);
                params.put(KEY_TYPE,type);

                Log.d("session_hash:"+session_hash, "Hash value");
                Log.d("emp_id:"+emp_id, "employee id");
                Log.d("type:"+type, "type");
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
                Log.d("REMOVE_EMP", "VALID SESSION");
                //                {"code":"200","message":"Success",
//                  "employee":[{"id":"8","email":"vinod@gmail.com","phone":"66565165","password":"12345","address":"scsdcasd ","type":"2","company":null,"merchant_id":"2","name":"lbkdfmbkl"}]}
                JSONArray employeesArray = obj.getJSONArray("employees");
                ArrayList<User> employeessss = new ArrayList<>();
                int length = employeesArray.length();
                for (int i=0;i<length;i++) {
                    JSONObject emp1 = (JSONObject) employeesArray.get(i);
                    User u = new User(emp1);
                    employeessss.add(u);

                }
                removeEmpApiResponse=new RemoveEmpApiResponse(r,employeessss);
            } else {
                removeEmpApiResponse = new RemoveEmpApiResponse(r, null);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("REMOVE_EMP",  "Parsing error");
        }
        handleResponse();
    }

    @Override
    public void handleResponse() {

    }


}
