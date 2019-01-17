package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NewChildActivity extends AppCompatActivity {

    private static final String TAG = NewChildActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_child);

        Log.d(TAG, "onCreate: ");

    }
}
