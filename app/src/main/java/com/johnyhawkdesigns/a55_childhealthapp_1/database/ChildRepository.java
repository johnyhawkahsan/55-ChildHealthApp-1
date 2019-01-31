package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

/*
 * A Repository is a class that abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries, but is a suggested best practice for code separation and architecture.
 * A Repository class handles data operations. It provides a clean API to the rest of the app for app data
 */

public class ChildRepository {

    private ChildDao childDao;
    private LiveData<List<Child>> mAllChilds;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ChildRepository(Application application){
        ChildRoomDatabase childRoomDatabase = ChildRoomDatabase.getDBINSTANCE(application); //Created ChildRoomDatabase using singleton pattern.
        childDao = childRoomDatabase.childDAO(); // we receive childDAO from childRoomDatabase
        mAllChilds = childDao.getAllChild(); // We are receiving all childs data in constructor
    }



    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Child>> getAllChilds(){
        return mAllChilds;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Child child){
        new insertAsyncTask(childDao).execute(child);
    }


    public void updateTask(final Child child) {
        child.setProfileUpdateDate(AppUtils.getCurrentDateTime()); // Set time to new time.

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                childDao.update(child);
                return null;
            }
        }.execute();
    }


    private static class insertAsyncTask extends AsyncTask<Child, Void, Void> {
        private ChildDao mAsyncTaskDao;

        //Constructor
        insertAsyncTask(ChildDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Child... params) {
            mAsyncTaskDao.insert(params[0]);//Insert
            return null;
        }
    }







}
