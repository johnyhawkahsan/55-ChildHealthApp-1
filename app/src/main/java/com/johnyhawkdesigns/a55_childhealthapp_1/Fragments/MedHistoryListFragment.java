package com.johnyhawkdesigns.a55_childhealthapp_1.Fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.MainActivity;
import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.adapter.MedHistoryAdapter;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.MedHistoryViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class MedHistoryListFragment extends Fragment{

    // callback method implemented by MainActivity
    public interface MedHistoryListFragmentListener{

        void onMedHistorySelected(int chID, int medID);

        // called when add button is pressed
        void onAddMedHistory();
    }

    // used to inform the MedHistoryActivity when a item is selected or added
    private MedHistoryListFragmentListener listener;

    private static final String TAG = MedHistoryListFragment.class.getSimpleName();

    private TextView emptyTextView;
    private RecyclerView recyclerView;

    private MedHistoryAdapter medHistoryAdapter;

    private MedHistoryViewModel medHistoryViewModel;
    FloatingActionButton floatingActionButton;

    public int chID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true); // fragment has menu items to display

        chID = getArguments().getInt("chID"); // receive chID
        Log.d(TAG, "onCreateView: chID received = " + chID);

        // inflate GUI and get reference to the RecyclerView
        View view =  inflater.inflate(R.layout.med_history_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMedHistory);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //recyclerView.addItemDecoration();

        emptyTextView = view.findViewById(R.id.tv__empty_medHistory);

        medHistoryViewModel = new MedHistoryViewModel(getActivity().getApplication(), chID);

        medHistoryAdapter = new MedHistoryAdapter(getActivity(), new MedHistoryAdapter.MedHistoryClickListener() {
            @Override
            public void onClick(int chID, int medID) {
                Log.d(TAG, "onClick: received chID = " + chID + ", medID = " + medID);
                listener.onMedHistorySelected(chID, medID);
            }
        });

        recyclerView.setAdapter(medHistoryAdapter);

        medHistoryViewModel.getAllMedicalHistories().observe(this, new Observer<List<ChildMedicalHistory>>() {
            @Override
            public void onChanged(@Nullable List<ChildMedicalHistory> childMedicalHistories) {
                Log.d(TAG, "onChanged: med history list size = " + childMedicalHistories.size());

                // Loop through all returned list items and display in logs
                for (int i = 0; i < childMedicalHistories.size(); i++){
                    Log.d(TAG, "medID = " + childMedicalHistories.get(i).getMedID());
                }

                medHistoryAdapter.setMedicalHistoryList(childMedicalHistories);

                if (childMedicalHistories.size() > 0){
                    emptyTextView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.VISIBLE);
                }

            }
        });


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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_med_record, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delete_med_record:
                Log.d(TAG, "onOptionsItemSelected: delete all med record");

                // Build alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Do you want to delete all medical records??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.showMessage(getActivity(), "Delete all medical records success" );
                        medHistoryViewModel.deleteAllMedHistories(chID);
                        medHistoryAdapter.notifyDataSetChanged();
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
