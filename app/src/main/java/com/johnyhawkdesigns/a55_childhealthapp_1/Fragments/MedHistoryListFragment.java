package com.johnyhawkdesigns.a55_childhealthapp_1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;

public class MedHistoryListFragment extends Fragment{

    // callback method implemented by MainActivity
    public interface MedHistoryListFragmentListener{

        // called when add button is pressed
        void onAddMedHistory();
    }

    // used to inform the MedHistoryActivity when a item is selected or added
    private MedHistoryListFragmentListener listener;

    private static final String TAG = MedHistoryListFragment.class.getSimpleName();

    private ChildViewModel childViewModel;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true); // fragment has menu items to display

        // inflate GUI and get reference to the RecyclerView
        View view =  inflater.inflate(R.layout.med_history_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMedHistory);

        Log.d(TAG, "onCreateView: ");

        //When Floating button is clicked, we are redirected to AddEditMedHistoryFragment
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addMedHistory);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddEditMedHistoryFragment
                listener.onAddMedHistory();
            }
        });


        return view;
    }

    // Fragment lifecycle method- set MedHistoryListFragmentListener when fragment attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MedHistoryListFragmentListener) context;
    }

    // Fragment lifecycle method- remove MedHistoryListFragmentListener when Fragment detached
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
