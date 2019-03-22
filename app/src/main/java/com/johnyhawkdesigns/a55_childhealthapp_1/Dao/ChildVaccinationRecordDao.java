package com.johnyhawkdesigns.a55_childhealthapp_1.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

import java.util.List;

@Dao
public interface ChildVaccinationRecordDao {


    @Query("Select * FROM vacRecord_table WHERE foreignChID == :foreignChID ORDER BY vacID DESC")
    LiveData<List<ChildVaccinationRecord>> loadVacRecordOfChild(int foreignChID);

    @Query("Select * FROM vacRecord_table WHERE vacID == :vacID AND foreignChID == :foreignChID")
    ChildVaccinationRecord getChildVacRecordWithID(int foreignChID, int vacID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChildVaccinationRecord... childVaccinationRecords);

    @Update
    void update(ChildVaccinationRecord... childVaccinationRecords);

    @Delete
    void deleteVacRecord(ChildVaccinationRecord... childVaccinationRecords);

    @Query("DELETE FROM vacRecord_table WHERE foreignChID = :chID AND vacID = :vacID")
    void deleteVacRecordWithID(int chID, int vacID);

    @Query("DELETE FROM vacRecord_table WHERE foreignChID = :chID")
    void deleteAllVacRecordsOfChild(int chID);


}
