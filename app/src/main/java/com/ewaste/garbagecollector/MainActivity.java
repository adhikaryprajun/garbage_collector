package com.ewaste.garbagecollector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ewaste.garbagecollector.api.LoginApi;
import com.ewaste.garbagecollector.api.models.ApiResponse;
import com.ewaste.garbagecollector.api.response.LoginApiResponse;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    TextView tvResponse;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvResponse = findViewById(R.id.tvResponse);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginApi loginApi = new LoginApi(getApplicationContext(), etUsername.getText().toString(), etPassword.getText().toString(), tvResponse, new ProgressDialog(MainActivity.this));
                loginApi.makeRequest();
            }
        });
    }
}
