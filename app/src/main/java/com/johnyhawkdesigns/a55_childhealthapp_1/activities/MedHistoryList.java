package com.johnyhawkdesigns.a55_childhealthapp_1.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.AddEditMedHistoryFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;

public class MedHistoryList extends AppCompatActivity {

    private static final String TAG = MedHistoryList.class.getSimpleName();

    private ChildViewModel childViewModel;

    private TextView emptyTextView;
    private RecyclerView recyclerView;

    private FloatingActionButton floatingActionButton;
    AddEditMedHistoryFragment addEditMedHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_history_list);

        Log.d(TAG, "onCreate: " + TAG);

        //When Floating button is clicked, we are redirected to AddEditMedHistoryFragment
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addMedHistory);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                addEditMedHistoryFragment = new AddEditMedHistoryFragment();
                //fragmentTransaction.add(addEditMedHistoryFragment);
            }
        });


    }


}
