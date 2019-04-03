package com.johnyhawkdesigns.a55_childhealthapp_1.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.adapter.VacRecordAdapter;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.VacRecordViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AlarmReceiver;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

                    Calendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(System.currentTimeMillis());

                    Log.d(TAG, "onChanged: foundVacRecord.getVacDueDate() = " + foundVacRecord.getVacDueDate());
                    Log.d(TAG, "onChanged: calendar.getTime() = " + calendar.getTime());


                    if (foundVacRecord.getVacDueDate() == calendar.getTime() ){
                        // Generate Notification
                        Log.d(TAG, "onChanged: foundVacRecord.getVacDueDate() = " + foundVacRecord.getVacDueDate());
                        Log.d(TAG, "onChanged: calendar.getTime() = " + calendar.getTime());

                        generateNotification(foundVacRecord.getDoseTime());


                    }

                }

                vacRecordAdapter.setVacRecordList(childVaccinationRecords);
                
            }
        });

    }



    private void generateNotification(String vaccinationDetails){
        Intent alarmIntent = new Intent(this, AlarmReceiver.class); // This intent will be created at a specific time and will launch AlarmReceiver which is a Broadcast receiver - AlarmReceiver will launch IntentService which holds our Notification.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT); // PendingIntent.FLAG_UPDATE_CURRENT could be 0



        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = new GregorianCalendar(); // Calendar.getInstance(); also correct
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 0);// Generate Notification after these seconds. We can change this to Month as well.

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vaccination, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.resetVaccination:

                Log.d(TAG, "onOptionsItemSelected: reset vaccination data");

                // Build alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(Vaccination_Activity.this);
                builder.setTitle("Do you want to reset vaccination data??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.showMessage(Vaccination_Activity.this, "Reset Vaccination Success" );
                        vacRecordViewModel.getAllVacRecords().observe(Vaccination_Activity.this, new Observer<List<ChildVaccinationRecord>>() {
                            @Override
                            public void onChanged(@Nullable List<ChildVaccinationRecord> childVaccinationRecords) {
                                for (int i = 0; i < childVaccinationRecords.size(); i++){
                                    ChildVaccinationRecord foundVacRecord = childVaccinationRecords.get(i);
                                    Log.d(TAG, "onChanged: foundVacRecord = " + foundVacRecord.getVacID());
                                    foundVacRecord.setVacDone(false); // Set all vaccination records to false
                                    vacRecordViewModel.update(foundVacRecord);
                                }

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
