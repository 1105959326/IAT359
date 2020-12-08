package com.example.wefit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Launch extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();


    }
    public void onStart() {
        super.onStart();
       Intent intent = new Intent(Launch.this,MainActivity.class);
       intent.putExtra("name",1);
       startActivity(intent);

    }
}