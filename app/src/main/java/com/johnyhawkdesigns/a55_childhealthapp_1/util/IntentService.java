package com.johnyhawkdesigns.a55_childhealthapp_1.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.MainActivity;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;

public class IntentService extends android.app.IntentService{

    private static final String TAG = IntentService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 3;

    String title;
    String content;

    public IntentService() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent.getStringExtra("vaccinationDetails") != null){
            String vaccinationDetails = intent.getStringExtra("vaccinationDetails");
            intent.getStringExtra("vaccinationDetails");

            title = "Upcoming Vaccination";
            content = vaccinationDetails;

            Log.d(TAG, "onReceive: vaccinationDetails = " + vaccinationDetails );

        } else {
            title = "Child Health App";
            content = "Update child profile please";
        }



        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_firstaidkit);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent); //to be able to launch your activity from the notification

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

    }

}
