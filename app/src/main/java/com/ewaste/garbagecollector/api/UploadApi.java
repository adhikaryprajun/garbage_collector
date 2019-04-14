package com.ewaste.garbagecollector.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ewaste.garbagecollector.EmployeeActivity;
import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.models.Session;
import com.ewaste.garbagecollector.api.models.User;
import com.ewaste.garbagecollector.api.response.LoginApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class UploadApi {

    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/?.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_ = "";
    private String p1;

    private Context context;

    private LoginApiResponse loginApiResponse;
    private final String KEY_SESSION = "session";
    private final String KEY_USER = "user";

    public UploadApi(Context context, String p1) {
        this.context = context;
        this.p1 = p1;
    }

    public void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(context);



        String url = "http://www.angga-ari.com/api/something/awesome";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

//                    if (status.equals(Constant.REQUEST_SUCCESS)) {
//                        // tell everybody you have succed upload image and post strings
//                        Log.i("Messsage", message);
//                    } else {
//                        Log.i("Unexpected", message);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
//                params.put("name", mNameInput.getText().toString());
//                params.put("location", mLocationInput.getText().toString());
//                params.put("about", mAvatarInput.getText().toString());
//                params.put("contact", mContactInput.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
//                params.put("avatar", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mAvatarImage.getDrawable()), "image/jpeg"));
//                params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                return params;
            }
        };
        queue.add(multipartRequest);
    }

    void parseResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String code = obj.getString(ApiResponse.KEY_CODE);
            String message = obj.getString(ApiResponse.KEY_MESSAGE);
            ApiResponse r = new ApiResponse(code, message);
            if (r.isValid()) {
                Log.d("UPLOAD", "VALID");

                JSONObject session = obj.getJSONObject(KEY_SESSION);
                Session s = new Session(session);
                Log.d("UPLOAD", "VALID SESSION");

                JSONObject user = obj.getJSONObject(KEY_USER);
                User u = new User(user);
                Log.d("UPLOAD", "VALID USER");

                loginApiResponse = new LoginApiResponse(r,s,u);
            } else {
                loginApiResponse = new LoginApiResponse(r, null, null);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("UPLOAD",  "Parsing error");
        }
        handleLoginApiResponse();
    }

    void handleLoginApiResponse() {
        if(loginApiResponse.getResponse().isValid()) {
            Intent intent = new Intent(context, EmployeeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Log.d("UPLOAD", "Not able to login");
        }

    }
}
