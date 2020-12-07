package com.example.wefit;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HistoryAdapter extends BaseAdapter {

    private LinkedList<History> historyData;
    private Context historyContext;

    public HistoryAdapter(LinkedList<History> historyData, Context historyContext) {
        super();
        this.historyData = historyData;
        this.historyContext = historyContext;
    }


    @Override
    public int getCount() {
        return historyData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos  = position;
        convertView = LayoutInflater.from(historyContext).inflate(R.layout.each_history,parent,false);

        TextView txt_distance = (TextView) convertView.findViewById(R.id.distance_txt);
        TextView txt_time = (TextView) convertView.findViewById(R.id.time_text);
        TextView txt_speed = (TextView) convertView.findViewById(R.id.speed_text);
        TextView txt_cal = (TextView) convertView.findViewById(R.id.cal_text);
        Button route = convertView.findViewById(R.id.route);
        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(historyContext, MapDetail.class);
                i.putExtra("time", historyData.get(pos).getTime_His());
                i.putExtra("speed", historyData.get(pos).getSpeed_His());
                i.putExtra("dist", historyData.get(pos).getDistance_His());
                i.putExtra("cal", historyData.get(pos).getCal());
                i.putExtra("points", historyData.get(pos).getPoints());
                historyContext.startActivity(i);
            }
        });
        txt_distance.setText("Distance\n" + historyData.get(position).getDistance_His());
        txt_time.setText("Time\n" + historyData.get(position).getTime_His());
        txt_speed.setText("Speed\n" + historyData.get(position).getSpeed_His());
        txt_cal.setText("Cal Burned\n" + historyData.get(position).getCal());
        return convertView;
    }




}
