package com.johnyhawkdesigns.a55_childhealthapp_1.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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


        if (intent.getStringExtra("vaccinationDetails") != null){
            String vaccinationDetails = intent.getStringExtra("vaccinationDetails");
            intent1.getStringExtra("vaccinationDetails");
            intent1.putExtra("vaccinationDetails", vaccinationDetails);

            Log.d(TAG, "onReceive: vaccinationDetails = " + vaccinationDetails );
        }

        context.startService(intent1);
    }
}
