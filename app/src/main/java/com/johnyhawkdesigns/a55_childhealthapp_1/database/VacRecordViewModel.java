package com.johnyhawkdesigns.a55_childhealthapp_1.database;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

import java.util.List;

public class VacRecordViewModel extends AndroidViewModel{

    private VacRecordRepository vacRecordRepository;

    private LiveData<List<ChildVaccinationRecord>> mAllVacRecords;
    private MutableLiveData<ChildVaccinationRecord> searchResults;

    // Constructor of ViewModel with chID
    public VacRecordViewModel(@NonNull Application application, int chID) {
        super(application);
        vacRecordRepository = new VacRecordRepository(application, chID);
        mAllVacRecords = vacRecordRepository.getAllVacRecords();
        searchResults = vacRecordRepository.getMedicalHistorySearchResult();
    }

    // Constructor 2 of ViewModel without chID
    public VacRecordViewModel(@NonNull Application application) {
        super(application);
        vacRecordRepository = new VacRecordRepository(application);
        searchResults = vacRecordRepository.getMedicalHistorySearchResult();
    }

    public LiveData<List<ChildVaccinationRecord>> getAllVacRecords() {
        return mAllVacRecords;
    }

    public void insert(ChildVaccinationRecord childVaccinationRecord){
        vacRecordRepository.insert(childVaccinationRecord);
    }

    public void update(ChildVaccinationRecord childVaccinationRecord){
        vacRecordRepository.updateTask(childVaccinationRecord);
    }

    public void findVacRecordWithID(int chID, int vacID){
        vacRecordRepository.findVacRecordWithID(chID, vacID);
    }

    public MutableLiveData<ChildVaccinationRecord> getSearchResults() {
        return searchResults;
    }

    public void deleteVacRecordWithID(int chID, int vacID){
        vacRecordRepository.deleteVacRecordWithID(chID, vacID);
    }

    public void deleteAllMedHistories(int chID){
        vacRecordRepository.deleteAllVacRecords(chID);
    }




}
