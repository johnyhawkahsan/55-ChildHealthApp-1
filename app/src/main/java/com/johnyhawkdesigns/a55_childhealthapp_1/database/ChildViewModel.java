package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
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

    public ChildViewModel(@NonNull Application application) {
        super(application);
        childRepository = new ChildRepository(application);
        mAllChilds = childRepository.getAllChilds();
    }

    //Getter method for getting all words.
    public LiveData<List<Child>> getAllChilds(){
        return mAllChilds;
    }

    //Insert method
    public void insert(Child child){
        childRepository.insert(child);
    }

    public void findProduct(String name) {
        childRepository.findChild(name);
    }

    public void deleteProduct(String name) {
        childRepository.deleteChild(name);
    }

}
