package com.johnyhawkdesigns.a55_childhealthapp_1.activities;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.widget.TextView;


import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.AddEditMedHistoryFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.MedHistoryListFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;

public class MedHistoryActivity extends AppCompatActivity
    implements MedHistoryListFragment.MedHistoryListFragmentListener{

    private static final String TAG = MedHistoryActivity.class.getSimpleName();



    private MedHistoryListFragment medHistoryListFragment;

    private TextView emptyTextView;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_history_container);

        Log.d(TAG, "onCreate: ");

        medHistoryListFragment = new MedHistoryListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.medFragmentContainer, medHistoryListFragment);
        transaction.commit();


    }


    @Override
    public void onAddMedHistory() {

        AddEditMedHistoryFragment addEditMedHistoryFragment = new AddEditMedHistoryFragment();

        // Now we need to verify if we are adding a new MedHistory or editing an existing one
        /*
        // if editing existing medHistory, provide medID as an argument
        if (medID != null){
            Bundle arguments = new Bundle();
            arguments.putParcelable(MED_ID, medID);
            addEditMedHistoryFragment.setArguments(arguments);
        }
        */
        Log.d(TAG, "onAddMedHistory: launching addEditMedHistoryFragment");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.medFragmentContainer, addEditMedHistoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }
}
