package com.example.wefit;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wefit.dummy.DummyContent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecordHistory extends Activity {
    private ListView lv;
    private RecordedDatabase db;
    private ArrayList<String> list = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_record_history_list);
        lv = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                update());
        lv.setAdapter(adapter);
    }



    private void findId(View view){

    }






    public ArrayList<String> update() {
        Cursor cursor = db.getData();


        int index1 = cursor.getColumnIndex(Constants.UID);
        int index2 = cursor.getColumnIndex(Constants.TYPE);
        int index3 = cursor.getColumnIndex(Constants.DISTANCE);
        int index4 = cursor.getColumnIndex(Constants.TIME);
        int index5 = cursor.getColumnIndex(Constants.SPEED);
        int index6 = cursor.getColumnIndex(Constants.CALORY);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {
         list.add(cursor.getString(i));


        }


        return list;
    }

}