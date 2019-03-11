package com.johnyhawkdesigns.a55_childhealthapp_1.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class MedHistoryAdapter extends RecyclerView.Adapter<MedHistoryAdapter.MedHistoryViewHolder>{

    private static final String TAG = MedHistoryAdapter.class.getSimpleName();
    Context mContext;
    private final LayoutInflater mInflator;
    private List<ChildMedicalHistory> medicalHistories;

    // interface implemented by MedHistoryActivity to respond when the user touches an item in the RecyclerView.
    public interface MedHistoryClickListener{
        void onClick(int chID, int medID); // Two id's because both are needed for query
    }

    private final MedHistoryClickListener medHistoryClickListener;

    // Constructor
    public MedHistoryAdapter(Context mContext, MedHistoryClickListener medHistoryClickListener) {
        this.mContext = mContext;
        this.medHistoryClickListener = medHistoryClickListener;
        mInflator = LayoutInflater.from(mContext);
    }

    public void setMedicalHistoryList(List<ChildMedicalHistory> medicalHistoryList){
        this.medicalHistories = medicalHistoryList;
        notifyDataSetChanged();
    }

    //MedHistoryViewHolder class
     class MedHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView chID, medID, visitDate, doctorName;
        private final ImageView medPrescriptionIcon;


        public MedHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            medPrescriptionIcon = itemView.findViewById(R.id.medPrescriptionIcon);
            medID = itemView.findViewById(R.id.tv_medID);
            chID = itemView.findViewById(R.id.tv_chID);
            visitDate = itemView.findViewById(R.id.tv_visitDate);
            doctorName = itemView.findViewById(R.id.tv_doctorName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ChildMedicalHistory childMedicalHistory = medicalHistories.get(getAdapterPosition());
            int medID = childMedicalHistory.getMedID();
            int chID = childMedicalHistory.getForeignChID();

            AppUtils.showMessage(mContext, "Clicked on MedHistory with medID = " + medID + ", chID = " + chID);
            Log.d(TAG, "onClick: Clicked on MedHistory with medID = " + medID + ", chID = " + chID);

            // This interface method sends chID and medID to MedHistoryActivity's MedHistoryAdapter constructor method.
            medHistoryClickListener.onClick(chID, medID);

        }
    }

    @NonNull
    @Override
    public MedHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflator.inflate(R.layout.recycler_med_history_item, viewGroup, false);
        return new MedHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedHistoryViewHolder medHistoryViewHolder, int position) {
        ChildMedicalHistory childMedicalHistory = medicalHistories.get(position);
        medHistoryViewHolder.medPrescriptionIcon.setImageResource(R.drawable.doctor_prescription);
        medHistoryViewHolder.chID.setText(String.valueOf(childMedicalHistory.getForeignChID()));
        medHistoryViewHolder.medID.setText(String.valueOf(childMedicalHistory.getMedID()));
        medHistoryViewHolder.visitDate.setText(AppUtils.getFormattedDateString(childMedicalHistory.getVisitDate()));
        medHistoryViewHolder.doctorName.setText(childMedicalHistory.getDoctorName());

        if (childMedicalHistory.getImagePath() != null){
            Uri imageUri = Uri.parse(childMedicalHistory.getImagePath());
            // load image using Glide
            Glide
                    .with(mContext)
                    .load(imageUri)
                    .into(medHistoryViewHolder.medPrescriptionIcon);
        }


    }

    @Override
    public int getItemCount() {
        if (medicalHistories == null)
            return 0;
        else
            return  medicalHistories.size();
    }


}
