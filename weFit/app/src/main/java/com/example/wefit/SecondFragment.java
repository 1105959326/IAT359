package com.example.wefit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private Button activityButton;
    private Button SecondButton;

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
//        SecondButton= view.findViewById(R.id.button_second);
//        SecondButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(firstFragment==null){
//                    firstFragment = new FirstFragment();
//                }
//                getFragmentManager().beginTransaction().replace(R.id.activity_window,firstFragment).commitAllowingStateLoss();
//
//            }
//
//
//
//        });


//        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//
//            }
//        });


    }


    @Override
    public void onClick(View v) {

    }
}