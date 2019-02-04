package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

public class ChildDetailActivity extends AppCompatActivity {

    private static final String TAG = ChildDetailActivity.class.getSimpleName();

    private int chID;
    private ChildViewModel childViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_child_details);

        chID = (int) getIntent().getSerializableExtra("chID");
        Log.d(TAG, "onCreate: received chID = " + chID);

        //Child child = childViewModel.getChildWithID(chID);
        //Log.d(TAG, "onCreate: child name = " + child.getName());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_child_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d(TAG, "onOptionsItemSelected: edit child profile");
                return true;
            case R.id.action_delete:
                Log.d(TAG, "onOptionsItemSelected: delete child profile");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
