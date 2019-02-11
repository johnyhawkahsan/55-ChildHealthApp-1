package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

/*
 * A Repository is a class that abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries, but is a suggested best practice for code separation and architecture.
 * A Repository class handles data operations. It provides a clean API to the rest of the app for app data
 */

public class ChildRepository implements AsyncResult{

    private static final String TAG = ChildRepository.class.getSimpleName();

    // We declare a MutableLiveData variable named searchResults into which the results of a search operation are stored whenever a asynchronous search task completes
    private MutableLiveData<List<Child>> searchResults = new MutableLiveData<>();
    private ChildDao childDao;
    private LiveData<List<Child>> mAllChilds;
    // We declare a MutableLiveData variable named searchResults into which the results of a search operation are stored whenever a asynchronous search task completes
    private MutableLiveData<Child> childSearchResult = new MutableLiveData<>();

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

    // methods that the ViewModel can call to obtain a references to the searchResults live data objects
    public MutableLiveData<Child> getSearchResults() {
        return childSearchResult;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Child child){
        new insertAsyncTask(childDao).execute(child);
    }

    public void deleteChildWithID(int chID) {
        new deleteAsyncTask(childDao).execute(chID);
    }

    public void deleteAllChilds() {
        new deleteAllAsyncTask(childDao).execute();
    }


    public void findChildWithID(int chID) {
        queryAsyncTask task = new queryAsyncTask(childDao);
        task.delegate = this;
        task.execute(chID);
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


    // We pass this "Integer" primitive type and in return, we receive Child object
    private static class queryAsyncTask extends AsyncTask<Integer, Void, Child> {
        private static final String TAG = queryAsyncTask.class.getSimpleName();

        private ChildDao asyncTaskDao;
        private ChildRepository delegate = null;

        // constructor method needs to be be passed a reference to the DAO object
        queryAsyncTask(ChildDao dao) {
            asyncTaskDao = dao;
        }

        // passed a String containing the product name for which the search is to be performed, passes it to the findProduct() method of the DAO and returns a list of matching Product entity objects findProduct() method of the DAO
        @Override
        protected Child doInBackground(Integer... params) {
            Log.d(TAG, "doInBackground: chID in params = " + params[0]);
            return asyncTaskDao.getChildWithID(params[0]);
        }

        @Override
        protected void onPostExecute(Child child) {
            delegate.asyncFinished(child); // returned child result is interfaced through asyncFinished method
        }
    }

    // This is our interface's implemented method. It takes child object from onPostExecute and help us set value for childSearchResult.
    @Override
    public void asyncFinished(Child foundChild) {
        Log.d(TAG, "asyncFinished: foundChild.getChID() = " + foundChild.getChID());
        childSearchResult.setValue(foundChild); // setValue is a special method for "MutableLive Data". We set the found child value to childSearhResult object
    }



    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private ChildDao asyncTaskDao;

        deleteAsyncTask(ChildDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            Log.d(TAG, "doInBackground: delete child with chID = " + params[0]);
            asyncTaskDao.deleteChildWithID(params[0]);
            return null;
        }
    }



    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private ChildDao asyncTaskDao;

        deleteAllAsyncTask(ChildDao dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: delete all childs");
            asyncTaskDao.deleteAll();
            return null;
        }
    }



    // I intentionally left this task as static from (anita-1990) sample to check this functionality as well
    public void updateTask(final Child child) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                childDao.update(child);
                Log.d("updateTask", "doInBackground: updating child with chID = " + child.getChID());
                return null;
            }
        }.execute();
    }







}
