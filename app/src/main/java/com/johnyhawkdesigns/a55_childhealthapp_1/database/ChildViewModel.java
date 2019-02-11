package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

import java.util.List;

public class ChildViewModel extends AndroidViewModel{

    //Add a private member variable to hold a reference to the repository.
    private ChildRepository childRepository;

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Child>> mAllChilds;
    private MutableLiveData<Child> searchResults;


    // Constructor of ViewModel
    public ChildViewModel(@NonNull Application application) {
        super(application);
        childRepository = new ChildRepository(application);
        mAllChilds = childRepository.getAllChilds(); // This will hold all child records
        searchResults = childRepository.getSearchResults(); // This will hold searchResults
    }

    //Getter method for getting all words.
    public LiveData<List<Child>> getAllChilds(){
        return mAllChilds;
    }

    //Insert method
    public void insert(Child child){
        childRepository.insert(child);
    }

    public void update(Child child){
        childRepository.updateTask(child);
    }


    // We first call this method to begin searchin in ChildRepository, and then returned results are stored within search result returned by AsyncTask
    public void findChildWithID(int chID) {
        childRepository.findChildWithID(chID);
    }


    public MutableLiveData<Child> getSearchResults(){
        return searchResults;
    }

    public void deleteChildWithID(int chID) {
        childRepository.deleteChildWithID(chID);
    }

    public void deleteAllChilds() {
        childRepository.deleteAllChilds();
    }

}
