package com.johnyhawkdesigns.a55_childhealthapp_1.activities;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.AddEditMedHistoryFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.MedHistoryDetailFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.Fragments.MedHistoryListFragment;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;


public class MedHistoryActivity extends AppCompatActivity
    implements MedHistoryListFragment.MedHistoryListFragmentListener, AddEditMedHistoryFragment.AddEditFragmentListener, MedHistoryDetailFragment.MedHistoryDetailFragmentListener{

    private static final String TAG = MedHistoryActivity.class.getSimpleName();

    private MedHistoryListFragment medHistoryListFragment;

    public int chID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_history_container);

        getSupportActionBar().setTitle("Medical Records");

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
        Log.d(TAG, "onMedHistorySelected: chID = " + chID + ", medID = " + medID);

        getSupportActionBar().setTitle("Medical Details");

        MedHistoryDetailFragment medHistoryDetailFragment = new MedHistoryDetailFragment();
        Bundle args = new Bundle();
        args.putInt("chID", chID);
        args.putInt("medID", medID);
        medHistoryDetailFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.medFragmentContainer, medHistoryDetailFragment);
        transaction.commit();

    }

    @Override
    public void onAddMedHistory() {

        getSupportActionBar().setTitle("Add Medical Record");

        AddEditMedHistoryFragment addEditMedHistoryFragment = new AddEditMedHistoryFragment();

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
    }

    @Override
    public void onMedHistoryDeleted() {
        Log.d(TAG, "onMedHistoryDeleted: ");
    }

    @Override
    public void onEditMedHistory(int chID, int medID) {
        Log.d(TAG, "onEditMedHistory: chID = " + chID + ", medID = " + medID);

        getSupportActionBar().setTitle("Editing Medical Record");

        AddEditMedHistoryFragment addEditMedHistoryFragment = new AddEditMedHistoryFragment();
        Bundle args = new Bundle();
        args.putInt("chID", chID);
        args.putInt("medID", medID);
        addEditMedHistoryFragment.setArguments(args);

        Log.d(TAG, "onEditMedHistory: edit record with chID = " + chID + ", medID = " + medID);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.medFragmentContainer, addEditMedHistoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
