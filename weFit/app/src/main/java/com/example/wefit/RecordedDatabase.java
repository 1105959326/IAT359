package com.example.wefit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordedDatabase {
    private SQLiteDatabase db;
    private Context context;
    private final HelperClass helper;

    public RecordedDatabase(Context context) {
        this.context = context;
        helper = new HelperClass(context);
    }

    public long insertData (String type, String distance, String time, String speed, String calories){
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TYPE, type);
        contentValues.put(Constants.DISTANCE, distance);
        contentValues.put(Constants.TIME, time);
        contentValues.put(Constants.SPEED, speed);
        contentValues.put(Constants.CALORY, calories);
        long id= db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData(){
        db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.TYPE, Constants.DISTANCE, Constants.TIME, Constants.SPEED, Constants.CALORY};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null,null, null, null, null);
        return cursor;
    }


}
