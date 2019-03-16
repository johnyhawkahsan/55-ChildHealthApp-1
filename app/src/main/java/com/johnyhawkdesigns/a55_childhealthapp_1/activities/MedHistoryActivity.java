package com.johnyhawkdesigns.a55_childhealthapp_1.activities;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.AddEditMedHistoryFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.MedHistoryListFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;


public class MedHistoryActivity extends AppCompatActivity
    implements MedHistoryListFragment.MedHistoryListFragmentListener, AddEditMedHistoryFragment.AddEditFragmentListener{

    private static final String TAG = MedHistoryActivity.class.getSimpleName();

    private MedHistoryListFragment medHistoryListFragment;

    public int chID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_history_container);

        chID = (int) getIntent().getSerializableExtra("chID");
        Log.d(TAG, "onCreate: received chID = " + chID);

        medHistoryListFragment = new MedHistoryListFragment();

        Bundle args = new Bundle();
        args.putInt("chID", chID);
        medHistoryListFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.medFragmentContainer, medHistoryListFragment);
        transaction.commit();

    }


    @Override
    public void onMedHistorySelected(int chID, int medID) {

    }

    @Override
    public void onAddMedHistory() {

        AddEditMedHistoryFragment addEditMedHistoryFragment = new AddEditMedHistoryFragment();

        // Now we need to verify if we are adding a new MedHistory or editing an existing one
        /*
        // if editing existing medHistory, provide medID as an argument
        if (medID != null){
            Bundle arguments = new Bundle();
            arguments.putInt("medID", medID);
        }
        */

        Bundle args = new Bundle();
        args.putInt("chID", chID);
        addEditMedHistoryFragment.setArguments(args);

        Log.d(TAG, "onAddMedHistory: launching addEditMedHistoryFragment - sending chID = " + chID);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.medFragmentContainer, addEditMedHistoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public void onAddEditCompleted(int medID) {
        Log.d(TAG, "onAddEditCompleted: medID = " + medID);
        getSupportFragmentManager().popBackStack();
    }
}
