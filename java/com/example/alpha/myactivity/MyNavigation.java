package com.example.alpha.myactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.BuildConfig;
import com.example.alpha.DBActivity;
import com.example.alpha.ListNoteActivity;
import com.example.alpha.PeopleActivity;
import com.example.alpha.PersonalActivity;
import com.example.alpha.PrintsActivity;
import com.example.alpha.R;
import com.example.alpha.RandomActivity;
import com.example.alpha.data.StaticData;

import androidx.appcompat.app.AppCompatActivity;

public abstract class MyNavigation extends AppCompatActivity {
    protected WeakHandler mHandler;

    public void onDiceClick(View v){
        final Intent randomActivity = new Intent(this, RandomActivity.class);
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Dice open");
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(randomActivity);
            }
        }, 1);
    }

    public void onSignaturesClick(View v){
        final Intent printsActivity = new Intent(this, PrintsActivity.class);
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Signatures open");
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(printsActivity);
            }
        }, 1);
    }

    public void onNotesClick(View v){
        final Intent notesActivity = new Intent(this, ListNoteActivity.class);
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Notes open");
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(notesActivity);
            }
        }, 1);
    }

    public  void onPersonsClick(View v){
        final Intent peopleActivity = new Intent(this, PeopleActivity.class);
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Persons open");
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(peopleActivity);
            }
        }, 1);
    }

    public void onCodesClick(View v){
        final Intent codesActivity = new Intent(this, DBActivity.class);
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Codes open");
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(codesActivity);
            }
        }, 1);
    }
//do not work!
    public void onProfileClick(View v){
        final Intent personalActivity = new Intent(this, PersonalActivity.class);
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Profile open");
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(personalActivity);
            }
        }, 1);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onStart() {
        super.onStart();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set money
        final TextView money = findViewById(R.id.money);
        final int m = StaticData.getUser().getMoney();
        final String outputLine = m + getString(R.string.valueMoney);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                money.setText(outputLine);
            }
        }, 1);
    }
}
