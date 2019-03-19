package com.johnyhawkdesigns.a55_childhealthapp_1.Fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.MedHistoryViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

public class MedHistoryDetailFragment extends Fragment {

    // callback method implemented by MedHistoryActivity
    public interface MedHistoryDetailFragmentListener{
        void onMedHistoryDeleted();
        void onEditMedHistory(int chID, int medID);
    }

    private MedHistoryDetailFragmentListener listener;

    MedHistoryViewModel medHistoryViewModel;

    private static final String TAG = MedHistoryDetailFragment.class.getSimpleName();

    public int chID;
    public int medID;

    private ImageView prescImage;
    private TextView prescChId;
    private TextView prescMedID;
    private TextView tvDoctorName;
    private TextView tvVisitDate;
    private TextView tvDiseaseDesc;
    private TextView tvPrescMedicine;

    public Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.view_med_record, container, false);

        Bundle arguments = getArguments();
        if (arguments != null){
            chID = arguments.getInt("chID"); // receive chID
            medID = arguments.getInt("medID"); // receive medID
            Log.d(TAG, "onCreateView: received chID = " + chID + ", medID = " + medID);
        }

        prescImage = (ImageView) view.findViewById(R.id.prescImage);
        prescChId = (TextView) view.findViewById(R.id.prescChId);
        prescMedID = (TextView) view.findViewById(R.id.prescMedID);
        tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
        tvVisitDate = (TextView) view.findViewById(R.id.tvVisitDate);
        tvDiseaseDesc = (TextView) view.findViewById(R.id.tvDiseaseDesc);
        tvPrescMedicine = (TextView) view.findViewById(R.id.tvPrescMedicine);

        medHistoryViewModel = new MedHistoryViewModel(getActivity().getApplication());
        medHistoryViewModel.findMedHistoryWithID(medID, chID);
        medHistoryViewModel.getSearchResults().observe(getActivity(), new Observer<ChildMedicalHistory>() {
            @Override
            public void onChanged(@Nullable ChildMedicalHistory childMedicalHistory) {
                Log.d(TAG, "onChanged: found MedicalHistory with chID = " + chID +
                        ", \nmedID = " + medID +
                        ", \ndoctorName = " + childMedicalHistory.getDoctorName() +
                        ", \ndiseaseDesc = " + childMedicalHistory.getDiseaseDec()
                );


                prescChId.setText("chID = " + String.valueOf(childMedicalHistory.getForeignChID()));
                prescMedID.setText("medID = " + String.valueOf(childMedicalHistory.getMedID()));
                tvDoctorName.setText(childMedicalHistory.getDoctorName());
                tvVisitDate.setText(AppUtils.getFormattedDateString(childMedicalHistory.getVisitDate()));
                tvDiseaseDesc.setText(childMedicalHistory.getDiseaseDec());
                tvPrescMedicine.setText(childMedicalHistory.getPrescMedicine());

                if (childMedicalHistory.getImagePath() != null){
                    imageUri = Uri.parse(childMedicalHistory.getImagePath());
                    Glide
                            .with(getActivity())
                            .load(imageUri)
                            .into(prescImage);
                }




            }
        });

        return view;
    }

    // Fragment lifecycle method- set MedHistoryDetailFragmentListener when fragment attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MedHistoryDetailFragmentListener) context;
    }

    // Fragment lifecycle method- remove MedHistoryDetailFragmentListener when Fragment detached
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_children, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d(TAG, "onOptionsItemSelected: edit child profile");

                listener.onEditMedHistory(chID, medID);

                return true;

            case R.id.action_delete:

                Log.d(TAG, "onOptionsItemSelected: delete med history");

                // Build alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.showMessage(getActivity(), "Delete Med Record chID = " + chID + ", medID = " + medID + " success" );
                        medHistoryViewModel.deleteMedHistoryWithID(chID, medID);
                        listener.onMedHistoryDeleted();
                        getFragmentManager().popBackStack();
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