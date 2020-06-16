package com.example.alpha;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.myactivity.MyNavigation;

public class RandomActivity extends MyNavigation {

    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        mHandler=new WeakHandler();

        final ImageView image = findViewById(R.id.randomGif);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.setBackgroundResource(R.drawable.randomgif);
                animation = (AnimationDrawable)image.getBackground();
                animation.start();
            }
        }, 1);
    }

    public void onRollStopClick(View v){
        if(animation.isRunning()){
            animation.stop();
        }else{
            animation.start();
        }
    }

}
