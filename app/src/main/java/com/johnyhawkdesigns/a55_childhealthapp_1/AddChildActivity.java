package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.Date;

public class AddChildActivity extends AppCompatActivity {

    private static final String TAG = AddChildActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "Add_child_extra";

    private boolean addingNewChild = true; // adding (true) or editing (false)

    private TextInputEditText textInputName;
    private TextInputEditText textInputGender;
    private TextInputEditText textInputBloodGroup;
    private TextInputEditText textInputDateOfBirth;
    private TextInputEditText textInputAge;
    private TextInputEditText textInputHeight;
    private TextInputEditText textInputWeight;
    private TextInputEditText textInputProfileUpdateDate;
    private Button saveChildData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);


        Log.d(TAG, "onCreate: ");

        textInputName = (TextInputEditText) findViewById(R.id.textInputName);
        textInputGender = (TextInputEditText) findViewById(R.id.textInputGender);
        textInputBloodGroup = (TextInputEditText) findViewById(R.id.textInputBloodGroup);
        textInputDateOfBirth = (TextInputEditText) findViewById(R.id.textInputDateOfBirth);
        textInputAge = (TextInputEditText) findViewById(R.id.textInputAge);
        textInputHeight = (TextInputEditText) findViewById(R.id.textInputHeight);
        textInputWeight = (TextInputEditText) findViewById(R.id.textInputWeight);
        textInputProfileUpdateDate = (TextInputEditText) findViewById(R.id.textInputProfileUpdateDate);
        saveChildData = (Button) findViewById(R.id.saveChildData);


        final Date currentDate = AppUtils.getCurrentDateTime();
        final String profileUpdateDate = AppUtils.getFormattedDateString(currentDate);
        textInputProfileUpdateDate.setText(profileUpdateDate);

        final Child child = new Child();

        saveChildData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = textInputName.getText().toString();
                final String gender = textInputGender.getText().toString();
                final String bloodGroup = textInputBloodGroup.getText().toString();
                final String dateOfBirth = textInputDateOfBirth.getText().toString();
                final float age = Float.parseFloat(textInputAge.getText().toString());
                final float height = Float.parseFloat(textInputHeight.getText().toString());
                final float weight = Float.parseFloat(textInputWeight.getText().toString());



                child.setName(name);
                child.setGender(gender);
                child.setBloodGroup(bloodGroup);
                child.setDateOfBirth(dateOfBirth);
                child.setAge(age);
                child.setHeight(height);
                child.setWeight(weight);
                child.setProfileUpdateDate(currentDate);
                child.setGender(gender);
            }
        });
    }

    private final View.OnClickListener saveChildDataButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (addingNewChild){

            }
        }
    };

}
