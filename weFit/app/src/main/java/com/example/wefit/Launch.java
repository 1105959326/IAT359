package com.example.wefit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Launch extends AppCompatActivity {// set up a first view class to run the first fragment
    private FragmentManager fm;
    private FragmentTransaction ft;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();




    }
    public void onStart() {
        super.onStart();
       Intent intent = new Intent(Launch.this,MainActivity.class);// intent a new class with a result value
        //the value will be detected in mainActivity class and switch to the login fragment.


       intent.putExtra("name",1);
       startActivity(intent);
       //ask for permission
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;


        }
    }
}