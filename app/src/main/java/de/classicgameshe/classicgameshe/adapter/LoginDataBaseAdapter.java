package de.classicgameshe.classicgameshe.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import de.classicgameshe.classicgameshe.support.DataBaseHelper;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "LOGIN";
    public static final String USER_ID = "ID";
    public static final String USER_NAME_COLUMN = "USERNAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";

    // SQL Statement zum Erstellen der Datenbank
    public static final String DATABASE_CREATE = "create table "+TABLE_NAME+
            "( " +USER_ID+" integer primary key autoincrement,"+ USER_NAME_COLUMN+"  text,"+ PASSWORD_COLUMN+ " text); ";
    public SQLiteDatabase db;
    private final Context context;
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
        newValues.put(USER_NAME_COLUMN, userName);
        newValues.put(PASSWORD_COLUMN, password.hashCode());

        // Datensatz in die Tablle einfügen
        db.insert(TABLE_NAME, null, newValues);
    }



    public boolean isDbEmpty (){
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
           cursor.moveToFirst();
               if (cursor.getInt(0) == 0) {
                   return true;

               } else {
                   return false;
               }
    }

    public int deleteEntry(String UserName)
    {
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete(TABLE_NAME, where, new String[]{UserName});
        return numberOFEntriesDeleted;
    }

    public boolean updateEntry(String userName,String password)
    {
        //Die zu verändernden Felder definieren
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(USER_NAME_COLUMN, userName);
        updatedValues.put(PASSWORD_COLUMN, password.hashCode());

        String where="USERNAME = ?";
        if (String.valueOf(password.hashCode()).equals (userPwd(userName))){
            return false;
        }else{
            db.update(TABLE_NAME, updatedValues, where, new String[]{userName});
            return true;
        }

    }

    public String userPwd (String userName){
        Cursor mCursor = db.rawQuery("SELECT "+PASSWORD_COLUMN+" FROM "+TABLE_NAME+ " WHERE USERNAME=?",
                new String[]{userName});
        String pwd = null;
        if (mCursor != null){
            if (mCursor.moveToFirst()){
                pwd = mCursor.getString(mCursor.getCount()-1);
            }
        }
        Log.d(TABLE_NAME,"userPwd="+pwd);
        return pwd;
    }

    public boolean loginUser(String username, String password) throws SQLException
    {
        Log.d(DATABASE_NAME,"loginUser username="+username+" pwd="+password);
        Cursor mCursor = db.rawQuery("SELECT * FROM " + "LOGIN" + " WHERE username=? AND password=?",
                new String[]{username, String.valueOf(password.hashCode())});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public int getloginUserID (String username, String password) throws SQLException {
        Cursor mCursor = db.rawQuery("SELECT "+USER_ID+" FROM " + TABLE_NAME + " WHERE username=? AND password=?", new String[]{username, String.valueOf(password.hashCode())});
        int id = 0;
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                id = mCursor.getInt(mCursor.getCount() - 1);
            }
        }
        Log.d(TABLE_NAME,"userID="+id);
    return id;
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

    public  String getUserName (String username, String password){
        Cursor mCursor = db.rawQuery("SELECT "+USER_NAME_COLUMN+" FROM "+TABLE_NAME+ " WHERE USERNAME=? AND PASSWORD=?",
                new String[]{username, String.valueOf(password.hashCode())});
        String userName = null;
        if (mCursor != null){
            if (mCursor.moveToFirst()){
               userName = mCursor.getString(mCursor.getCount()-1);
            }
        }
        Log.d(TABLE_NAME,"userName="+userName);
        return userName;
    }

}
