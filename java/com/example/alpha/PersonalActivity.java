package com.example.alpha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.update.UpdateUserPassword;
import com.example.alpha.data.StaticData;
import com.example.alpha.myactivity.MyNavigation;


public class PersonalActivity extends MyNavigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler= new WeakHandler();
        setContentView(R.layout.activity_personal);
    }

    public void onExitClick(View v){
        final Intent authActivity = new Intent(this, LoginActivity.class);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(authActivity);
            }
        }, 1);
    }

    public void onChangeClick(View v){
        Toast toast;
        if (checkPassword()){
            String pass1 = ((EditText)findViewById(R.id.inputNewPass)).getText().toString();
            String pass2= ((EditText)findViewById(R.id.inputRepeatPass)).getText().toString();
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
            }catch (Exception e){
                toast = Toast.makeText(this,"Password must have 1 symbol and 1 number",Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            int ch=0;
            for (int i=0;i<pass1.length();i++){
                try {
                    Integer.parseInt(String.valueOf(pass1.indexOf(i)));
                }catch (Exception e){
                    ch++;
                }
            }
            if (ch!=0){
                toast = Toast.makeText(this,"Password must have 1 symbol and 1 number",Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    UpdateUserPassword updateUserPassword = new UpdateUserPassword();

                    updateUserPassword.setIdUser(StaticData.getUser().getIdUser());
                    updateUserPassword.setPassword(((EditText)findViewById(R.id.inputNewPass)).getText().toString());
                    updateUserPassword.start();
                    try {
                        updateUserPassword.join();
                    } catch (InterruptedException e) {
                        if(BuildConfig.DEBUG){
                            Log.e(StaticData.getTAG(),e.getMessage());
                        }
                    }
                }
            },1);

            toast = Toast.makeText(this,"Password changed",Toast.LENGTH_LONG);
        }else{
            toast = Toast.makeText(this,"Passwords are not equals",Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public void onCheckClick(View v){
        Toast toast;
        if (checkPassword()){
            toast = Toast.makeText(this,"Passwords are equals",Toast.LENGTH_LONG);
        }else{
            toast = Toast.makeText(this,"Passwords are not equals",Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public boolean checkPassword(){
        String firstPass = ((EditText)findViewById(R.id.inputNewPass)).getText().toString();
        String secondPass= ((EditText)findViewById(R.id.inputRepeatPass)).getText().toString();
        return firstPass.equals(secondPass);
    }

}
