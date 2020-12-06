package com.example.wefit;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SecondFragment extends Fragment implements View.OnClickListener {
    private ImageView ivHead;
    private TextView Distance, Time, Speed, Calory;
    private Button change, search;
    private Bitmap head;
    private static String path = "/wefit/";
    private RecordedDatabase db;
    private float totalDis, totalTime, totalSpeed, totalCal;
    HelperClass helper;
    private EditText search_type;
    private HelperClass helperClass;


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
        initView();
        db = new RecordedDatabase(getContext());
        helperClass = new HelperClass(getContext());


//

    }

    public void onStart() {
        super.onStart();
        update();
    }

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
    }


    private void initView() {

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);
            ivHead.setImageDrawable(drawable);
        } else {

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_button:

                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent1, 1);
                break;

            default:
                break;
        }

        if (v.getId() == R.id.search_button) {
            if (search_type != null) {
                String queryResults = db.getSelectedType(search_type.getText().toString());
                Toast.makeText(getContext(), queryResults, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:

                cropPhoto(data.getData());


                break;

            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {

                        setPicToView(head);
                        ivHead.setImageBitmap(head);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();
        String fileName = path + "head.jpg";
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void update() {
        Cursor cursor = db.getData();
        int index1 = cursor.getColumnIndex(Constants.TYPE);
        int index2 = cursor.getColumnIndex(Constants.DISTANCE);
        int index3 = cursor.getColumnIndex(Constants.TIME);
        int index4 = cursor.getColumnIndex(Constants.SPEED);
        int index5 = cursor.getColumnIndex(Constants.CALORY);
        int index6 = cursor.getColumnIndex(Constants.MAPPOINTS);
        while(cursor.moveToNext()) {
            totalDis += Float.parseFloat(cursor.getString(index2));
            totalTime += Float.parseFloat(cursor.getString(index3));
            totalSpeed += Float.parseFloat(cursor.getString(index4));
            totalCal += Float.parseFloat(cursor.getString(index5));
        }

        long millis = (long) totalTime;
        int second = (int)(millis/60);
        int minutes = (int) (second/60);
        second %= 60;
        millis = millis % 60;

        Distance.setText(String.format("%.1f", totalDis));
        Time.setText(String.format("%02d:%02d:%02d", minutes, second, millis));
        Speed.setText(String.format("%.1f", 1000 * totalDis/totalTime * 60));
        Calory.setText(String.format("%.0f", totalCal));
    }
}

