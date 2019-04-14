package com.ewaste.garbagecollector.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ewaste.garbagecollector.EmployeeActivity;
import com.ewaste.garbagecollector.R;
import com.ewaste.garbagecollector.api.ApiCallback;
import com.ewaste.garbagecollector.api.UploadStatusApi;
import com.ewaste.garbagecollector.api.models.Session;
import com.ewaste.garbagecollector.api.models.Upload;
import com.ewaste.garbagecollector.api.response.UploadStatusApiResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by prajunadhikary on 13/04/19.
 */

public class UploadListAdapter extends ArrayAdapter<Upload> {

    private ArrayList<Upload> uploads;
    private Context context;
    private Session session;

    private int lastPosition = -1;

    public UploadListAdapter(Context context, Session session, ArrayList<Upload> uploads) {
        super(context, R.layout.list_row_upload, uploads);
        this.context = context;
        this.uploads = uploads;
        this.session = session;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Upload upload = getItem(position);
        ViewHolder vh;
        final View result;
        if(convertView == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_upload, parent, false);
            vh.tvListUploadTime = (TextView) convertView.findViewById(R.id.tvListUploadTime);
            vh.tvListDescription = (TextView) convertView.findViewById(R.id.tvListDescription);
            vh.tvListUploadStatus = (TextView) convertView.findViewById(R.id.tvListUploadStatus);
            vh.ivListUploadImage = (ImageView) convertView.findViewById(R.id.ivListUploadImage);
            vh.btnListUpdateStatus = (Button) convertView.findViewById(R.id.btnListUpdateStatus);
            result = convertView;
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        Picasso.get().load("http://goodrichpestcontrol.com/ewaste/api/uploads/"+upload.getPicture()).into(vh.ivListUploadImage);
        vh.tvListUploadTime.setText(upload.getUploadTime());
        vh.tvListDescription.setText(upload.getDescription());
        vh.tvListUploadStatus.setText(upload.getStatus());
        vh.btnListUpdateStatus.setTag(position);
        vh.btnListUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload clickedUpload = (Upload) getItem(position);
                Log.d("UPLOAD" , "Clicked on " + clickedUpload.getId());
                Log.d("UPLOAD", "Session Hash" + session.getHashValue());
                Log.d("UPLOAD", "Status " + clickedUpload.getStatus());

                String newStatus = "0";
                if(clickedUpload.getStatus().equals("0")) {
                    newStatus = "1";
                } else if(clickedUpload.getStatus().equals("2")) {
                    newStatus = "3";
                }
                UploadStatusApi uploadStatusApi = new UploadStatusApi(context, session.getHashValue(), newStatus, clickedUpload.getId(), new ApiCallback() {
                    @Override
                    public void apiCompleted(UploadStatusApiResponse response) {
                        Log.d("UPLOAD_CALLBACK","callback from adapter");
                        ListView lv = (ListView) parent;
                        lv.setAdapter(new UploadListAdapter(context, session, response.getUploads()));
                    }
                });
                uploadStatusApi.makeRequest();
            }
        });
        return convertView;
    }

    public ArrayList<Upload> getUploads() {
        return uploads;
    }

    public void setUploads(ArrayList<Upload> uploads) {
        this.uploads = uploads;
    }

    private static class ViewHolder {
        TextView tvListUploadTime;
        TextView tvListDescription;
        TextView tvListUploadStatus;
        ImageView ivListUploadImage;
        Button btnListUpdateStatus;
    }
}
