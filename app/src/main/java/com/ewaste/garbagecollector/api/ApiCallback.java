package com.ewaste.garbagecollector.api;

import com.ewaste.garbagecollector.api.response.UploadStatusApiResponse;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public interface ApiCallback {

    void apiCompleted(UploadStatusApiResponse response);
}
