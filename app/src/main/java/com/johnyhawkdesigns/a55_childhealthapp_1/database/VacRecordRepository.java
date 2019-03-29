package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildVaccinationRecordDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

import java.util.List;

public class VacRecordRepository implements AsyncResultVacRecord {

    private static final String TAG = VacRecordRepository.class.getSimpleName();


    private ChildVaccinationRecordDao childVaccinationRecordDao;
    private LiveData<List<ChildVaccinationRecord>> mAllVacRecords; // get all vac records associated with this chID

    // We declare a MutableLiveData variable named medicalHistorySearchResult into which the results of a search operation are stored whenever a asynchronous search task completes
    private MutableLiveData<ChildVaccinationRecord> vacRecordsSearchResult = new MutableLiveData<>();

    // constructor 1 with chID
    public VacRecordRepository(Application application, int chID) {
        ChildRoomDatabase childRoomDatabase = ChildRoomDatabase.getDBINSTANCE(application);
        childVaccinationRecordDao = childRoomDatabase.childVaccinationRecordDao();
        mAllVacRecords = childVaccinationRecordDao.loadVacRecordOfChild(chID);
    }


    // constructor 2 without chID
    public VacRecordRepository(Application application) {
        ChildRoomDatabase childRoomDatabase = ChildRoomDatabase.getDBINSTANCE(application);
        childVaccinationRecordDao = childRoomDatabase.childVaccinationRecordDao();
    }

    public LiveData<List<ChildVaccinationRecord>> getAllVacRecords() {
        return mAllVacRecords;
    }

    public MutableLiveData<ChildVaccinationRecord> getMedicalHistorySearchResult() {
        return vacRecordsSearchResult;
    }

    public void insert(ChildVaccinationRecord... childVaccinationRecord){
        new insertAsyncTask(childVaccinationRecordDao).execute(childVaccinationRecord);
    }

    public void deleteVacRecordWithID(int chID, int medID){
        new deleteAsyncTask(childVaccinationRecordDao).execute(chID, medID);
    }

    public void deleteAllVacRecords(int chID){
        new deleteAllAsyncTask(childVaccinationRecordDao).execute(chID);
    }

    public void findVacRecordWithID(int chID, int vacID){
        queryAsyncTask task = new queryAsyncTask(childVaccinationRecordDao);
        task.delegate = this;
        task.execute(chID, vacID);
    }




    private static class insertAsyncTask extends AsyncTask<ChildVaccinationRecord, Void, Void> {
        private ChildVaccinationRecordDao mAsyncTaskDao;

        //Constructor
        insertAsyncTask(ChildVaccinationRecordDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ChildVaccinationRecord... params) {
            mAsyncTaskDao.insert(params);//Insert
            return null;
        }
    }

    // We pass this "Integer" primitive type and in return, we receive Child object
    private static class queryAsyncTask extends AsyncTask<Integer, Void, ChildVaccinationRecord> {
        private static final String TAG = queryAsyncTask.class.getSimpleName();

        private ChildVaccinationRecordDao asyncTaskDao;
        private VacRecordRepository delegate = null;

        // constructor method needs to be be passed a reference to the DAO object
        queryAsyncTask(ChildVaccinationRecordDao dao) {
            asyncTaskDao = dao;
        }

        // passed a String containing the product name for which the search is to be performed, passes it to the findProduct() method of the DAO and returns a list of matching Product entity objects findProduct() method of the DAO
        @Override
        protected ChildVaccinationRecord doInBackground(Integer... params) {
            int chID = params[0];
            int vacID = params[1];
            Log.d(TAG, "doInBackground: chID = " + chID + ", vacID = " + vacID);
            return asyncTaskDao.getChildVacRecordWithID(chID,vacID);
        }

        @Override
        protected void onPostExecute(ChildVaccinationRecord childVaccinationRecord) {
            delegate.asyncFinished(childVaccinationRecord); // returned child result is interfaced through asyncFinished method
        }
    }

    @Override
    public void asyncFinished(ChildVaccinationRecord foundVacRecord) {
        Log.d(TAG, "asyncFinished: foundVacRecord.getVacID() = " + foundVacRecord.getVacID() );
        vacRecordsSearchResult.setValue(foundVacRecord);
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private ChildVaccinationRecordDao asyncTaskDao;

        deleteAsyncTask(ChildVaccinationRecordDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            int chID = params[0];
            int vacID = params[1];
            Log.d(TAG, "doInBackground: delete VacRecord with chID = " + chID + ", vacID = " + vacID);
            asyncTaskDao.deleteVacRecordWithID(chID, vacID);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Integer, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private ChildVaccinationRecordDao asyncTaskDao;

        deleteAllAsyncTask(ChildVaccinationRecordDao dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Integer... params) {
            int chID = params[0];
            Log.d(TAG, "doInBackground: delete all VacRecords of this chID= " + chID);
            asyncTaskDao.deleteAllVacRecordsOfChild(chID);
            return null;
        }
    }

    // I intentionally left this task as static from (anita-1990) sample to check this functionality as well
    public void updateTask(final ChildVaccinationRecord childVaccinationRecord) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                childVaccinationRecordDao.update(childVaccinationRecord);
                Log.d("updateTask", "doInBackground: updating childVaccinationRecord with vacID = " + childVaccinationRecord.getVacID());
                return null;
            }
        }.execute();
    }








































}
