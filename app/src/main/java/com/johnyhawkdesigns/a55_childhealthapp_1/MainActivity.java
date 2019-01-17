package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int NEW_CHILD_ACTIVITY_REQUEST_CODE = 1;


    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //When Floating button is clicked, we are redirected to NewChildActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewChildActivity.class);
                startActivityForResult(intent, NEW_CHILD_ACTIVITY_REQUEST_CODE);

                Log.d(TAG, "onClick: launching NewChildActivity");
            }
        });
    }
}
