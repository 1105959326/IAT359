package com.example.wefit;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.codingending.popuplayout.PopupLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static java.text.DateFormat.DEFAULT;

public class SecondFragment extends Fragment implements View.OnClickListener {
    private ImageView ivHead;
    private TextView Distance, Time, Speed, Calory;
    private Button change, search,history,share,clear;
    private ImageButton icon1,icon2,icon3,icon4,icon5,icon6;
    private Bitmap head;
    private static String path = "weFit/";
    private RecordedDatabase db;
    private float totalDis, totalTime, totalSpeed, totalCal;
    private EditText search_type;
    private HelperClass helperClass;
    private boolean click=false;
    LinearLayout layout1;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findId(view);

        db = new RecordedDatabase(getContext());
        helperClass = new HelperClass(getContext());
        layout1=(LinearLayout)view.findViewById(R.id.pop);
        layout1.setVisibility(View.INVISIBLE);
        SharedPreferences sp= getContext().getSharedPreferences("avatarNum", MODE_PRIVATE);
        int num = sp.getInt("picNum", DEFAULT);
        if (num != 0) {
            switch (num) {
                case 1:
                    ivHead.setImageResource(R.drawable.icon1);
                    break;
                case 2:
                    ivHead.setImageResource(R.drawable.icon2);
                    break;
                case 3:
                    ivHead.setImageResource(R.drawable.icon3);
                    break;
                case 4:
                    ivHead.setImageResource(R.drawable.icon4);
                    break;
                case 5:
                    ivHead.setImageResource(R.drawable.icon5);
                    break;
                case 6:
                    ivHead.setImageResource(R.drawable.icon6);
                    break;

            }
        }

    }

    public void onStart() {
        super.onStart();
        update();


    }


//get the elements in xml and set the click listener
    private void findId(View view) {

        Distance = view.findViewById(R.id.Distance_t);
        Time = view.findViewById(R.id.Time_t);
        Speed = view.findViewById(R.id.Speed_t);
        Calory = view.findViewById(R.id.Calory_t);
        change = (Button) view.findViewById(R.id.change_button);
        change.setOnClickListener(this);
        ivHead = (ImageView) view.findViewById(R.id.iv_head);
        search_type = view.findViewById(R.id.search_txt);
        search = view.findViewById(R.id.search_button);
        search.setOnClickListener(this);
        share=view.findViewById(R.id.share);
        share.setOnClickListener(this);
        history=view.findViewById(R.id.empty);
        history.setOnClickListener(this);
        icon1 = (ImageButton)view.findViewById(R.id.icon_1);

        icon2 = (ImageButton)view.findViewById(R.id.icon_2);
        icon3 = (ImageButton)view.findViewById(R.id.icon_3);
        icon4 = (ImageButton)view.findViewById(R.id.icon_4);
        icon5 = (ImageButton)view.findViewById(R.id.icon_5);
        icon6 = (ImageButton)view.findViewById(R.id.icon_6);
        icon1.setOnClickListener(this);
        icon2.setOnClickListener(this);
        icon3.setOnClickListener(this);
        icon4.setOnClickListener(this);
        icon5.setOnClickListener(this);
        icon6.setOnClickListener(this);



    }

