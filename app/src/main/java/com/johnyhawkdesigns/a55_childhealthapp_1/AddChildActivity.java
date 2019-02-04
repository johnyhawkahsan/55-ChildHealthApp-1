package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.app.DatePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildRepository;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.Calendar;
import java.util.Date;

public class AddChildActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = AddChildActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "Add_child_extra";
    public static ChildViewModel childViewModel;

    private boolean addingNewChild = true; // adding (true) or editing (false)

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        getSupportActionBar().setTitle("Add Child Profile");

        childViewModel = new ChildViewModel(getApplication());

        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, AddChildActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) );

        Log.d(TAG, "onCreate: ");

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


        currentDate = AppUtils.getCurrentDateTime();
        profileUpdateDateString = AppUtils.getFormattedDateString(currentDate);
        textInputProfileUpdateDate.setText(profileUpdateDateString);


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


    // When Save button is cliked
    private final View.OnClickListener saveChildDataButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name = textInputName.getText().toString();
            //gender = String.valueOf(textInputGender.getSelectedItem());
            bloodGroup = textInputBloodGroup.getText().toString();
            age = textInputAge.getText().toString();
            height = textInputHeight.getText().toString();
            weight = textInputWeight.getText().toString();


            if (name.length() == 0 || gender.length() == 0 || bloodGroup.length() == 0 || dateOfBirth.toString().length() == 0 || age.length() == 0 || height.length() == 0 || weight.length() == 0){

                Log.d(TAG, "onClick: any of the parameters is empty");
                AppUtils.showMessage(getApplicationContext(), "Please fill all details!");

            } else {

                final Child child = new Child();
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
                    Log.d(TAG, "onClick: child added = " + child.getName() + ", chID = " + child.getChID());
                    AppUtils.showMessage(getApplicationContext(), "Child named " + child.getName() + " with chID = " + child.getChID() + " added to database");
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
}
