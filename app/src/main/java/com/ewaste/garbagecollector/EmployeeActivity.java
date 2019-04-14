package com.ewaste.garbagecollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ewaste.garbagecollector.adapter.UploadListAdapter;
import com.ewaste.garbagecollector.api.ApiCallback;
import com.ewaste.garbagecollector.api.LogoutApi;
import com.ewaste.garbagecollector.api.UploadStatusApi;
import com.ewaste.garbagecollector.api.models.Session;
import com.ewaste.garbagecollector.api.models.Upload;
import com.ewaste.garbagecollector.api.response.UploadStatusApiResponse;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    private ListView lvUploads;
    public static UploadListAdapter uploadListAdapter;

    private Session session;

    private ArrayList<Upload> uploads;
    private UploadStatusApiResponse controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        session = (Session) getIntent().getSerializableExtra("session");

        Log.d("SESSION", session.getUserId());

        lvUploads = (ListView) findViewById(R.id.lvUploads);

        uploads = new ArrayList<>();
        uploadListAdapter = new UploadListAdapter(getApplicationContext(), session, uploads);
        lvUploads.setAdapter(uploadListAdapter);

        Log.d("HASH", session.getHashValue());
        UploadStatusApi uploadStatusApi = new UploadStatusApi(getApplicationContext(), session.getHashValue(), "0", "0", new ApiCallback() {
            @Override
            public void apiCompleted(UploadStatusApiResponse response) {
                controller = response;
                uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, response.getEmployeeActiveUploads(session.getUserId()));
                lvUploads.setAdapter(uploadListAdapter);
            }
        });
        uploadStatusApi.makeRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.employee_logout) {
            // TODO Call Logout API
            Log.d("LOGOUT", "Calling Logout");
            LogoutApi logoutApi = new LogoutApi(getApplicationContext(), session.getHashValue());
            logoutApi.makeRequest();
        } else if(item.getItemId()==R.id.employee_new_reports) {
            uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, controller.getAllActiveUploads());
            lvUploads.setAdapter(uploadListAdapter);
        } else if(item.getItemId()==R.id.employee_activity) {
            uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, controller.getEmployeeActiveUploads(session.getUserId()));
            lvUploads.setAdapter(uploadListAdapter);
        }
        return super.onOptionsItemSelected(item);
    }
}
