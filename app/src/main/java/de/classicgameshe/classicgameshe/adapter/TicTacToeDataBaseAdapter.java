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
 * Created by marinus on 16.10.15.
 */
public class TicTacToeDataBaseAdapter
{
    static final String DATABASE_NAME = "tictactoe.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "create table "+"TICTACTOE"+
            "(ID integer primary key autoincrement, userID int, x  int,o int); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public TicTacToeDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public TicTacToeDataBaseAdapter open() throws SQLException
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

    public void insertEntry()
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("userID", 1);
        newValues.put("x", 0);
        newValues.put("o", 0);

        // Insert the row into your table
        db.insert(DATABASE_NAME, null, newValues);
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


    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public void  updateEntry(String winner)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put(winner, 1);

        db.update("TICTACTOE", updatedValues, "id" + "= 1", null);
    }

}
