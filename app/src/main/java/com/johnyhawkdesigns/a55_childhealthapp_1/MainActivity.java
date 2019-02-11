package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.adapter.ChildListAdapter;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_CREATE_CHILD = 1;
    private static final int RC_UPDATE_CHILD = 2;
    //private static final int RC_DELETE_CHILD = 3; // Not needed. I tested but I'm launching delete method from AlertDialog builder, but it should be launched using startActivityForResult



    private ChildViewModel childViewModel;

    private TextView emptyTextView;
    private RecyclerView recyclerView;
    // private List<Child> childList; // We don't use this list here anymore, because we are directly assigning childList in Adapter to Observers returned list via childListAdapter.setChildList
    private ChildListAdapter childListAdapter;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTextView =  findViewById(R.id.tv__empty);

        //Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        childViewModel = new ChildViewModel(getApplication());

        childListAdapter = new ChildListAdapter(getApplicationContext(), new ChildListAdapter.ChildClickListener() {
            @Override
            public void onClick(int chID) {
                //Child foundChild = childViewModel.findChildWithID(chID);
                Log.d(TAG, "onClick: chID received in MainActivity = " + chID);

                Intent intent = new Intent(getApplicationContext(), ChildDetailActivity.class);
                intent.putExtra("chID", chID);
                getApplicationContext().startActivity(intent);
            }
        });
        recyclerView.setAdapter(childListAdapter);

        childViewModel.getAllChilds().observe(this, new Observer<List<Child>>() {
            @Override
            public void onChanged(@Nullable List<Child> children) {
                Log.d(TAG, "onChanged: child list size = " + children.size());

                // Loop through all returned list items and display in logs
                for (int i = 0; i < children.size(); i++ ){
                    Log.d(TAG, "chID = " + children.get(i).getChID() + ", child name = " + children.get(i).getName());
                }


                childListAdapter.setChildList(children);

                if (children.size() > 0 ) {
                    emptyTextView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.VISIBLE);
                }

            }
        });






        //When Floating button is clicked, we are redirected to AddEditChildActivity
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditChildActivity.class);
                startActivityForResult(intent, RC_CREATE_CHILD);

                AppUtils.showMessage(getApplicationContext(), "Launching AddNewChild Activity");

                Log.d(TAG, "onClick: launching AddEditChildActivity");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: resultCode = " + resultCode);

        //childListAdapter.notifyDataSetChanged();

        if (requestCode == RC_CREATE_CHILD && resultCode == RESULT_OK) {
            Log.d(TAG, "New child added. onActivityResult: requestCode = " + RC_CREATE_CHILD  + ", resultCode = " + resultCode);
            // Whenever new child is added, update our list
            childListAdapter.notifyDataSetChanged();
        } else if (requestCode == RC_UPDATE_CHILD && resultCode == RESULT_OK){
            Log.d(TAG, "Child has been updated. onActivityResult: requestCode = " + RC_CREATE_CHILD  + ", resultCode = " + resultCode);

            childListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_delete:
                Log.d(TAG, "onOptionsItemSelected: delete all children profiles");

                // Build alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Do you want to delete all children data??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.showMessage(MainActivity.this, "Delete all children success" );
                        childViewModel.deleteAllChilds();
                        childListAdapter.notifyDataSetChanged();
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
