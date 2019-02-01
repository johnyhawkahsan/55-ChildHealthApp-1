package com.johnyhawkdesigns.a55_childhealthapp_1.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

import java.util.List;

@Dao
public interface ChildDao {


    /*
    // TypeConverter compatible date function sample
    // https://developer.android.com/training/data-storage/room/referencing-data#java
    @Dao
    public interface UserDao {
        @Query("SELECT * FROM user WHERE birthday BETWEEN :from AND :to")
        List<User> findUsersBornBetweenDates(Date from, Date to);
    }
    */


    @Query("Select * FROM child_table ORDER BY ChID DESC")
    //Child[] loadAll(); // We don't use this because we want to retain observability
    LiveData<List<Child>> getAllChild();


    @Query("SELECT * FROM child_table WHERE ChID = :ChID")
    Child getChildWithID(int ChID);

    //Find child by name
    @Query("SELECT * FROM child_table WHERE name LIKE '%' || :name || '%'")
    LiveData<List<Child>> findChildByName(String name);

    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Child... child);

    @Update
    void update(Child child);

    @Delete
    void delete(Child child);

    @Query("DELETE FROM child_table WHERE ChID = :ChID")
    void deleteChildWithID(int ChID);

    @Query("DELETE FROM child_table")
    void deleteAll();

}
