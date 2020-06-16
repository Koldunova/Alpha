package com.example.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.insert.InsertNewUser;
import com.example.alpha.dao.dml.select.SelectUserByEmail;
import com.example.alpha.data.StaticData;

import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {

    private WeakHandler mHandler;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mHandler=new WeakHandler();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onRegClick(View v) {
        Toast toast;
        final EditText email = findViewById(R.id.input_email);
        final EditText password1 = findViewById(R.id.input_password);
        final EditText password2 = findViewById(R.id.input_password2);

        //check exist or no this email
        SelectUserByEmail selectUserByEmail = new SelectUserByEmail();
        selectUserByEmail.setValue(email.getText().toString());
        selectUserByEmail.start();
        try {
            selectUserByEmail.join();
        }catch (Exception e){
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }

        if (selectUserByEmail.getResult().equals("exist")){
            toast = Toast.makeText(this,"This login already exist",Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        //this email free
        if (Pattern.matches("(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*", email.getText().toString())) {
            final String pass1=password1.getText().toString();
            final  String pass2=password2.getText().toString();
            //check length line
            if (pass1.length()<6){
                //incorrect password
                toast = Toast.makeText(this,"Password must be at least 6 characters long",Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            //check 1 symbol and 1 number
            try{
                Integer.parseInt(pass1);
                toast = Toast.makeText(this,"Password must have 1 symbol and 1 number",Toast.LENGTH_LONG);
                toast.show();
                return;
            }catch (Exception ex){
                int ch=0;
                for (int i=0;i<pass1.length();i++){
                    try {
                        Integer.parseInt(String.valueOf(pass1.indexOf(i)));
                    }catch (Exception e){
                        ch++;
                    }
                }
                if (ch!=0 && ch!=pass1.length()){
                    toast = Toast.makeText(this,"Password must have 1 symbol and 1 number",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                //check equals passwords
                if (!pass1.equals(pass2)){
                    //not equals
                    toast = Toast.makeText(this,"Passwords do not match",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                //create user
                final InsertNewUser insertNewUser = new InsertNewUser();
                insertNewUser.setLogin(email.getText().toString());
                insertNewUser.setPassword(pass1);
                insertNewUser.start();
                try {
                    insertNewUser.join();
                }catch (Exception e){
                    if(BuildConfig.DEBUG){
                        Log.e(StaticData.getTAG(),e.getMessage());
                    }
                    toast = Toast.makeText(this,"Smt went wrong",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                toast = Toast.makeText(this,"Successfully",Toast.LENGTH_LONG);
                toast.show();
            }

        }else{
            toast = Toast.makeText(this,"Enter correct Email",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onBackClick(View v){
        final Intent authActivity = new Intent(this, LoginActivity.class);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(authActivity);
            }
        }, 1);
    }

}
