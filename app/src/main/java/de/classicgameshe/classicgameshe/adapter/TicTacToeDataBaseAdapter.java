package de.classicgameshe.classicgameshe.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import de.classicgameshe.classicgameshe.support.DataBaseHelper;

/**
 * Created by marinus on 16.10.15.
 */

public class TicTacToeDataBaseAdapter extends DataBaseHelper
{
    public static final String DATABASE_NAME = "statistic.db";
    public static final String CONTACTS_TABLE_NAME = "tictactoe";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_userID = "userID";
    public static final String CONTACTS_COLUMN_x = "x";
    public static final String CONTACTS_COLUMN_o = "o";
    public static final String CONTACTS_COLUMN_multiplayer = "multiplayer";

    public TicTacToeDataBaseAdapter(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table tictactoe " +
                        "(id integer primary key, userID int,x int,o int, multiplayer int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tictactoe");
        onCreate(db);
    }

    public boolean insertEntry  (int userID, int x, int o, int multiplayer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("userID", userID);
        newValues.put("x", x);
        newValues.put("o", o);
        newValues.put("multiplayer", multiplayer);
        db.insert("tictactoe", null, newValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tictactoe where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateEntry (Integer id, int userID, int x, int o, int multiplayer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put("userID", userID);
        updateValues.put("x", x);
        updateValues.put("o", o);
        updateValues.put("multiplayer", multiplayer);
        db.update("contacts", updateValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Integer deleteEntry (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tictactoe",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    // neu^

    public ArrayList<String> getAllEntrys()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from tictactoe", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

}
