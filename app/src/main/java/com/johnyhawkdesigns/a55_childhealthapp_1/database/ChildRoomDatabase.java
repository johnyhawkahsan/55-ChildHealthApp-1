package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildMedicalHistoryDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildVaccinationRecordDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.typeConverters.DateTypeConverter;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

/*
    Abstract class in Java is similar to interface except that it can contain default method implementation.
    An abstract class can have an abstract method without body and it can have methods with implementation also.
    abstract keyword is used to create a abstract class and method. Abstract class in java can’t be instantiated.
    An abstract class is mostly used to provide a base for subclasses to extend and implement the abstract methods and
    override or use the implemented methods in abstract class.
*/
@Database(entities = {Child.class, ChildMedicalHistory.class, ChildVaccinationRecord.class}, version = 1)
@TypeConverters({DateTypeConverter.class}) // In our AppDatabase class we will have to tell which type converter to use. We have to do this using the annotation  @TypeConverters .
public abstract class ChildRoomDatabase extends RoomDatabase {

    private static final String TAG = ChildRoomDatabase.class.getSimpleName();

    // This method will get DAO objects
    public abstract ChildDao getChildDAO();
    public abstract ChildMedicalHistoryDao getMedicalHistoryDao();
    public abstract ChildVaccinationRecordDao getChildVaccinationRecordDao();

    //We only need one instance of this class, so we make it singleton
    private static ChildRoomDatabase DBINSTANCE;

    //Singleton pattern method
    public static ChildRoomDatabase getDBINSTANCE(Context context){
        if (DBINSTANCE == null){
            DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ChildRoomDatabase.class,
                    "child-db")
                    //.allowMainThreadQueries() // Allow queries in Main thread instead of AsyncTask - This is not recommended.
                    .build();
        }
        return DBINSTANCE;
    }


}
