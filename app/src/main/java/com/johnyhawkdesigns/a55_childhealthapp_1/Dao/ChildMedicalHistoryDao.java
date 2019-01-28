package com.johnyhawkdesigns.a55_childhealthapp_1.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;

import java.util.List;

@Dao
public interface ChildMedicalHistoryDao {

    @Query("Select * FROM medHistory_table")
    //ChildMedicalHistory[] loadAllHistory();
    LiveData<List<ChildMedicalHistory>> loadAllHistory();

    @Query("Select * FROM medHistory_table WHERE foreignChID == :foreignChID")
    LiveData<ChildMedicalHistory> loadHistoryOfChild(int foreignChID);

    @Insert
    void insert(ChildMedicalHistory... childMedicalHistories);

    @Update
    void update(ChildMedicalHistory... childMedicalHistories);

    @Delete
    void delete(ChildMedicalHistory... childMedicalHistories);

    @Query("DELETE FROM medHistory_table")
    void deleteAllMedicalHistories();



}
