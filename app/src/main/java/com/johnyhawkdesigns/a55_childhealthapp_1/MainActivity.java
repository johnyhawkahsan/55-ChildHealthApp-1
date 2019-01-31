package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildRepository;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int NEW_CHILD_ACTIVITY_REQUEST_CODE = 1;

    public static ChildRepository childRepository;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        childRepository = new ChildRepository(getApplication());

        childRepository.getAllChilds().observe(this, new Observer<List<Child>>() {
            @Override
            public void onChanged(@Nullable List<Child> children) {
                Log.d(TAG, "onChanged: child list size = " + children.size());
            }
        });


        //When Floating button is clicked, we are redirected to AddChildActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddChildActivity.class);
                startActivityForResult(intent, NEW_CHILD_ACTIVITY_REQUEST_CODE);

                Log.d(TAG, "onClick: launching AddChildActivity");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
