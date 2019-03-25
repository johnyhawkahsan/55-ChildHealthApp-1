package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;

import java.util.List;

public class MedHistoryViewModel extends AndroidViewModel{

    private MedHistoryRepository medHistoryRepository;

    private LiveData<List<ChildMedicalHistory>> mAllMedicalHistories;
    private MutableLiveData<ChildMedicalHistory> searchResults;

    // Constructor 1 of ViewModel with chID
    public MedHistoryViewModel(@NonNull Application application, int chID) {
        super(application);
        medHistoryRepository = new MedHistoryRepository(application, chID);
        mAllMedicalHistories = medHistoryRepository.getAllMedicalHistories();
        searchResults = medHistoryRepository.getMedicalHistorySearchResult();
    }

    // Constructor 2 of ViewModel without chID
    public MedHistoryViewModel(@NonNull Application application) {
        super(application);
        medHistoryRepository = new MedHistoryRepository(application);
        searchResults = medHistoryRepository.getMedicalHistorySearchResult();
    }

    public LiveData<List<ChildMedicalHistory>> getAllMedicalHistories() {
        return mAllMedicalHistories;
    }

    //Insert method
    public void insert(ChildMedicalHistory childMedicalHistory){
        medHistoryRepository.insert(childMedicalHistory);
    }

    public void update(ChildMedicalHistory childMedicalHistory){
        medHistoryRepository.updateTask(childMedicalHistory);
    }

    public void findMedHistoryWithID(int medID, int chID){
        medHistoryRepository.findMedicalHistoryWithID(medID, chID);
    }

    public MutableLiveData<ChildMedicalHistory> getSearchResults() {
        return searchResults;
    }

    public void deleteMedHistoryWithID(int chID, int medID){
        medHistoryRepository.deleteMedHistoryWithID(chID, medID);
    }

    public void deleteAllMedHistories(int chID){
        medHistoryRepository.deleteAllMedicalHistories(chID);
    }



}
