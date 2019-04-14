package com.ewaste.garbagecollector.api;

import android.app.ProgressDialog;
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
import com.ewaste.garbagecollector.EmployeeActivity;
import com.ewaste.garbagecollector.MainActivity;
import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.models.Session;
import com.ewaste.garbagecollector.api.models.User;
import com.ewaste.garbagecollector.api.response.LoginApiResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class LoginApi {

    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/login.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_USERNAME = "username";
    private final String KEY_PASSWORD = "password";

    private String username = "";
    private String password = "";

    private Context context;
    private TextView tv;

    private LoginApiResponse loginApiResponse;
    private final String KEY_SESSION = "session";
    private final String KEY_USER = "user";

    public ProgressDialog d;

    public LoginApi(Context context, String username, String password, TextView tv, ProgressDialog d) {
        this.context = context;
        this.tv = tv;
        this.username = username;
        this.password = password;
//        this.d = new ProgressDialog(MainActivity.this);
        this.d=d;
    }

    public void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(METHOD, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LOGIN API", response);
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGIN API", "Failed "+ error.getMessage());
                d.dismiss();
//                tv.setText(error.getMessage());

            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                Log.d("LOGIN USERNAME:"+username, "USN");
                Log.d("LOGIN PASSWORD:"+password, "PSW");
                return params;
            }
        };
        d.setMessage("Loading...");
        d.show();
        queue.add(stringRequest);
    }

    void parseResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String code = obj.getString(ApiResponse.KEY_CODE);
            String message = obj.getString(ApiResponse.KEY_MESSAGE);
            ApiResponse r = new ApiResponse(code, message);
            if (r.isValid()) {
                Log.d("LOGIN", "VALID");

                JSONObject session = obj.getJSONObject(KEY_SESSION);
                Session s = new Session(session);
                Log.d("LOGIN", "VALID SESSION");

                JSONObject user = obj.getJSONObject(KEY_USER);
                User u = new User(user);
                Log.d("LOGIN", "VALID USER");

                loginApiResponse = new LoginApiResponse(r,s,u);
            } else {
                loginApiResponse = new LoginApiResponse(r, null, null);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("LOGIN",  "Parsing error");
        }
        d.dismiss();
        handleLoginApiResponse();
    }

    void handleLoginApiResponse() {
        if(loginApiResponse.getResponse().isValid()) {
            Log.d("SESSION", loginApiResponse.getSession().getUserId());
            Intent intent = new Intent(context, EmployeeActivity.class);
            intent.putExtra("session", loginApiResponse.getSession());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Log.d("LOGIN", "Not able to login");
        }

    }
}
