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
    private LiveData<List<ChildVaccinationRecord>> mAllVacRecords; // get all vac records asssociated with this chID

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

    public void insert(ChildVaccinationRecord childVaccinationRecord){
        new insertAsyncTask(childVaccinationRecordDao).execute(childVaccinationRecord);
    }

    public void deleteVacRecordWithID(int chID, int medID){
        new deleteAsyncTask(childVaccinationRecordDao).execute(chID, medID);
    }

    public void deleteAllVacRecords(int chID){
        new deleteAllAsyncTask(childVaccinationRecordDao).execute(chID);
    }

    public void findVacRecordWithID(int medID, int chID){
        queryAsyncTask task = new queryAsyncTask(childVaccinationRecordDao);
        task.delegate = this;
        task.execute(medID, chID);
    }




    private static class insertAsyncTask extends AsyncTask<ChildVaccinationRecord, Void, Void> {
        private ChildVaccinationRecordDao mAsyncTaskDao;

        //Constructor
        insertAsyncTask(ChildVaccinationRecordDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ChildVaccinationRecord... params) {
            mAsyncTaskDao.insert(params[0]);//Insert
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
            int medID = params[0];
            int chID = params[1];
            Log.d(TAG, "doInBackground: chID = " + chID + ", medID = " + medID);
            return asyncTaskDao.getChildVacRecordWithID(medID,chID);
        }

        @Override
        protected void onPostExecute(ChildVaccinationRecord childVaccinationRecord) {
            delegate.asyncFinished(childVaccinationRecord); // returned child result is interfaced through asyncFinished method
        }
    }

    @Override
    public void asyncFinished(ChildVaccinationRecord foundVacRecord) {

    }









































}
