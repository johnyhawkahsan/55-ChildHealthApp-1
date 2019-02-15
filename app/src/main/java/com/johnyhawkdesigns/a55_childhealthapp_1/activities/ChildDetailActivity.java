package com.johnyhawkdesigns.a55_childhealthapp_1.activities;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

public class ChildDetailActivity extends AppCompatActivity {

    private static final String TAG = ChildDetailActivity.class.getSimpleName();

    private int chID;
    private ChildViewModel childViewModel;

    private static final int RC_UPDATE_CHILD = 2;

    private ImageView imageViewIcon;
    private TextView textViewName;
    private TextView textViewChID;
    private TextView tvChName;
    private TextView tvGender;
    private TextView tvBloodGroup;
    private TextView tvDOB;
    private TextView tvAge;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvLastProfileUpdateDate;

    private Button viewMedicalRecord;
    private Button viewVaccinationRecord;

    public Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_child_details);

        getSupportActionBar().setTitle("Viewing Child Profile");


        imageViewIcon = (ImageView) findViewById(R.id.imageViewIcon);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewChID = (TextView) findViewById(R.id.textViewChID);
        tvChName = (TextView) findViewById(R.id.tvChName);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvBloodGroup = (TextView) findViewById(R.id.tvBloodGroup);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvLastProfileUpdateDate = (TextView) findViewById(R.id.tvLastProfileUpdateDate);


        viewMedicalRecord = (Button) findViewById(R.id.viewMedicalRecord);
        viewVaccinationRecord = (Button) findViewById(R.id.viewVaccinationRecord);

        // initialize childViewModel
        childViewModel = new ChildViewModel(getApplication());

        chID = (int) getIntent().getSerializableExtra("chID");
        Log.d(TAG, "onCreate: received chID = " + chID);


        // initiate search method for chID
        childViewModel.findChildWithID(chID);

        // we simply get an instance of childSearchResult and use observer for this
        childViewModel.getSearchResults().observe(this, new Observer<Child>() {
            @Override
            public void onChanged(@Nullable Child child) {
                Log.d(TAG, "onChanged: found child with name = " + child.getName() + ", with chID = " + child.getChID());

                textViewName.setText(child.getName());
                textViewChID.setText(String.valueOf(child.getChID()));
                tvChName.setText(child.getName());
                tvGender.setText(child.getGender());
                tvBloodGroup.setText(child.getBloodGroup());
                tvDOB.setText(child.getDateOfBirth());
                tvAge.setText(String.valueOf(child.getAge()) + " years");
                tvHeight.setText(String.valueOf(child.getHeight()) + " feet");
                tvWeight.setText(String.valueOf(child.getWeight()) + " kg");
                tvLastProfileUpdateDate.setText(AppUtils.getFormattedDateString(child.getProfileUpdateDate()));

                if (child.getImagePath() != null){
                    imageUri = Uri.parse(child.getImagePath());
                    Glide
                            .with(ChildDetailActivity.this)
                            .load(imageUri)
                            .into(imageViewIcon);
                }



            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_child_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d(TAG, "onOptionsItemSelected: edit child profile");

                Intent intent = new Intent(ChildDetailActivity.this, AddEditChildActivity.class);
                intent.putExtra("chID", chID);
                startActivityForResult(intent, RC_UPDATE_CHILD);
                return true;

            case R.id.action_delete:
                Log.d(TAG, "onOptionsItemSelected: delete child profile");

                // Build alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(ChildDetailActivity.this);
                builder.setTitle("Are you sure??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.showMessage(ChildDetailActivity.this, "Delete child with chID = " + chID + " success" );
                        childViewModel.deleteChildWithID(chID);
                        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode = " + requestCode + ", resultCode = " + resultCode);

        if (requestCode == RC_UPDATE_CHILD && resultCode == RESULT_OK){
            // if RC_UPDATE_CHILD , we again finish the activity with RC_UPDATE_CHILD to MainActivity so there, we can use notifyDataSetChanged() method to update list
            setResult(RC_UPDATE_CHILD);
            finish();
        }

    }
}
