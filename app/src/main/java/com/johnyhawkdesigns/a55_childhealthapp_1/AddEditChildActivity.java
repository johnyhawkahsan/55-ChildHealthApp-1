package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.Calendar;
import java.util.Date;

public class AddEditChildActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = AddEditChildActivity.class.getSimpleName();
    public static ChildViewModel childViewModel;

    private static final int PICKFILE_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    private boolean addingNewChild = true; // adding (true) or editing (false)
    private int chID = 0;
    public Uri selectedImageUri;

    private TextInputEditText textInputName;
    private Spinner textInputGender;
    private TextInputEditText textInputBloodGroup;
    private TextInputEditText textInputDateOfBirth;
    private TextInputEditText textInputAge;
    private TextInputEditText textInputHeight;
    private TextInputEditText textInputWeight;
    private TextView textInputProfileUpdateDate;
    private FloatingActionButton saveChildData;
    private Button setDateOfBirth;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Date dateOfBirth;

    Date currentDate;
    String profileUpdateDateString;


    String name = "";
    String gender = "";
    String bloodGroup = "";
    String dateOfBirthString = "";
    String age = "";
    String height =  "";
    String weight = "";

    private ImageView mPostImage;
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_child);

        if (addingNewChild){
            getSupportActionBar().setTitle("Add Child Profile");
        }

        // initialize childViewModel
        childViewModel = new ChildViewModel(getApplication());


        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, AddEditChildActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) );

        mPostImage = (ImageView) findViewById(R.id.post_image);
        textInputName = (TextInputEditText) findViewById(R.id.textInputName);
        textInputGender = (Spinner) findViewById(R.id.textInputGender);
        textInputBloodGroup = (TextInputEditText) findViewById(R.id.textInputBloodGroup);
        textInputDateOfBirth = (TextInputEditText) findViewById(R.id.textInputDateOfBirth);
        textInputDateOfBirth.setEnabled(false); // I want it to be non editable
        setDateOfBirth = (Button) findViewById(R.id.setDateOfBirth) ;
        textInputAge = (TextInputEditText) findViewById(R.id.textInputAge);
        textInputHeight = (TextInputEditText) findViewById(R.id.textInputHeight);
        textInputWeight = (TextInputEditText) findViewById(R.id.textInputWeight);
        textInputProfileUpdateDate = (TextView) findViewById(R.id.textInputProfileUpdateDate);
        saveChildData = (FloatingActionButton) findViewById(R.id.saveChildDataFab);

        // if we are adding new child, there should be no data in intent to assign to chID
        if (getIntent().getSerializableExtra("chID") != null){
            chID = (int) getIntent().getSerializableExtra("chID");
            Log.d(TAG, "onCreate: received chID = " + chID);

            addingNewChild = false;
            getSupportActionBar().setTitle("Edit Child Profile");

            childViewModel.findChildWithID(chID);
            childViewModel.getSearchResults().observe(this, new Observer<Child>() {
                @Override
                public void onChanged(@Nullable Child child) {
                    textInputName.setText(child.getName());
                    textInputBloodGroup.setText(child.getBloodGroup());
                    textInputDateOfBirth.setText(child.getDateOfBirth());
                    textInputAge.setText(String.valueOf(child.getAge()));
                    textInputHeight.setText(String.valueOf(child.getHeight()));
                    textInputWeight.setText(String.valueOf(child.getWeight()));

                    Log.d(TAG, "onChanged: " + child.getDateOfBirth());
                    textInputProfileUpdateDate.setText(child.getDateOfBirth());
                }
            });
        }

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Open popup menu to choose new photo");

                PopupMenu popupMenu = new PopupMenu(AddEditChildActivity.this, mPostImage);
                popupMenu.getMenuInflater().inflate(R.menu.menu_image, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_select_image:
                                Log.d(TAG, "onMenuItemClick: ");

                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//Allow the user to select a particular kind of data and return it. This is different than ACTION_PICK in that here we just say what kind of data is desired, not a URI of existing data from which the user can pick.
                                intent.setType("image/*");
                                //startActivityForResult(intent, PICKFILE_REQUEST_CODE); // Both startActivityForResult do the same
                                startActivityForResult(Intent.createChooser(intent, "Select a picture"), PICKFILE_REQUEST_CODE);

                                return true;

                            default:
                                return true;
                        }
                    }
                });

                popupMenu.show(); //showing popup menu

            }
        });

        currentDate = AppUtils.getCurrentDateTime();
        profileUpdateDateString = AppUtils.getFormattedDateString(currentDate);
        textInputProfileUpdateDate.setText(profileUpdateDateString);

        // Two options for spinner
        String[] genders = {"Male", "Female"};

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        textInputGender.setAdapter(dataAdapter);

        textInputGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: gender selected = " + gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        saveChildData.setOnClickListener(saveChildDataButtonClicked);

    }


    // When Save button is clicked
    private final View.OnClickListener saveChildDataButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final Child child = new Child();

            name = textInputName.getText().toString();
            //gender = String.valueOf(textInputGender.getSelectedItem());
            bloodGroup = textInputBloodGroup.getText().toString();
            age = textInputAge.getText().toString();
            height = textInputHeight.getText().toString();
            weight = textInputWeight.getText().toString();




            if (name.length() == 0 || gender.length() == 0 || bloodGroup.length() == 0 || dateOfBirthString.length() == 0 || age.length() == 0 || height.length() == 0 || weight.length() == 0){

                Log.d(TAG, "onClick: any of the parameters is empty");
                AppUtils.showMessage(getApplicationContext(), "Please fill all details!");


            }
            //If no field is left empty and everything is filled
            else {

                child.setName(name);
                child.setGender(gender);
                child.setBloodGroup(bloodGroup);
                child.setDateOfBirth(dateOfBirthString);

                if (age.length() != 0){
                    child.setAge(Double.parseDouble(age));
                }
                if (height.length() != 0){
                    child.setHeight(Double.parseDouble(height));
                }
                if (weight.length() != 0){
                    child.setWeight(Double.parseDouble(weight));
                }

                child.setProfileUpdateDate(currentDate);
                child.setGender(gender);

                // If addingNewChild is true and we are not editing existing child
                if (addingNewChild){

                    childViewModel.insert(child);
                    Log.d(TAG, "onClick: child added = " + child.getName());
                    AppUtils.showMessage(getApplicationContext(), "Child named " + child.getName() + " added to database");
                    setResult(RESULT_OK);
                    finish();

                }
                // if addingNewChild == false, we are editing existing child
                else {

                    Log.d(TAG, "onClick: editing existing child with chID = " + chID);
                    child.setChID(chID);
                    childViewModel.update(child);
                    setResult(RESULT_OK);
                    finish();
                }

            }
        }
    };


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateOfBirth = calendar.getTime();
        dateOfBirthString = AppUtils.getFormattedDateString(dateOfBirth);
        Log.d(TAG, "onDateSet: dateOfBirthString = " + dateOfBirthString + " , from original dateOfBirth = " + dateOfBirth);
        //dateOfBirthString = dateOfBirth.toLocaleString().substring(0, 11);
        textInputDateOfBirth.setText(dateOfBirthString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting a new image from memory
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            selectedImageUri = data.getData();

            if (selectedImageUri != null){
                Log.d(TAG, "onActivityResult: image uri: " + selectedImageUri);


                if (checkPermissionREAD_EXTERNAL_STORAGE(this)){
                    // if permission is granted, below lines will get executed.
                    Glide
                            .with(AddEditChildActivity.this)
                            .load(selectedImageUri)
                            .into(mPostImage);
                }

            }

        }
    }


    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT; // Get current sdk version
        Log.d(TAG, "checkPermissionREAD_EXTERNAL_STORAGE: currentAPIVersion = " + currentAPIVersion);

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) { // android M api is 23

            // checks if the app does not have permission needed
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // shows an explanation of why permission is needed
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            // if app already has permission to write to external storage
            return true;
        }
    }

    // Show dialog for permissions
    public void showDialog(final String msg, final Context context, final String permission) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    // called by the system when the user either grants or denies the permission for saving an image
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // switch chooses appropriate action based on which feature requested permission
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: PackageManager.PERMISSION_GRANTED");
                    // when user first time grants the permissions, these lines get executed
                    Glide
                            .with(AddEditChildActivity.this)
                            .load(selectedImageUri)
                            .into(mPostImage);

                } else {
                    Toast.makeText(AddEditChildActivity.this, "Get read permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
