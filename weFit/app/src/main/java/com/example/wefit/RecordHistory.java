package com.example.wefit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecordHistory extends Activity {

    String distance_S;
    float distance_F;
    float time_set;
    String time_show;
    String speed;
    String cal, points;
    private List<History> historyData = null;
    private Context historyContext;
    private HistoryAdapter historyAdapter = null;
    private ListView list_history;
    private HelperClass helperClass;
    private RecordedDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_history_list);
        historyContext = RecordHistory.this;
         db = new RecordedDatabase(this.historyContext);
        list_history = (ListView) findViewById(R.id.history_list_view);
        historyData = new LinkedList<History>();

        historyAdapter = new HistoryAdapter((LinkedList<History>) historyData, historyContext);
        list_history.setAdapter(historyAdapter);

//        list_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(historyContext, MapDetail.class);
//                i.putExtra("time", distance_S);
//                i.putExtra("speed", speed);
//                i.putExtra("dist", time_show);
//                i.putExtra("cal", cal);
//                i.putExtra("points", points);
//                startActivity(i);
//            }
//        });
    }
    public void onStart() {
        super.onStart();
        update();
    }


    public void update() {


        Cursor cursor = db.getData();
        int index3 = cursor.getColumnIndex(Constants.DISTANCE);
        int index4 = cursor.getColumnIndex(Constants.TIME);
        int index5 = cursor.getColumnIndex(Constants.SPEED);
        int index6 = cursor.getColumnIndex(Constants.CALORY);
        int index7 = cursor.getColumnIndex(Constants.MAPPOINTS);
        while (cursor.moveToNext()) {
            distance_S = String.format("%.1f", Float.parseFloat(cursor.getString(index3)));
            distance_F = Float.parseFloat(cursor.getString(index3));
            time_set = Float.parseFloat(cursor.getString(index4));
            long millis = (long) time_set;
            int second = (int) (millis / 60);
            int minutes = (int) (second / 60);
            second %= 60;
            millis = millis % 60;
            time_show = String.format("%02d:%02d:%02d", minutes, second, millis);
            speed = String.format("%.1f", 1000 * distance_F / time_set * 60);
            cal = String.format("%.0f", Float.parseFloat(cursor.getString(index6)));
            points = cursor.getString(index7);
            historyData.add(new History(distance_S, time_show, speed, cal, points));


        }


    }
}

