package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AddChildActivity extends AppCompatActivity {

    private static final String TAG = AddChildActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "Add_child_extra";

    private TextInputEditText et_title, et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        Log.d(TAG, "onCreate: ");

    }
}
