package com.johnyhawkdesigns.a55_childhealthapp_1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//For tutorial, visit = https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a
@Entity(foreignKeys = @ForeignKey(entity = Child.class,
        parentColumns = "chID",
        childColumns = "foreignChID",
        onDelete = CASCADE)) //onDelete = CASCADE tells if child row will be deleted, we’d like to delete also all of it repositories.
public class ChildMedicalHistory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medID")
    private int medID;

    @ColumnInfo(name = "foreignChID")
    private int foreignChID;

    @Nullable
    @ColumnInfo(name = "doctorName")
    private String doctorName;

    @Nullable
    @ColumnInfo(name = "visitDate")
    private Date visitDate;

    @Nullable
    @ColumnInfo(name = "diseaseDec")
    private String diseaseDec;

    @Nullable
    @ColumnInfo(name = "prescMedicine")
    private String prescMedicine;

    @Nullable
    @ColumnInfo(name = "imagePath")
    private String imagePath;



}
