package com.example.wefit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;

    private EditText et_user_name,et_psw,et_psw_again;

    private String userName,psw,pswAgain;

    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_page);


        init();
    }

    private void init() {


        btn_register=findViewById(R.id.btn_register);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        et_psw_again=findViewById(R.id.et_psw_again);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditString();

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "Please confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "password was different", Toast.LENGTH_SHORT).show();
                    return;

                }else if(isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this, "the username has exist", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "successful", Toast.LENGTH_SHORT).show();


                    saveRegisterInfo(userName, psw);


                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);

                    RegisterActivity.this.finish();
                }
            }
        });
    }

    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }

    private boolean isExistUserName(String userName){
        boolean has_userName=false;

        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //get passward
        String spPsw=sp.getString(userName, "");

        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    /**
     * save password and account number into SharedPreferences
     */
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);

        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);

        SharedPreferences.Editor editor=sp.edit();

        editor.putString(userName, md5Psw);

        editor.commit();
    }
}