package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.select.SelectUserByEmailAndPass;
import com.example.alpha.data.StaticData;

public class LoginActivity extends AppCompatActivity {

    private WeakHandler mHandler;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler = new WeakHandler();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onRegistrationClick(View v){
        final Intent regActivity = new Intent(this, RegActivity.class);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(regActivity);
            }
        },1);
    }

    public void onAuthorizationClick(View v){
        Toast toast;
        try {
            SelectUserByEmailAndPass selectUserByEmailAndPass = new SelectUserByEmailAndPass();
            final EditText inputLogin= findViewById(R.id.input_email);
            final EditText inputPassword = findViewById(R.id.input_password);

            selectUserByEmailAndPass.setValueEmail(inputLogin.getText().toString());
            selectUserByEmailAndPass.setValuePass(inputPassword.getText().toString());
            selectUserByEmailAndPass.start();
            selectUserByEmailAndPass.join();

            if (selectUserByEmailAndPass.getUser()!=null){
                StaticData.setUser(selectUserByEmailAndPass.getUser());
                final Intent peopleActivity = new Intent(this, PeopleActivity.class);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(peopleActivity);
                    }
                }, 1);
            }else{
                toast = Toast.makeText(this,"Wrong data",Toast.LENGTH_LONG);
                toast.show();
            }
        }catch (Exception e){
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
            toast=Toast.makeText(this,"An error has occurred "+e.getMessage(),Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
