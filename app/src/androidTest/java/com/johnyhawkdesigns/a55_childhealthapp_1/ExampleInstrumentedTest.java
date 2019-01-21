package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildRoomDatabase;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context appContext;
    private ChildRoomDatabase childRoomDatabase;
    private ChildDao childDao;

    @Before
    public void setupDatabase(){
        appContext = InstrumentationRegistry.getTargetContext();
        try {
            childRoomDatabase = ChildRoomDatabase.getDBINSTANCE(appContext);
            //childRoomDatabase = Room.inMemoryDatabaseBuilder(appContext, ChildRoomDatabase.class).allowMainThreadQueries().build(); // Which one create method is correct?? I'm

            childDao = childRoomDatabase.getChildDAO();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertChild(){
        Child newChild = new Child(1, "Maryam", "Female", "AB+", null, null, null );
        childDao.insert(newChild); // Use insert method of DAO to insert the object

        LiveData<Child> child = childDao.getChild(1);

    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.johnyhawkdesigns.a55_childhealthapp_1", appContext.getPackageName());
    }


    @After
    public void closeDatabase(){
        childRoomDatabase.close();
    }



}
