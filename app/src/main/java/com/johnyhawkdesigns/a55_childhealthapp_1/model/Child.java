package com.johnyhawkdesigns.a55_childhealthapp_1.model;



import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity(tableName = "child_table")
public class Child implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chID")
    private int chID;

    @Nullable
    @ColumnInfo(name = "name")
    private String name;

    @Nullable
    @ColumnInfo(name = "gender")
    private String gender;

    @Nullable
    @ColumnInfo(name = "bloodGroup")
    private String bloodGroup;

    @Nullable
    @ColumnInfo(name = "imagePath")
    private String imagePath;

    @Nullable
    @ColumnInfo(name = "dateOfBirth")
    private Date dateOfBirth;

    @Nullable
    @ColumnInfo(name = "profileUpdateDate")
    private Date profileUpdateDate;

    // Constructor
    public Child(@NonNull int chID, String name, String gender, String bloodGroup, String imagePath, Date dateOfBirth, Date profileUpdateDate) {
        this.chID = chID;
        this.name = name;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.imagePath = imagePath;
        this.dateOfBirth = dateOfBirth;
        this.profileUpdateDate = profileUpdateDate;
    }
}
