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



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecordHistory extends Activity {


    private List<History> historyData = null;
    private Context historyContext;
    private HistoryAdapter historyAdapter = null;
    private ListView list_history;
    private HelperClass helperClass;
//    private RecordedDatabase db = new RecordedDatabase("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_history_list);
        historyContext = RecordHistory.this;
        list_history = (ListView) findViewById(R.id.history_list_view);
        historyData = new LinkedList<History>();

        historyAdapter = new HistoryAdapter((LinkedList<History>) historyData, historyContext);
        list_history.setAdapter(historyAdapter);
    }
    public void onStart() {
        super.onStart();
//        update();
    }


//    public void update() {
//        String distance_S;
//        float distance_F;
//        float time_set;
//        String time_show;
//        String speed;
//        String cal;
//
//        Cursor cursor = db.getData();
//        int index3 = cursor.getColumnIndex(Constants.DISTANCE);
//        int index4 = cursor.getColumnIndex(Constants.TIME);
//        int index5 = cursor.getColumnIndex(Constants.SPEED);
//        int index6 = cursor.getColumnIndex(Constants.CALORY);
//        while (cursor.moveToNext()) {
//            distance_S = String.format("%.1f", Float.parseFloat(cursor.getString(index3)));
//            distance_F = Float.parseFloat(cursor.getString(index3));
//            time_set = Float.parseFloat(cursor.getString(index4));
//            long millis = (long) time_set;
//            int second = (int) (millis / 60);
//            int minutes = (int) (second / 60);
//            second %= 60;
//            millis = millis % 60;
//            time_show = String.format("%02d:%02d:%02d", minutes, second, millis);
//            speed = String.format("%.1f", 1000 * distance_F / time_set * 60);
//            cal = String.format("%.0f", Float.parseFloat(cursor.getString(index6)));
//
//            historyData.add(new History(distance_S, time_show, speed, cal));
//
//
//        }
//
//
//    }
}

