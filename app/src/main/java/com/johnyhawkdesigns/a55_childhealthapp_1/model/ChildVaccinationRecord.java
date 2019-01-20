package com.johnyhawkdesigns.a55_childhealthapp_1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "vacRecord_table",
        foreignKeys = @ForeignKey(entity = Child.class,
                parentColumns = "chID",
                childColumns = "foreignChID",
                onDelete = CASCADE)) //onDelete = CASCADE tells if child row will be deleted, we’d like to delete also all of it repositories.
public class ChildVaccinationRecord {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vacID")
    private int vacID;

    // This column stores foreign key as chID. We can also use @ForeignKey here but we used it on start
    @ColumnInfo(name = "foreignChID")
    private int foreignChID;

    @Nullable
    @ColumnInfo(name = "vacType")
    private String vacType;

    @Nullable
    @ColumnInfo(name = "vacDone")
    private String vacDone;

    @Nullable
    @ColumnInfo(name = "vacDoneDate")
    private Date vacDoneDate;

    @Nullable
    @ColumnInfo(name = "vacDueDate")
    private Date vacDueDate;

    @Nullable
    @ColumnInfo(name = "dose")
    private String dose;


}
