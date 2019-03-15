package com.johnyhawkdesigns.a55_childhealthapp_1.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.MedHistoryViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.Calendar;
import java.util.Date;


public class AddEditMedHistoryFragment extends android.support.v4.app.Fragment implements DatePickerDialog.OnDateSetListener{


    //callback method implemented by MainActivity
    public interface AddEditFragmentListener{
        // called when medID is saved
        void onAddEditCompleted(int medID);
    }

    private AddEditFragmentListener addEditFragmentListener;

    private static final String TAG = AddEditMedHistoryFragment.class.getSimpleName();
    private static MedHistoryViewModel medHistoryViewModel;

    private static final int PICKFILE_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    private boolean addingNewMedRecord = true; // adding (true) or editing (false)

    public Uri selectedImageUri;
    private TextInputEditText textInputDoctorName;
    private Button setVisitDate;
    private TextInputEditText textInputVisitDate;
    private TextInputEditText textInputDiseaseDesc;
    private TextInputEditText textPrescMedicine;
    private FloatingActionButton saveChildMedicalRecord;
    private ImageView mPostImage;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Date visitDate;

    String doctorName = "";
    String visitDateString = "";
    String diseaseDesc = "";
    String prescMedicine = "";

    private int chID = 0;
    private int medID = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.add_edit_med_record, container, false);

        medHistoryViewModel = new MedHistoryViewModel(getActivity().getApplication(), 0);

        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) );

        textInputDoctorName = (TextInputEditText) view.findViewById(R.id.textInputDoctorName);
        setVisitDate = (Button) view.findViewById(R.id.setVisitDate);
        textInputVisitDate = (TextInputEditText) view.findViewById(R.id.textInputVisitDate);
        textInputDiseaseDesc = (TextInputEditText) view.findViewById(R.id.textInputDiseaseDesc);
        textPrescMedicine = (TextInputEditText) view.findViewById(R.id.textPrescMedicine);
        saveChildMedicalRecord = (FloatingActionButton) view.findViewById(R.id.saveChildMedicalRecord);
        mPostImage = (ImageView) view.findViewById(R.id.imageViewPrescription);

        // if we are adding new MedHistory, there should be no data in getArguments to assign to chID
        if (getArguments() != null){
            chID = getArguments().getInt("chID"); // receive chID
            medID = getArguments().getInt("medID"); // receive chID

            Log.d(TAG, "onCreateView: received chID = " + chID + ", addingNewMedRecord = false");

            addingNewMedRecord = false;

            medHistoryViewModel.findMedHistoryWithID(medID, chID);
            medHistoryViewModel.getSearchResults().observe(this, new Observer<ChildMedicalHistory>() {
                @Override
                public void onChanged(@Nullable ChildMedicalHistory childMedicalHistory) {

                }
            });

        }


        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Open popup menu to choose new photo");

                PopupMenu popupMenu = new PopupMenu(getActivity(), mPostImage);
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

        setVisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        saveChildMedicalRecord.setOnClickListener(saveButtonClicked);


        return view;
    }

    // When Save button is clicked
    private final View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            doctorName = textInputDoctorName.getText().toString();
            diseaseDesc = textInputDiseaseDesc.getText().toString();
            prescMedicine = textPrescMedicine.getText().toString();

            if (doctorName.length() == 0 || diseaseDesc.length() == 0 || prescMedicine.length() == 0 || visitDateString.length() == 0){

                Log.d(TAG, "onClick: any of the parameters is empty");
                AppUtils.showMessage(getActivity(), "Please fill all details!");

            }

            //If no field is left empty and everything is filled
            else {
                final ChildMedicalHistory childMedicalHistory = new ChildMedicalHistory(chID, doctorName, visitDate, diseaseDesc, prescMedicine);

                if (selectedImageUri != null){
                    childMedicalHistory.setImagePath(selectedImageUri.toString());
                }

                // If addingNewMedRecord is true and we are not editing existing child
                if (addingNewMedRecord){
                    medHistoryViewModel.insert(childMedicalHistory);
                    Log.d(TAG, "onClick: MedRecord added, medID = " + childMedicalHistory.getMedID());
                    AppUtils.showMessage(getActivity(), "Med Record added with meID = " + childMedicalHistory.getMedID());
                    addEditFragmentListener.onAddEditCompleted(childMedicalHistory.getMedID());
                }
                // if addingNewMedRecord == false, we are editing existing MedRecord
                else {

                    Log.d(TAG, "onClick: editing existing MedRecord with medID = " + medID);
                    childMedicalHistory.setMedID(medID);
                    medHistoryViewModel.update(childMedicalHistory);
                    addEditFragmentListener.onAddEditCompleted(childMedicalHistory.getMedID());
                }

            }

        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        visitDate = calendar.getTime();
        visitDateString = AppUtils.getFormattedDateString(visitDate);
        Log.d(TAG, "onDateSet: visitDateString = " + visitDateString + " , from original visitDate = " + visitDate);
        textInputVisitDate.setText(visitDateString);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting a new image from memory
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            selectedImageUri = data.getData();

            if (selectedImageUri != null){
                Log.d(TAG, "onActivityResult: image uri: " + selectedImageUri);


                if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())){
                    // if permission is granted, below lines will get executed.
                    Glide
                            .with(getActivity())
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
                            .with(getActivity())
                            .load(selectedImageUri)
                            .into(mPostImage);

                } else {
                    Toast.makeText(getActivity(), "Get read permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addEditFragmentListener = (AddEditFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addEditFragmentListener = null;
    }




}
