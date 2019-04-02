package com.johnyhawkdesigns.a55_childhealthapp_1.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, IntentService.class);
        context.startService(intent1);
    }
}
