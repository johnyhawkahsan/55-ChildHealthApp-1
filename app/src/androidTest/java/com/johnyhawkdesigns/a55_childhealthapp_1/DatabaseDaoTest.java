package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.Dao.ChildDao;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildRoomDatabase;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseDaoTest {

    private static final String TAG = DatabaseDaoTest.class.getSimpleName();
    Context appContext;
    Application application;
    private ChildRoomDatabase childRoomDatabase;
    private ChildDao childDao;
    //private ChildViewModel childViewModel;

    @Before
    public void setupDatabase(){

        appContext = InstrumentationRegistry.getTargetContext();



        try {
            //childRoomDatabase = ChildRoomDatabase.getDBINSTANCE(appContext); // This standard method is not working for this JUnit test
            // Using an in-memory database because the information stored here disappears when the process is killed.
            childRoomDatabase = Room.inMemoryDatabaseBuilder(appContext, ChildRoomDatabase.class).allowMainThreadQueries().build();

            childDao = childRoomDatabase.childDAO();
            //childViewModel = new ChildViewModel(appContext);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.johnyhawkdesigns.a55_childhealthapp_1", appContext.getPackageName());
    }

    @Test
    public void testInsertAndRetrieveChild() throws InterruptedException {

        String childName1 = "Maryam";

        Child newChild = new Child();
        newChild.setName(childName1);
        newChild.setAge(3);
        newChild.setBloodGroup("AB+");
        childDao.insert(newChild); // Use insert method of DAO to insert the object

        // Now test if it retrieves as well
        List<Child> allChilds = LiveDataTestUtil.getValue(childDao.getAllChild());
        Log.d(TAG, "testInsertChild: allChilds.get(0).getName() = " + allChilds.get(0).getName() );
        assertEquals(allChilds.get(0).getName(), childName1);
    }



    @Test
    public void testRetreiveAllChild() throws InterruptedException {

        String childName1 = "Maryam";

        Child child1 = new Child();
        child1.setName(childName1);
        child1.setAge(3);
        child1.setBloodGroup("AB+");
        childDao.insert(child1); // Use insert method of DAO to insert the object

        String childName2 = "Memoona";

        Child child2 = new Child();
        child2.setName(childName2);
        child2.setAge(1);
        child2.setBloodGroup("B+");
        childDao.insert(child2); // Use insert method of DAO to insert the object

        // Now test if it retrieves as well
        List<Child> allChilds = LiveDataTestUtil.getValue(childDao.getAllChild());

        String receivedName1 = allChilds.get(0).getName();
        String receivedName2 = allChilds.get(1).getName();

        Log.d(TAG, "testRetreiveAllChild: receivedName1 = " + receivedName1 + ", receivedName2 = " + receivedName2);


        Log.d(TAG, "testInsertChild: allChilds.get(0).getName() = " + receivedName1 );
        assertEquals(receivedName1, childName1);
        Log.d(TAG, "testInsertChild: allChilds.get(1).getName() = " + receivedName2 );
        assertEquals(receivedName2, childName2);

    }

    @Test
    public void deleteAll() throws Exception {

        Child newChild = new Child();
        newChild.setName("Maryam");
        newChild.setAge(3);
        newChild.setBloodGroup("AB+");
        childDao.insert(newChild); // Use insert method of DAO to insert the object

        childDao.deleteAll();

        // Now test if it retrieves as well
        List<Child> allChilds = LiveDataTestUtil.getValue(childDao.getAllChild());
        assertTrue(allChilds.isEmpty());
    }

/*
    @Test
    public void testViewModel() throws InterruptedException {

        Child newChild = new Child();
        newChild.setName("Maryam");
        newChild.setAge(3);
        newChild.setBloodGroup("AB+");

        childViewModel.insert(newChild);
        List<Child> allChilds = LiveDataTestUtil.getValue(childViewModel.getAllChilds());

        String receivedName = allChilds.get(0).getName();

        Log.d(TAG, "testRetreiveAllChild: receivedName = " + receivedName);

        Log.d(TAG, "testInsertChild: allChilds.get(0).getName() = " + receivedName );
        assertEquals(receivedName, newChild);
    }
*/




    @After
    public void closeDatabase(){
        childRoomDatabase.close();
    }



}
