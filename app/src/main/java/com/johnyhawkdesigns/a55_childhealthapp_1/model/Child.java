package com.johnyhawkdesigns.a55_childhealthapp_1.model;



import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.johnyhawkdesigns.a55_childhealthapp_1.database.typeConverters.DateTypeConverter;

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

    // To avoid complication, I'm converting Date of birth to string for now
    @Nullable
    @TypeConverters({DateTypeConverter.class})
    @ColumnInfo(name = "dateOfBirth")
    private Date dateOfBirth;

    @Nullable
    @ColumnInfo(name = "age")
    private int age;

    @Nullable
    @ColumnInfo(name = "height")
    private int height;

    @Nullable
    @ColumnInfo(name = "weight")
    private int weight;


    @Nullable
    @ColumnInfo(name = "profileUpdateDate")
    @TypeConverters({DateTypeConverter.class})
    private Date profileUpdateDate;

    public int getChID() {
        return chID;
    }

    public void setChID(int chID) {
        this.chID = chID;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    public void setGender(@Nullable String gender) {
        this.gender = gender;
    }

    @Nullable
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(@Nullable String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }

    @Nullable
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@Nullable Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Nullable
    public int getAge() {
        return age;
    }

    public void setAge(@Nullable int age) {
        this.age = age;
    }

    @Nullable
    public int getHeight() {
        return height;
    }

    public void setHeight(@Nullable int height) {
        this.height = height;
    }

    @Nullable
    public int getWeight() {
        return weight;
    }

    public void setWeight(@Nullable int weight) {
        this.weight = weight;
    }

    @Nullable
    public Date getProfileUpdateDate() {
        return profileUpdateDate;
    }

    public void setProfileUpdateDate(@Nullable Date profileUpdateDate) {
        this.profileUpdateDate = profileUpdateDate;
    }

}
