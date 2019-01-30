package com.ingic.ezhalbatek.technician.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.activities.MainActivity;
import com.ingic.ezhalbatek.technician.entities.NotificationCountEnt;
import com.ingic.ezhalbatek.technician.entities.ResponseWrapper;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.technician.helpers.NotificationHelper;
import com.ingic.ezhalbatek.technician.retrofit.WebService;
import com.ingic.ezhalbatek.technician.retrofit.WebServiceFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        Log.e(TAG, "From: " + remoteMessage.getFrom());

     //   getNotificaitonCount();
        if (remoteMessage == null)
            return;
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            buildNotification(remoteMessage);


        } else if (remoteMessage.getNotification() != null) {
            String title = getString(R.string.app_name);
            String message = remoteMessage.getNotification().getBody().toString();
            Log.e(TAG, "message: " + message);

            Intent resultIntent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            resultIntent.putExtra("message", message);
            resultIntent.putExtra("tapped", true);


            Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);
            showNotificationMessage(MyFirebaseMessagingService.this, title, message, "", resultIntent);

        }
    }

    private void buildNotification(RemoteMessage messageBody) {
        //getNotificaitonCount();
        String title = getString(R.string.app_name);
        String message = messageBody.getData().get("message");
        String action_id = messageBody.getData().get("action_id");
        String action_type = messageBody.getData().get("action_type");
        String notification_id = messageBody.getData().get("notification_id");
        String badge = messageBody.getData().get("badge");
        Log.e(TAG, "message: " + message);
        Intent resultIntent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra("message", message);
        resultIntent.putExtra("action_id", action_id);
        resultIntent.putExtra("action_type", action_type);
        resultIntent.putExtra("notification_id", notification_id);
        resultIntent.putExtra("badge", badge);
        resultIntent.putExtra("tapped", true);

        Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        pushNotification.putExtra("action_id", action_id);
        pushNotification.putExtra("action_type", action_type);
        pushNotification.putExtra("notification_id", notification_id);
        pushNotification.putExtra("badge", badge);

        preferenceHelper.setNotificationCount(Integer.parseInt(badge));

        if (badge != null && !badge.equals("") && !badge.equals("0")) {
            ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(badge));
        }

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);
        showNotificationMessage(MyFirebaseMessagingService.this, title, message, "", resultIntent);
    }

    private void getNotificaitonCount() {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.Local_SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper<NotificationCountEnt>> call = webservice.notificationCount(preferenceHelper.getUser().getId()+"");
        call.enqueue(new Callback<ResponseWrapper<NotificationCountEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<NotificationCountEnt>> call, Response<ResponseWrapper<NotificationCountEnt>> response) {
                preferenceHelper.setNotificationCount(response.body().getResult().getCount());
            }

            @Override
            public void onFailure(Call<ResponseWrapper<NotificationCountEnt>> call, Throwable t) {

            }
        });
    }

    private void SendNotification(int count, JSONObject json) {

    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        NotificationHelper.getInstance().showNotification(context,
                R.drawable.android_icon,
                title,
                message,
                timeStamp,
                intent);
    }


}
