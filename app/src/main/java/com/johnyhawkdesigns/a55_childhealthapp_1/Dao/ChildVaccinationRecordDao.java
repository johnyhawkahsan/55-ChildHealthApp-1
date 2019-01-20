package com.johnyhawkdesigns.a55_childhealthapp_1.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

import java.util.List;

public interface ChildVaccinationRecordDao {

    @Query("Select * FROM vacRecord_table")
    LiveData<List<ChildVaccinationRecord>>[] loadAllVacRecords();

    @Query("Select * FROM vacRecord_table WHERE foreignChID == :foreignChID")
    LiveData<ChildVaccinationRecord> loadVacRecordOfChild(int foreignChID);

    @Insert
    void insert(ChildVaccinationRecord... childVaccinationRecords);

    @Update
    void update(ChildVaccinationRecord... childVaccinationRecords);

    @Delete
    void delete(ChildVaccinationRecord... childVaccinationRecords);

    @Query("DELETE FROM vacRecord_table")
    void deleteAllVacRecords();


}
