package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildMedicalHistoryDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;

import java.util.List;

public class MedHistoryRepository implements AsyncResultMedHistory{

    private static final String TAG = MedHistoryRepository.class.getSimpleName();


    private ChildMedicalHistoryDao medicalHistoryDao;
    private LiveData<List<ChildMedicalHistory>> mAllMedicalHistories; // get all medical histories asssociated with this chID

    // We declare a MutableLiveData variable named childSearchResult into which the results of a search operation are stored whenever a asynchronous search task completes
    private MutableLiveData<ChildMedicalHistory> medicalHistorySearchResult = new MutableLiveData<>();

    // constructor
    public MedHistoryRepository(Application application, int chID) {
        ChildRoomDatabase childRoomDatabase = ChildRoomDatabase.getDBINSTANCE(application);
        medicalHistoryDao = childRoomDatabase.medicalHistoryDao();
        mAllMedicalHistories = medicalHistoryDao.loadAllMedHistoryOfChild(chID); // get history of this specific child
    }

    public LiveData<List<ChildMedicalHistory>> getAllMedicalHistories() {
        return mAllMedicalHistories;
    }

    public MutableLiveData<ChildMedicalHistory> getMedicalHistorySearchResult() {
        return medicalHistorySearchResult;
    }

    public void insert(ChildMedicalHistory childMedicalHistory){
        new insertAsyncTask(medicalHistoryDao).execute(childMedicalHistory);
    }

    public void deleteMedHistoryWithID(int medID){
        new deleteAsyncTask(medicalHistoryDao).execute(medID);
    }

    public void deleteAllMedicalHistories(int chID){
        new deleteAllAsyncTask(medicalHistoryDao).execute(chID);
    }

    public void findMedicalHistoryWithID(int medID){
        queryAsyncTask task = new queryAsyncTask(medicalHistoryDao);
        task.delegate = this;
        task.execute(medID);
    }



    private static class insertAsyncTask extends AsyncTask<ChildMedicalHistory, Void, Void> {
        private ChildMedicalHistoryDao mAsyncTaskDao;

        //Constructor
        insertAsyncTask(ChildMedicalHistoryDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ChildMedicalHistory... params) {
            mAsyncTaskDao.insertMedHistory(params[0]);//Insert
            return null;
        }
    }


    // We pass this "Integer" primitive type and in return, we receive Child object
    private static class queryAsyncTask extends AsyncTask<Integer, Void, ChildMedicalHistory> {
        private static final String TAG = queryAsyncTask.class.getSimpleName();

        private ChildMedicalHistoryDao asyncTaskDao;
        private MedHistoryRepository delegate = null;

        // constructor method needs to be be passed a reference to the DAO object
        queryAsyncTask(ChildMedicalHistoryDao dao) {
            asyncTaskDao = dao;
        }

        // passed a String containing the product name for which the search is to be performed, passes it to the findProduct() method of the DAO and returns a list of matching Product entity objects findProduct() method of the DAO
        @Override
        protected ChildMedicalHistory doInBackground(Integer... params) {
            Log.d(TAG, "doInBackground: chID in params = " + params[0]);
            return asyncTaskDao.getHistoryWithMedID(params[0]);
        }

        @Override
        protected void onPostExecute(ChildMedicalHistory childMedicalHistory) {
            delegate.asyncFinished(childMedicalHistory); // returned child result is interfaced through asyncFinished method
        }
    }

    @Override
    public void asyncFinished(ChildMedicalHistory foundMedHistory) {
        Log.d(TAG, "asyncFinished: foundMedHistory.getMedID() = " + foundMedHistory.getMedID());
        medicalHistorySearchResult.setValue(foundMedHistory);// setValue is a special method for "MutableLive Data". We set the found foundMedHistory value to medicalHistorySearchResult object
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private ChildMedicalHistoryDao asyncTaskDao;

        deleteAsyncTask(ChildMedicalHistoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            Log.d(TAG, "doInBackground: delete child with chID = " + params[0]);
            asyncTaskDao.deleteMedHistoryWithID(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Integer, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private ChildMedicalHistoryDao asyncTaskDao;

        deleteAllAsyncTask(ChildMedicalHistoryDao dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Integer... params) {
            Log.d(TAG, "doInBackground: delete all childs");
            asyncTaskDao.deleteAllMedicalHistories(params[0]);
            return null;
        }
    }

    // I intentionally left this task as static from (anita-1990) sample to check this functionality as well
    public void updateTask(final ChildMedicalHistory childMedicalHistory) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                medicalHistoryDao.updateMedHistory(childMedicalHistory);
                Log.d("updateTask", "doInBackground: updating childMedicalHistory with medID = " + childMedicalHistory.getMedID());
                return null;
            }
        }.execute();
    }


}
