package com.johnyhawkdesigns.a55_childhealthapp_1.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;

import java.util.List;

@Dao
public interface ChildMedicalHistoryDao {

    @Query("Select * FROM medHistory_table WHERE foreignChID == :foreignChID ORDER BY medID DESC")
    LiveData<List<ChildMedicalHistory>> loadAllMedHistoryOfChild(int foreignChID);

    @Query("Select * FROM medHistory_table WHERE medID == :medID")
    ChildMedicalHistory getHistoryWithMedID(int medID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedHistory(ChildMedicalHistory... childMedicalHistories);

    @Update
    void updateMedHistory(ChildMedicalHistory... childMedicalHistories);

    @Delete
    void deleteMedHistory(ChildMedicalHistory... childMedicalHistories);

    @Query("DELETE FROM medHistory_table WHERE foreignChID = :ChID")
    void deleteAllHistoryOfChild(int ChID);

    @Query("DELETE FROM medHistory_table WHERE medID = :medID")
    void deleteMedHistoryWithID(int medID);

    @Query("DELETE FROM medHistory_table WHERE foreignChID = :chID")
    void deleteAllMedicalHistories(int chID);

}
