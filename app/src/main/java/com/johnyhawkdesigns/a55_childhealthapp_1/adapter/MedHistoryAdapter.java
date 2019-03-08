package com.johnyhawkdesigns.a55_childhealthapp_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class MedHistoryAdapter extends RecyclerView.Adapter<MedHistoryAdapter.MedHistoryViewHolder>{

    private static final String TAG = MedHistoryAdapter.class.getSimpleName();
    Context mContext;
    private final LayoutInflater mInflator;
    private List<ChildMedicalHistory> medicalHistories;

    @NonNull
    @Override
    public MedHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MedHistoryViewHolder medHistoryViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // interface implemented by MainActivity to respond when the user touches an item in the RecyclerView.
    public interface MedHistoryClickListener{
        void onClick(int medID);
    }

    private final MedHistoryClickListener medHistoryClickListener;

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
        private final TextView medID, visitDate, doctorName;
        private final ImageView medPrescriptionIcon;


        public MedHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            medPrescriptionIcon = itemView.findViewById(R.id.medPrescriptionIcon);
            medID = itemView.findViewById(R.id.tv_medID);
            visitDate = itemView.findViewById(R.id.tv_visitDate);
            doctorName = itemView.findViewById(R.id.tv_doctorName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ChildMedicalHistory childMedicalHistory = medicalHistories.get(getAdapterPosition());
            int medID = childMedicalHistory.getMedID();
            String date= AppUtils.getFormattedDateString(childMedicalHistory.getVisitDate());

            Log.d(TAG, "onClick: ");

        }
    }

}
