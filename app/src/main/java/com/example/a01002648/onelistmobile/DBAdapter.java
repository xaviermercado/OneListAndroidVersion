package com.example.a01002648.onelistmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by A01002648 on 2/24/2017.
 */

public class DBAdapter {
    //various constants to be used in creating and updating the database
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_ENDDATE = "endDate";
    static final String KEY_ISDONE = "done";
    static final String KEY_COST = "cost";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "OneListMobileDB";
    static final String DATABASE_TABLE = "tasks";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table tasks (_id integer primary key autoincrement, "
                    + "name text not null, description text,endDate text, done integer, cost real);";

    final Context context;


    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //onCreate implicitly used to create the database table "contacts"
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //onUpgrade called implicitly when the database "version" has changed
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tasks");
            onCreate(db);
        }
    }

    //---opens the database---
    //calls SQLiteOpenHelper.getWritableDatabase() when opening the DB.
    //If the DB does not yet exist it will be created here.
    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        dbHelper.close();
    }

    //---insert a contact into the database---
    //uses ContentValues class to store key/value pairs for field names and data
    //to be inserted into the DB table by SQLiteDatabase.insert()
    public long insertTask(String name, String description,String enddate, int done, double cost)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_ENDDATE, enddate);
        initialValues.put(KEY_ISDONE, done);
        initialValues.put(KEY_COST, cost);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    //removes from the DB the record specified using SQLiteDatabase.delete()
    public boolean deleteTask(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    //SQLiteDatabase.query builds a SELECT query and returns a "Cursor" over the result set
    public Cursor getAllTasks()
    {
        String buildSQL = "SELECT * FROM " + DATABASE_TABLE;

        Log.d(TAG, "getAllData SQL: " + buildSQL);
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_DESCRIPTION,KEY_ENDDATE,KEY_ISDONE,KEY_COST}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getTask(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_DESCRIPTION,KEY_ENDDATE,KEY_ISDONE,KEY_COST}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    //Uses SQLiteDatabase.update() to change existing data by key/value pairs
    public boolean updateTask(long rowId,String name, String description,String enddate, int done, double cost)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_DESCRIPTION, description);
        args.put(KEY_ENDDATE, enddate);
        args.put(KEY_ISDONE, done);
        args.put(KEY_COST, cost);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
