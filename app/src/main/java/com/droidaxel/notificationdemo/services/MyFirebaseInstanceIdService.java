package com.droidaxel.notificationdemo.services;

import android.util.Log;
import com.droidaxel.notificationdemo.FCMHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * @author Abhijeet
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private String TAG = MyFirebaseInstanceIdService.class.getName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM token: " + token);
        FCMHelper.getInstance().setFcmToken(token);
    }
}