package com.example.alpha;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.select.SelectPersonByName;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Person;
import com.example.alpha.myactivity.MyNavigation;
import com.squareup.picasso.Picasso;

public class PeopleActivity extends MyNavigation {

    @Override
    protected void onStart() {
        super.onStart();
        //access camera
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            final int RequestCameraPermission = 1001;
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, RequestCameraPermission);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        mHandler=new WeakHandler();
        //scroll for description
        final TextView description = findViewById(R.id.personDescription);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                description.setMovementMethod(new ScrollingMovementMethod());
            }
        }, 1);
    }

    public void onSearchClick(View v){
        final EditText namePersonET= findViewById(R.id.searchText);
        final String namePerson = namePersonET.getText().toString();

        final SelectPersonByName selectPersonByName = new SelectPersonByName();
        selectPersonByName.setValue(namePerson);
        selectPersonByName.start();
        try {
            selectPersonByName.join();
        } catch (InterruptedException e) {
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }



        final Person person = selectPersonByName.getPerson();
        if(person!=null) {
            final TextView descriptionTV = findViewById(R.id.personDescription);
            final ImageView personImage = findViewById(R.id.personPic);
            mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        personImage.setVisibility(View.VISIBLE);
                        descriptionTV.setText(person.getDescription());
                        Picasso.get().load(person.getPicture()).into(personImage);
                    }
                }, 1);

        }else{
            final TextView descriptionTV = findViewById(R.id.personDescription);
            final ImageView personImage = findViewById(R.id.personPic);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    descriptionTV.setText(R.string.noMatches);
                    personImage.setVisibility(View.INVISIBLE);
                }
            }, 1);

        }
    }
}
