package com.ewaste.garbagecollector.api;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ewaste.garbagecollector.adapter.UploadListAdapter;
import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.models.Upload;
import com.ewaste.garbagecollector.api.models.User;
import com.ewaste.garbagecollector.api.response.UploadStatusApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadStatusApi implements ApiProtocol {
    //    PRODUCTION : http://goodrichpestcontrol.com/ewaste/api/login.php
//    DEVELOPMENT : http://192.168.64.2/ewaste/api/login.php
    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/upload_status.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_SESSION_HASH = "session_hash";
    private final String KEY_STATUS = "status";
    private final String KEY_UPLOAD_ID = "upload_id";

    private UploadStatusApiResponse uploadStatusApiResponse;
    private RequestQueue queue;

    private String session_hash = "";
    private String status= "";
    private String upload_id = "";

    private Context context;
//    private UploadListAdapter adp;
    private ApiCallback apiCallback;

    public UploadStatusApi(Context context, String session_hash, String status, String upload_id, ApiCallback apiCallback) {
        this.context = context;
        this.session_hash = session_hash;
        this.status = status;
        this.upload_id = upload_id;
//        this.adp = adp;
        this.apiCallback = apiCallback;
    }

    public void makeRequest() {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(METHOD, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UPLOAD_STATUS API", response);
                        // TODO Success Parsing
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UPLOAD_STATUS API", "Failed "+ error.getMessage());
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
                params.put(KEY_STATUS,status);
                params.put(KEY_UPLOAD_ID,upload_id);
                Log.d("session_hash:"+session_hash, "session_hash");
                Log.d("status:"+status, "status");
                Log.d("upload_id:"+upload_id, "upload_id");
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
                Log.d("ADDEMP", "VALID SESSION");
                //                {"code":"200","message":"Success",
//                  "employee":[{"id":"8","email":"vinod@gmail.com","phone":"66565165","password":"12345","address":"scsdcasd ","type":"2","company":null,"merchant_id":"2","name":"lbkdfmbkl"}]}
                JSONArray employeesArray = obj.getJSONArray("updated");
                ArrayList<Upload> employeessss = new ArrayList<>();
                int length = employeesArray.length();
                for (int i=0;i<length;i++) {
                    JSONObject emp1 = (JSONObject) employeesArray.get(i);
                    Upload u = new Upload(emp1);
                    employeessss.add(u);

                }
                uploadStatusApiResponse = new UploadStatusApiResponse(r,employeessss);
                Log.d("ADDEMP", "PARSIN COMPLETED");
            } else {
                uploadStatusApiResponse = new UploadStatusApiResponse(r,null);
                Log.d("ADDEMP", "NO UPLOAD IN DB");
            }
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("ADDEMP",  "Parsing error");
        }
        handleResponse();
    }

    @Override
    public void handleResponse() {
        apiCallback.apiCompleted(uploadStatusApiResponse);
        Log.d("ADDEMP", "SIZE OF UPLOADS ARRAY " + uploadStatusApiResponse.getUploads().size());
//        adp.setUploads(uploadStatusApiResponse.getUploads());
//        adp.notifyDataSetChanged();
//        Log.d("UPLOAD STATUS", "CODE " + uploadStatusApiResponse.getResponse().getMessage());
//        Log.d("UPLOAD STATUS", "MESSAGE " + uploadStatusApiResponse.getResponse().getMessage());
    }


}
