package de.classicgameshe.classicgameshe.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import de.classicgameshe.classicgameshe.support.DataBaseHelper;

/**
 * Created by lukashenze on 11.10.15.
 */
public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "LOGIN";
    public static final String USER_ID = "ID";
    public static final String USER_NAME_COLUMN = "USERNAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "create table "+TABLE_NAME+
            "( " +USER_ID+" integer primary key autoincrement,"+ USER_NAME_COLUMN+"  text,"+ PASSWORD_COLUMN+ " text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(USER_NAME_COLUMN, userName);
        newValues.put(PASSWORD_COLUMN, password);

        // Insert the row into your table
        db.insert(TABLE_NAME, null, newValues);
//        Log.v("DATENBANK:","this:" + db.rawQuery());
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public ArrayList<ArrayList<String>> selectRecordsFromDBList(String tableName, String[] tableColumns,String whereClase, String whereArgs[], String groupBy,String having, String orderBy)
    {

        ArrayList<ArrayList<String>> retList = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(tableName, tableColumns, whereClase, whereArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst())
        {
            do
            {
                list = new ArrayList<String>();
                for(int i=0; i<cursor.getColumnCount(); i++)
                {
                    list.add( cursor.getString(i) );
                }
                retList.add(list);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return retList;

    }

    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete(TABLE_NAME, where, new String[]{UserName});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query(TABLE_NAME, null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(PASSWORD_COLUMN));
        cursor.close();
        return password;
    }
    public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put(USER_NAME_COLUMN, userName);
        updatedValues.put(PASSWORD_COLUMN, password);

        String where="USERNAME = ?";
        db.update(TABLE_NAME, updatedValues, where, new String[]{userName});
    }

    public boolean loginUser(String username, String password) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + "LOGIN" + " WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public Cursor getloginUserID (String username, String password) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return mCursor;
            }
        }
        return mCursor;
    }

    public boolean checkIfUserExists (String username)
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=?", new String[]{username});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return false;
            }
        }
        return true;
    }

    public  ArrayList<String> getUserName (String username, String password){
        Cursor mCursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+ " WHERE USERNAME=? AND PASSWORD=?",new String[]{username,password});
        ArrayList<String> retList = new ArrayList<>();
        retList.add(mCursor.getString(mCursor.getCount()-1));
        String test = String.valueOf(mCursor);
//        if (mCursor.getColumnCount() >= 0){
//        for(int i=0; i<mCursor.getColumnCount(); i++)
//        {
//            retList.add( mCursor.getString(i) );
//        }

        return retList;
    }

}
