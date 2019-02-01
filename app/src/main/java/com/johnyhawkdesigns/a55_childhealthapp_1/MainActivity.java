package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.adapter.ChildListAdapter;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildRepository;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RC_CREATE_CHILD = 1;
    private static final int RC_UPDATE_CHILD = 2;

    private ChildViewModel childViewModel;

    private TextView emptyTextView;
    private RecyclerView recyclerView;
    // private List<Child> childList; // We don't use this list here anymore, because we are directly assigning childList in Adapter to Observers returned list via childListAdapter.setChildList
    private ChildListAdapter childListAdapter;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTextView =  findViewById(R.id.tv__empty);

        //Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        childViewModel = new ChildViewModel(getApplication());

        childViewModel.getAllChilds().observe(this, new Observer<List<Child>>() {
            @Override
            public void onChanged(@Nullable List<Child> children) {
                Log.d(TAG, "onChanged: child list size = " + children.size());

                if (children.size() > 0 ) {
                    childListAdapter.setChildList(children);
                    emptyTextView.setVisibility(View.GONE);
                }

            }
        });

        childListAdapter = new ChildListAdapter(getApplicationContext());
        recyclerView.setAdapter(childListAdapter);




        //When Floating button is clicked, we are redirected to AddChildActivity
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddChildActivity.class);
                startActivityForResult(intent, RC_CREATE_CHILD);

                AppUtils.showMessage(getApplicationContext(), "Launching AddNewChild Activity");

                Log.d(TAG, "onClick: launching AddChildActivity");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CREATE_CHILD && resultCode == RESULT_OK) {
            //loadChildList();
        } else if (requestCode == RC_UPDATE_CHILD && resultCode == RESULT_OK) {
            //loadChildList();
        }
    }
}
