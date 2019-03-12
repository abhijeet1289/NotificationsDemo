package com.droidaxel.notificationdemo.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.droidaxel.notificationdemo.MainActivity;
import com.droidaxel.notificationdemo.NotificationModel;
import com.droidaxel.notificationdemo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private Context context;
    private String TAG = MyFirebaseMessagingService.class.getName();

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: ");

        NotificationModel notificationModel = null;

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            notificationModel = new NotificationModel();

            RemoteMessage.Notification notification = remoteMessage.getNotification();
            notificationModel.setTitle(notification.getTitle());
            notificationModel.setText(notification.getBody());
            notificationModel.setLargeIcon(notification.getIcon());
            notificationModel.setEntityTypeId(remoteMessage.getData().get("entityTypeId"));
            notificationModel.setEntityId(remoteMessage.getData().get("entityId"));

//            notificationModel.setNotifCategory(remoteMessage.getData().get("notif_category"));
//            notificationModel.setActionId(remoteMessage.getData().get("action_id"));
//            notificationModel.setTitle(remoteMessage.getData().get("title"));
//            notificationModel.setText(remoteMessage.getData().get("text"));
//            notificationModel.setToneUri(remoteMessage.getData().get("tone_uri"));
//            notificationModel.setActionUrl(remoteMessage.getData().get("action_url"));
//            notificationModel.setLargeIcon(remoteMessage.getData().get("large_icon"));
//            notificationModel.setSmallIcon(remoteMessage.getData().get("small_icon"));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sendNotification(notificationModel);
        } else


            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(final NotificationModel notificationModel) {
        try {

//            Log.d(TAG, "onImageDownloaded bitmap = " + bitmap);
//            Bitmap largeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);

            long[] pattern = {0, 300, 300, 300};

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
//                    .setLargeIcon(largeBitmap)/*Notification icon image*/
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(notificationModel.getTitle())
                    .setContentText(notificationModel.getText())
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(bitmap))/*Notification with Image*/
                    .setAutoCancel(true).setVibrate(pattern);


//            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.mipmap.functionam_notfication)
//                            .setLargeIcon(bitmap)
////                            .setBadgeIconType(R.mipmap.app_icon)
//                            .setContentTitle(notificationModel.getTitle())
//                            .setStyle(new Notification.BigPictureStyle()
//                                    .bigPicture(bitmap))
//                            .setContentText(notificationModel.getText());
            Intent resultIntent = new Intent(context, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("NOTIFICATION_INTENT", notificationModel);
            resultIntent.putExtras(bundle);
            TaskStackBuilder stackBuilder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
            }

            PendingIntent resultPendingIntent = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
            }
            notificationBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, notificationBuilder.build());

        } catch (Exception e) {
            Log.d(TAG, "send notification exception " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}