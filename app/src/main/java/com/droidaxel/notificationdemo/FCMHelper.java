package com.droidaxel.notificationdemo;

import android.content.Context;

/**
 * @author Abhijeet
 */

public class FCMHelper {
    private static final String TAG = "FCMHelper";

    private Context mContext;
    private String fcmToken;
    private static FCMHelper fcmHelper;

    public static void init(Context context) {
        if (fcmHelper == null)
            fcmHelper = new FCMHelper();

        fcmHelper.mContext = context;
    }

    private FCMHelper() {
    }

    public static FCMHelper getInstance() {
        if (fcmHelper == null)
            fcmHelper = new FCMHelper();
        return fcmHelper;
    }

    public void setFcmToken(String fcmToken) {
        if (fcmToken.isEmpty())
            return;

        this.fcmToken = fcmToken;
    }

    private void saveToken(String fcmToken) {
        try {
            if (fcmToken == null)
                return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFcmToken() {
        return fcmToken;
    }
}