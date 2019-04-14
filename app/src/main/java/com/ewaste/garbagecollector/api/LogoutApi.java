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
import com.ewaste.garbagecollector.EmployeeActivity;
import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.response.LogoutApiResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogoutApi implements ApiProtocol {

    //    PRODUCTION : http://goodrichpestcontrol.com/ewaste/api/login.php
    //    DEVELOPMENT : http://192.168.64.2/ewaste/api/login.php
    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/logout.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_SESSION_HASH = "session_hash";

    private RequestQueue queue;

    private String session_hash = "";

    private LogoutApiResponse logoutApiResponse;

    private Context context;

    public LogoutApi(Context context, String session_hash) {
        this.context = context;
        this.session_hash = session_hash;
    }

    public void makeRequest() {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(METHOD, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LOGOUT API", response);
                        // TODO Success Parsing
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGOUT API", "Failed "+ error.getMessage());
                // TODO Failed
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
                Log.d("session_hash:"+session_hash, "Hash value");
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

            logoutApiResponse = new LogoutApiResponse(r);
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("LOGOUT",  "Parsing error");
        }
        handleResponse();

    }

    @Override
    public void handleResponse() {
        if(logoutApiResponse.getResponse().isValid()) {
            ((EmployeeActivity)context).finish();
        } else {
            Log.d("LOGOUT", "Not able to logout");
        }
    }


}
