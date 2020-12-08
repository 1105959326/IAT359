package com.example.wefit;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private TextView  tv_register;//
    private Button btn_login;//
    private String userName, psw, spPsw;//
    private EditText et_user_name, et_psw;//

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findId(view);


        init();

    }

    private void findId(View view) {
        tv_register = view.findViewById(R.id.tv_register);

        btn_login = view.findViewById(R.id.btn_login);
        et_user_name = view.findViewById(R.id.et_user_name);
        et_psw = view.findViewById(R.id.et_psw);

    }

    public void onStart() {
        super.onStart();

    }


    @Override
    public void onClick(View v) {


    }

    private void init() {

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), RegisterActivity.class);// go to register activity
                startActivityForResult(intent,1);
            }
        });


        //
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = et_user_name.getText().toString().trim();//transfer username
                psw = et_psw.getText().toString().trim();

                String md5Psw = MD5Utils.md5(psw);

                spPsw = readPsw(userName);

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;

                } else if (md5Psw.equals(spPsw)) {

                    Toast.makeText(getActivity(), "successful", Toast.LENGTH_SHORT).show();

                    saveLoginStatus(true, userName);

                    Intent data = new Intent();

                    data.putExtra("isLogin", true);



                    getActivity().finish();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    return;
                } else if ((spPsw != null && !TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
                    Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getActivity(), "user name not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   // get the user_name and password from the SharedPreferences

    private String readPsw(String userName) {

        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);

        return sp.getString(userName, "");
    }


    //save the login in statement and username to SharedPreferences中

    private void saveLoginStatus(boolean status, String userName) {

        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean("isLogin", status);

        editor.putString("loginUserName", userName);

        editor.commit();
    }


    @Override

   public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {

                et_user_name.setText(userName);

                et_user_name.setSelection(userName.length());
            }
        }
    }
}




