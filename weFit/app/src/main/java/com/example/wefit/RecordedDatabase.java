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

    public long insertData (String type, String distance, String time, String speed, String calories, String mappoints){
        //insert data to the table
        db = helper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TYPE, type);
        contentValues.put(Constants.DISTANCE, distance);
        contentValues.put(Constants.TIME, time);
        contentValues.put(Constants.SPEED, speed);
        contentValues.put(Constants.CALORY, calories);
        contentValues.put(Constants.MAPPOINTS, mappoints);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData(){
        //read data from the table
        db = helper.getReadableDatabase();

        String[] columns = {Constants.UID, Constants.TYPE, Constants.DISTANCE, Constants.TIME, Constants.SPEED, Constants.CALORY, Constants.MAPPOINTS};
        Cursor cursor = db.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }


    public String getSelectedType(String type){

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {Constants.UID, Constants.TYPE, Constants.DISTANCE, Constants.TIME, Constants.SPEED, Constants.CALORY, Constants.MAPPOINTS};

        String selection = Constants.TYPE + "='" + type + "'";
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection,null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(Constants.TYPE);
            int index2 = cursor.getColumnIndex(Constants.DISTANCE);
            int index3 = cursor.getColumnIndex(Constants.TIME);
            int index4 = cursor.getColumnIndex(Constants.SPEED);
            int index5 = cursor.getColumnIndex(Constants.CALORY);
            int index6 = cursor.getColumnIndex(Constants.MAPPOINTS);
            String typeA = cursor.getString(index1);
            String dist = cursor.getString(index2);
            String time = cursor.getString(index3);
            String speed = cursor.getString(index4);
            String claory = cursor.getString(index5);
            buffer.append(typeA + " " +  dist + " " + time + " " + speed + " " + claory + "\n");


        }
        return  buffer.toString();
    }

    public void clearData(){
        db.execSQL("DROP TABLE "+Constants.TABLE_NAME);
    } //clear table



}
