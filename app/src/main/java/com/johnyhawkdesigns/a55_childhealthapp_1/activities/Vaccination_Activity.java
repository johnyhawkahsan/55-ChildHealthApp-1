package com.johnyhawkdesigns.a55_childhealthapp_1.activities;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.adapter.VacRecordAdapter;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.VacRecordViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

import java.util.List;


public class Vaccination_Activity extends AppCompatActivity {

    private static final String TAG = Vaccination_Activity.class.getSimpleName();

    private RecyclerView recyclerView;
    private VacRecordAdapter vacRecordAdapter;
    private VacRecordViewModel vacRecordViewModel;

    public int chID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_vaccination_record);

        getSupportActionBar().setTitle("Child Vaccination Record");

        chID = (int) getIntent().getSerializableExtra("chID");
        Log.d(TAG, "onCreate: received chID = " + chID);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewVaccination);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vacRecordViewModel = new VacRecordViewModel(getApplication(), chID);
        
        vacRecordAdapter = new VacRecordAdapter(Vaccination_Activity.this, new VacRecordAdapter.VacRecordCheckListener() {
            @Override
            public void onCheck(int chID, int vacID) {
                Log.d(TAG, "onCheck: chID = " + chID + ", vacID = " + vacID);

                // get this vacID item and set it's status to done.
                vacRecordViewModel.findVacRecordWithID(chID, vacID);
                vacRecordViewModel.getSearchResults().observe(Vaccination_Activity.this, new Observer<ChildVaccinationRecord>() {
                    @Override
                    public void onChanged(@Nullable ChildVaccinationRecord childVaccinationRecord) {
                        Log.d(TAG, "onChanged: found vacRecord with chID = " + childVaccinationRecord.getForeignChID() + ", vacID = " + childVaccinationRecord.getVacID());
                        childVaccinationRecord.setVacDone(true);
                        vacRecordViewModel.update(childVaccinationRecord);
                    }
                });

            }
        });

        recyclerView.setAdapter(vacRecordAdapter);

        vacRecordViewModel.getAllVacRecords().observe(this, new Observer<List<ChildVaccinationRecord>>() {
            @Override
            public void onChanged(@Nullable List<ChildVaccinationRecord> childVaccinationRecords) {

                Log.d(TAG, "onChanged: vacRecordListSize = " + childVaccinationRecords.size());

                // Loop through all returned list items and display in logs
                for (int i = 0; i < childVaccinationRecords.size(); i++){
                    ChildVaccinationRecord foundVacRecord = childVaccinationRecords.get(i);
                    Log.d(TAG, "vacID = " + foundVacRecord.getVacID());

                    if (foundVacRecord.getVacDone() == true){
                        Log.d(TAG, "foundVacRecord.getVacDone() == true");
                    } else {
                        Log.d(TAG, "foundVacRecord.getVacDone() == false");
                    }

                }

                vacRecordAdapter.setVacRecordList(childVaccinationRecords);
                
            }
        });



    }
}
