package com.johnyhawkdesigns.a55_childhealthapp_1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.johnyhawkdesigns.a55_childhealthapp_1.database.typeConverters.DateTypeConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//For tutorial, visit = https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a
@Entity(tableName = "medHistory_table",
        foreignKeys = @ForeignKey(entity = Child.class,
                parentColumns = "chID",
                childColumns = "foreignChID",
                onDelete = CASCADE),
        indices=@Index(value="foreignChID")) //onDelete = CASCADE tells if child row will be deleted, we’d like to delete also all of it repositories.
public class ChildMedicalHistory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medID")
    private int medID;

    //@Relation(entity = Child.class, parentColumn = "chID", entityColumn = "foreignChID") // Not using this right now because need to first understand how it works.
    // This column stores foreign key as chID. We can also use @ForeignKey here but we used it on start
    @ColumnInfo(name = "foreignChID")
    private int foreignChID;

    @Nullable
    @ColumnInfo(name = "doctorName")
    private String doctorName;

    @Nullable
    @ColumnInfo(name = "visitDate")
    @TypeConverters({DateTypeConverter.class})
    private Date visitDate;

    @Nullable
    @ColumnInfo(name = "diseaseDesc")
    private String diseaseDec;

    @Nullable
    @ColumnInfo(name = "prescMedicine")
    private String prescMedicine;

    @Nullable
    @ColumnInfo(name = "imagePath")
    private String imagePath;


    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public int getForeignChID() {
        return foreignChID;
    }

    public void setForeignChID(int foreignChID) {
        this.foreignChID = foreignChID;
    }

    @Nullable
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(@Nullable String doctorName) {
        this.doctorName = doctorName;
    }

    @Nullable
    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(@Nullable Date visitDate) {
        this.visitDate = visitDate;
    }

    @Nullable
    public String getDiseaseDec() {
        return diseaseDec;
    }

    public void setDiseaseDec(@Nullable String diseaseDec) {
        this.diseaseDec = diseaseDec;
    }

    @Nullable
    public String getPrescMedicine() {
        return prescMedicine;
    }

    public void setPrescMedicine(@Nullable String prescMedicine) {
        this.prescMedicine = prescMedicine;
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }
}
