package com.johnyhawkdesigns.a55_childhealthapp_1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;


public class AddEditMedHistoryFragment extends android.support.v4.app.Fragment {

    //callback method implemented by MainActivity
    public interface AddEditFragmentListener{
        // called when medID is saved
        void onAddEditCompleted(int medID);
    }

    private AddEditFragmentListener addEditFragmentListener;


    private static final String TAG = AddEditMedHistoryFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.add_edit_med_record, container, false);




        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


}
