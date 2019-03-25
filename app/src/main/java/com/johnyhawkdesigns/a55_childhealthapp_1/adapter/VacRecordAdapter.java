package com.johnyhawkdesigns.a55_childhealthapp_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

import java.util.List;

public class VacRecordAdapter extends RecyclerView.Adapter<VacRecordAdapter.VacRecordViewHolder> {

    private static final String TAG = VacRecordAdapter.class.getSimpleName();

    Context mContext;
    private final LayoutInflater mInflator;
    private List<ChildVaccinationRecord> vaccinationRecords;

    // interface implemented by VaccinationActivity to respond when the user touches an item in the RecyclerView.
    public interface VacRecordCheckListener{
        void onCheck(int chID, int vacID);
    }

    private final VacRecordCheckListener vacRecordCheckListener;

    // Constructor
    public VacRecordAdapter(Context mContext, VacRecordCheckListener vacRecordCheckListener) {
        this.mContext = mContext;
        this.vacRecordCheckListener = vacRecordCheckListener;
        mInflator = LayoutInflater.from(mContext);
    }

    public void setVacRecordList(List<ChildVaccinationRecord> vacRecordList){
        this.vaccinationRecords = vacRecordList;
        notifyDataSetChanged();
    }

    //VacRecordViewHolder class
    class VacRecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView dose, doseTime, vac1, vac2, vac_date;
        private final CheckBox vacCheckbox;
        private final Button vacStatus;

        @Override
        public void onClick(View v) {

        }
    }










}
