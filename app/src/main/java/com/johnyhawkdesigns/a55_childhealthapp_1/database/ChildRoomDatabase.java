package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.typeConverters.DateTypeConverter;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

/*
    Abstract class in Java is similar to interface except that it can contain default method implementation.
    An abstract class can have an abstract method without body and it can have methods with implementation also.
    abstract keyword is used to create a abstract class and method. Abstract class in java can’t be instantiated.
    An abstract class is mostly used to provide a base for subclasses to extend and implement the abstract methods and
    override or use the implemented methods in abstract class.
*/
@Database(entities = {Child.class}, version = 1)
@TypeConverters({DateTypeConverter.class}) // In our AppDatabase  class we will have to tell which type converter to use. We have to do this using the annotation  @TypeConverters .
public abstract class ChildRoomDatabase extends RoomDatabase {

    // This method will get ContactDAO object
    public abstract ChildDao getChildDAO();
}
