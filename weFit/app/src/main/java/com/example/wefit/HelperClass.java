package com.example.wefit;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class HelperClass extends SQLiteOpenHelper {
    private Context context;
    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    Constants.TABLE_NAME + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.TYPE + " TEXT, " +
                    Constants.DISTANCE + " TEXT, " +
                    Constants.SPEED + " TEXT, " +
                    Constants.TIME + " TEXT, " +
                    Constants.CALORY + " TEXT, " +
                    Constants.MAPPOINTS + " TEXT);" ; //create the table
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME; //define DROP_TABLE

    public HelperClass(Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table, if cannot create the table then toast error message
        try{
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();
        }catch (SQLException e){
            Toast.makeText(context, "cant create db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade the table, if cannot upgrade the table then toast error message
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}
