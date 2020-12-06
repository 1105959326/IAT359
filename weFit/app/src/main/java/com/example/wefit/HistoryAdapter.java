package com.example.wefit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HistoryAdapter extends BaseAdapter {

    private LinkedList<History> historyData;
    private Context historyContext;

    public HistoryAdapter(LinkedList<History> historyData, Context historyContext) {
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
        convertView = LayoutInflater.from(historyContext).inflate(R.layout.each_history,parent,false);

        TextView txt_distance = (TextView) convertView.findViewById(R.id.distance_txt);
        TextView txt_time = (TextView) convertView.findViewById(R.id.time_text);
        TextView txt_speed = (TextView) convertView.findViewById(R.id.speed_text);
        TextView txt_cal = (TextView) convertView.findViewById(R.id.cal_text);

        txt_distance.setText(historyData.get(position).getDistance_His());
        txt_time.setText(historyData.get(position).getTime_His());
        txt_speed.setText(historyData.get(position).getSpeed_His());
        txt_cal.setText(historyData.get(position).getCal());
        return convertView;
    }
}