//set the head icon as you have saved. if not use the default icon


    @Override
    public void onClick(View v) {
        int i = 0;
        switch (v.getId()) {

            case R.id.icon_1:
                ivHead.setImageResource(R.drawable.icon1);
                i = 1;
                layout1.setVisibility(View.INVISIBLE);
                break;
            case R.id.icon_2:
                ivHead.setImageResource(R.drawable.icon2);
                layout1.setVisibility(View.INVISIBLE);
                i = 2;
                break;
            case R.id.icon_3:
                ivHead.setImageResource(R.drawable.icon3);
                layout1.setVisibility(View.INVISIBLE);
                i = 3;
                break;
            case R.id.icon_4:
                ivHead.setImageResource(R.drawable.icon4);
                layout1.setVisibility(View.INVISIBLE);
                i = 4;
                break;
            case R.id.icon_5:
                ivHead.setImageResource(R.drawable.icon5);
                i = 5;
                layout1.setVisibility(View.INVISIBLE);
                break;
            case R.id.icon_6:
                ivHead.setImageResource(R.drawable.icon6);
                i = 6;
                layout1.setVisibility(View.INVISIBLE);
                break;

            case R.id.change_button:

                layout1.setVisibility(View.VISIBLE);
                break;


            default:
                break;
        }
        SharedPreferences sp= getContext().getSharedPreferences("avatarNum", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("picNum", i);
        editor.commit();

        if (v.getId() == R.id.search_button) {
            if (search_type != null) {
                Intent intent = new Intent(getActivity(), RecordHistory.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra("search", search_type.getText().toString());
                startActivity(intent);
            }
        }
        if (v.getId() == R.id.empty) {
            Intent intent2 = new Intent();
            intent2.setClass(getActivity(), RecordHistory.class);// go to the record history page
            startActivity(intent2);

        }
        if (v.getId() == R.id.share) {
            Intent intent1 = new Intent(Intent.ACTION_PICK, null);//open the album
            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//get the select picture `s uri as data

            startActivityForResult(intent1, 4);//set the result code as 4


        }

    }
    public void check(View v){

            if (v.getId() == R.id.icon_1){

                ivHead.setImageResource(R.drawable.icon1);

            }
            if (v.getId() == R.id.icon_2){
                PopupLayout popupLayout= PopupLayout.init(getActivity(), R.layout.select_icon);
                popupLayout.dismiss();
                ivHead.setImageResource(R.drawable.icon2);
            }
            if (v.getId() == R.id.icon_3){
                PopupLayout popupLayout= PopupLayout.init(getActivity(), R.layout.select_icon);
                popupLayout.dismiss();
                ivHead.setImageResource(R.drawable.icon3);
            }
            if (v.getId() == R.id.icon_4){
                PopupLayout popupLayout= PopupLayout.init(getActivity(), R.layout.select_icon);
                popupLayout.dismiss();
                ivHead.setImageResource(R.drawable.icon4);
            }
            if (v.getId() == R.id.icon_5){
                PopupLayout popupLayout= PopupLayout.init(getActivity(), R.layout.select_icon);
                popupLayout.dismiss();
                ivHead.setImageResource(R.drawable.icon5);
            }
            if (v.getId() == R.id.icon_6){
                PopupLayout popupLayout= PopupLayout.init(getActivity(), R.layout.select_icon);
                popupLayout.dismiss();
                ivHead.setImageResource(R.drawable.icon6);
            }

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:

                break;

            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {

//                        setPicToView(head);//get the data of picture and set the icon as the picture after crop
                        ivHead.setImageBitmap(head);
                    }
                }
                break;
            case 4:
                Uri uri = data.getData();
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("image/*");
//            intent1.addCategory(Intent.CATEGORY_OPENABLE);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//share the image as the output
                intent1.putExtra(Intent.EXTRA_STREAM, uri);

                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent1, ""));
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }









    public void update() {
        Cursor cursor = db.getData();//connect to the database

        int index3 = cursor.getColumnIndex(Constants.DISTANCE);
        int index4 = cursor.getColumnIndex(Constants.TIME);
        int index5 = cursor.getColumnIndex(Constants.SPEED);
        int index6 = cursor.getColumnIndex(Constants.CALORY);
        while (cursor.moveToNext()) {
            totalDis += Float.parseFloat(cursor.getString(index3));//transfer the datatype
            totalTime += Float.parseFloat(cursor.getString(index4));
            totalSpeed += Float.parseFloat(cursor.getString(index5));
            totalCal += Float.parseFloat(cursor.getString(index6));
        }

        long millis = (long) totalTime;//set the time show type
        int second = (int) (millis / 60);
        int minutes = (int) (second / 60);
        second %= 60;
        millis = millis % 60;

        Distance.setText(String.format("%.1f", totalDis));//set the distance type
        Time.setText(String.format("%02d:%02d:%02d", minutes, second, millis));
        Speed.setText(String.format("%.1f", 1000 * totalDis / totalTime * 60));
        Calory.setText(String.format("%.0f", totalCal));
    }



}

