package com.johnyhawkdesigns.a55_childhealthapp_1.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver{

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: Alarm running");

        // Launch intent service which holds our Notification data
        Intent intent1 = new Intent(context, IntentService.class);
        context.startService(intent1);
    }
}
