package com.johnyhawkdesigns.a55_childhealthapp_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class VacRecordAdapter extends RecyclerView.Adapter<VacRecordAdapter.VacRecordViewHolder> {

    private static final String TAG = VacRecordAdapter.class.getSimpleName();

    Context mContext;
    private final LayoutInflater mInflator;
    private List<ChildVaccinationRecord> vaccinationRecords;


    // interface implemented by VaccinationActivity to respond when the user touches an item in the RecyclerView.
    public interface VacRecordCheckListener {
        void onCheck(int chID, int vacID);
    }

    private final VacRecordCheckListener vacRecordCheckListener;

    // Constructor
    public VacRecordAdapter(Context mContext, VacRecordCheckListener vacRecordCheckListener) {
        this.mContext = mContext;
        this.vacRecordCheckListener = vacRecordCheckListener;
        mInflator = LayoutInflater.from(mContext);
    }

    public void setVacRecordList(List<ChildVaccinationRecord> vacRecordList) {
        this.vaccinationRecords = vacRecordList;
        notifyDataSetChanged();
    }

    //VacRecordViewHolder class
    class VacRecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView dose, doseTime, vac1, vac2, vac_date;
        private final CheckBox vacCheckbox;
        private final ImageView vacStatus;

        public VacRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            dose = itemView.findViewById(R.id.dose);
            doseTime = itemView.findViewById(R.id.doseTime);
            vac1 = itemView.findViewById(R.id.vac1);
            vac2 = itemView.findViewById(R.id.vac2);
            vac_date = itemView.findViewById(R.id.vac_date);
            vacCheckbox = itemView.findViewById(R.id.vacCheckbox);
            vacStatus = itemView.findViewById(R.id.vacStatus);

            itemView.setOnClickListener(this);
            vacCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Log.d(TAG, "onCheckedChanged: isChecked = " + isChecked);
                }
            });
        }

        @Override
        public void onClick(View v) {
            ChildVaccinationRecord childVaccinationRecord = vaccinationRecords.get(getAdapterPosition());
            int chID = childVaccinationRecord.getForeignChID();
            int vacID = childVaccinationRecord.getVacID();

            AppUtils.showMessage(mContext, "Clicked on VacRecord with chID = " + chID + ", vacID = " + vacID);
            Log.d(TAG, "onClick: Clicked on VacRecord with chID = " + chID + ", vacID = " + vacID);


        }
    }


    @NonNull
    @Override
    public VacRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflator.inflate(R.layout.vaccination_cardview, viewGroup, false);
        return new VacRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacRecordViewHolder vacRecordViewHolder, int position) {
        ChildVaccinationRecord childVaccinationRecord = vaccinationRecords.get(position);
        vacRecordViewHolder.dose.setText("Dose - " + String.valueOf(childVaccinationRecord.getVacID()));
        vacRecordViewHolder.doseTime.setText(childVaccinationRecord.getDoseTime());
        vacRecordViewHolder.vac1.setText(childVaccinationRecord.getVac1());
        vacRecordViewHolder.vac2.setText(childVaccinationRecord.getVac2());
        if (childVaccinationRecord.getVacDone() == false){
            vacRecordViewHolder.vac_date.setText("Upcoming Vac Date = " + AppUtils.getFormattedDateString(childVaccinationRecord.getVacDueDate()));
            // Set cross button for ImageView
            Glide
                    .with(mContext)
                    .load(R.drawable.ic_close)
                    .into(vacRecordViewHolder.vacStatus);
        } else {
            vacRecordViewHolder.vac_date.setText("Vac Done On = " + AppUtils.getFormattedDateString(childVaccinationRecord.getVacDoneDate()));
            // Set checked image for completed vaccination
            Glide
                    .with(mContext)
                    .load(R.drawable.ic_checked)
                    .into(vacRecordViewHolder.vacStatus);
        }

    }

    @Override
    public int getItemCount() {
        if (vaccinationRecords == null)
            return 0;
        else
            return  vaccinationRecords.size();
    }

}