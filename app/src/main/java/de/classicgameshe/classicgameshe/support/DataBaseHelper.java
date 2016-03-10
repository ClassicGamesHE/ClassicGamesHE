package de.classicgameshe.classicgameshe.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import de.classicgameshe.classicgameshe.adapter.LoginDataBaseAdapter;
import de.classicgameshe.classicgameshe.adapter.TicTacToeDataBaseAdapter;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        // Log version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data");
        //Alte Datenbank löschen und neu erstellen...Für ein Datenbankupdate
        _db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Neue Datenbank erstellen
        onCreate(_db);
    }

}
