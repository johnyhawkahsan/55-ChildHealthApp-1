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


    public int getVacID() {
        return vacID;
    }

    public void setVacID(int vacID) {
        this.vacID = vacID;
    }

    public int getForeignChID() {
        return foreignChID;
    }

    public void setForeignChID(int foreignChID) {
        this.foreignChID = foreignChID;
    }

    @Nullable
    public String getVacType() {
        return vacType;
    }

    public void setVacType(@Nullable String vacType) {
        this.vacType = vacType;
    }

    @Nullable
    public String getVacDone() {
        return vacDone;
    }

    public void setVacDone(@Nullable String vacDone) {
        this.vacDone = vacDone;
    }

    @Nullable
    public Date getVacDoneDate() {
        return vacDoneDate;
    }

    public void setVacDoneDate(@Nullable Date vacDoneDate) {
        this.vacDoneDate = vacDoneDate;
    }

    @Nullable
    public Date getVacDueDate() {
        return vacDueDate;
    }

    public void setVacDueDate(@Nullable Date vacDueDate) {
        this.vacDueDate = vacDueDate;
    }

    @Nullable
    public String getDose() {
        return dose;
    }

    public void setDose(@Nullable String dose) {
        this.dose = dose;
    }
}
