package com.johnyhawkdesigns.a55_childhealthapp_1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.johnyhawkdesigns.a55_childhealthapp_1.database.typeConverters.DateTypeConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/*
@Entity(tableName = "vacRecord_table",
        foreignKeys = @ForeignKey(entity = Child.class,
                parentColumns = "chID",
                childColumns = "foreignChID",
                onDelete = CASCADE)) //onDelete = CASCADE tells if child row will be deleted, we’d like to delete also all of it repositories.

 */
@Entity(tableName = "vacRecord_table")
public class ChildVaccinationRecord {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vacID")
    private int vacID;

    // This column stores foreign key as chID. We can also use @ForeignKey here but we used it on start
    @ColumnInfo(name = "foreignChID")
    private int foreignChID;

    @Nullable
    @ColumnInfo(name = "dose")
    private String dose;

    @Nullable
    @ColumnInfo(name = "doseTime")
    private String doseTime;

    @Nullable
    @ColumnInfo(name = "vac1")
    private String vac1;

    @Nullable
    @ColumnInfo(name = "vac2")
    private String vac2;

    @Nullable
    @ColumnInfo(name = "vacDone")
    private Boolean vacDone;

    @Nullable
    @ColumnInfo(name = "vacDueDate")
    @TypeConverters({DateTypeConverter.class})
    private Date vacDueDate;

    @Nullable
    @ColumnInfo(name = "vacDoneDate")
    @TypeConverters({DateTypeConverter.class})
    private Date vacDoneDate;

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
    public String getDose() {
        return dose;
    }

    public void setDose(@Nullable String dose) {
        this.dose = dose;
    }

    @Nullable
    public String getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(@Nullable String doseTime) {
        this.doseTime = doseTime;
    }

    @Nullable
    public String getVac1() {
        return vac1;
    }

    public void setVac1(@Nullable String vac1) {
        this.vac1 = vac1;
    }

    @Nullable
    public String getVac2() {
        return vac2;
    }

    public void setVac2(@Nullable String vac2) {
        this.vac2 = vac2;
    }

    @Nullable
    public Boolean getVacDone() {
        return vacDone;
    }

    public void setVacDone(@Nullable Boolean vacDone) {
        this.vacDone = vacDone;
    }

    @Nullable
    public Date getVacDueDate() {
        return vacDueDate;
    }

    public void setVacDueDate(@Nullable Date vacDueDate) {
        this.vacDueDate = vacDueDate;
    }

    @Nullable
    public Date getVacDoneDate() {
        return vacDoneDate;
    }

    public void setVacDoneDate(@Nullable Date vacDoneDate) {
        this.vacDoneDate = vacDoneDate;
    }

    // Empty constructor
    public ChildVaccinationRecord() {
    }

    @Ignore
    public ChildVaccinationRecord(int foreignChID, String dose, String doseTime, String vac1, String vac2) {
        this.foreignChID = foreignChID;
        this.dose = dose;
        this.doseTime = doseTime;
        this.vac1 = vac1;
        this.vac2 = vac2;
    }
}
