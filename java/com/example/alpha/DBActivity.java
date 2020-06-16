package com.example.alpha;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.select.SwitchTypeCodeAndNextOperation;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Examination;
import com.example.alpha.domain.Report;
import com.example.alpha.myactivity.MyNavigation;

import java.io.IOException;

public class DBActivity extends MyNavigation implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    WebView webView;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    LinearLayout myPlayer;

    public void onPauseClick(View v){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            if(BuildConfig.DEBUG){
                Log.i(StaticData.getTAG(),"Player stopped");
            }
        }
    }

    public void onStartClick(View v){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            if(BuildConfig.DEBUG){
                Log.i(StaticData.getTAG(),"Player started");
            }
        }
    }

    public void onStopClick(View v){
        mediaPlayer.stop();
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Player stopped");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPlayer=findViewById(R.id.myPlayer);
        webView = findViewById(R.id.myWebGame);
        myPlayer=findViewById(R.id.myPlayer);

        mHandler=new WeakHandler();

        final TextView description = findViewById(R.id.textDescription);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                WebSettings webSettings=webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.setWebViewClient(new MyWebViewClient());

                audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                description.setMovementMethod(new ScrollingMovementMethod());
            }
        }, 1);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"Player started");
        }
    }

    private  void releaseMP(){
        if (mediaPlayer !=null){
           try{
                mediaPlayer.release();
                mediaPlayer=null;
           }catch (Exception e){
               if(BuildConfig.DEBUG){
                   Log.e(StaticData.getTAG(),e.getMessage());
               }
           }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        releaseMP();
    }

    private static class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void onEnterClick(View v){

        try{
            onStopClick(v);
        }catch (Exception e){
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }

        EditText codeET= findViewById(R.id.codeInput);
        String inpCode = codeET.getText().toString();
        final TextView resultTV= findViewById(R.id.textDescription);

        if (!inpCode.equals("")) {
            final SwitchTypeCodeAndNextOperation operation = new SwitchTypeCodeAndNextOperation();
            operation.setIdUser(StaticData.getUser().getIdUser());
            operation.setCode(inpCode);
            operation.start();
            try {
                operation.join();
            } catch (InterruptedException e) {
                if(BuildConfig.DEBUG){
                    Log.e(StaticData.getTAG(),e.getMessage());
                }
            }
            String type = operation.getType();
            final Report report = operation.getReport();

            if (type.equals("report")){
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myPlayer.setVisibility(View.GONE);
                        webView.setVisibility(View.GONE);
                        resultTV.setVisibility(View.VISIBLE);
                        resultTV.setText(report.getDocument());
                    }
                }, 1);

                if(BuildConfig.DEBUG){
                    Log.i(StaticData.getTAG(),"It is code equals REPORT");
                }

                Toast toast = Toast.makeText(this,"Report created by: "
                        +report.getPerson_name(),Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if(type.equals("exam")){
                final Examination examination = operation.getExamination();
                if(BuildConfig.DEBUG){
                    Log.i(StaticData.getTAG(),"It is code equals EXAM");
                }
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setLooping(false);
                try {
                    mediaPlayer.setDataSource(examination.getAudio());
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();

                } catch (IOException e) {
                    if(BuildConfig.DEBUG){
                        Log.e(StaticData.getTAG(),e.getMessage());
                    }
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myPlayer.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                        resultTV.setVisibility(View.VISIBLE);
                        resultTV.setText(examination.getDescription());
                    }
                }, 1);

                Toast toast = Toast.makeText(this,examination.getTitle()+
                        " conducted by: "+examination.getPerson_name(),Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if (type.equals("money")) {
                final int resultMoney = Integer.parseInt(operation.getResultAnswer());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(BuildConfig.DEBUG){
                            Log.i(StaticData.getTAG(),"It is code equals MONEY");
                        }
                        webView.setVisibility(View.GONE);
                        myPlayer.setVisibility(View.GONE);
                        resultTV.setVisibility(View.VISIBLE);

                        if (resultMoney==StaticData.getUser().getMoney()){
                            resultTV.setText(R.string.noMoney);
                            return;
                        }
                        StaticData.getUser().setMoney(resultMoney);
                        resultTV.setText(R.string.cashChanged);
                        //change money
                        onResume();
                    }
                }, 1);
                return;
            }

            if (type.equals("alert") ) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(BuildConfig.DEBUG){
                            Log.i(StaticData.getTAG(),"It is code equals ALERT");
                        }
                        webView.setVisibility(View.GONE);
                        myPlayer.setVisibility(View.GONE);
                        resultTV.setVisibility(View.VISIBLE);

                        resultTV.setText(operation.getResultAnswer());
                    }
                }, 1);
                return;
            }

            if (type.equals("game")) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(BuildConfig.DEBUG){
                            Log.i(StaticData.getTAG(),"It is code equals GAME");
                        }
                        webView.setVisibility(View.VISIBLE);
                        myPlayer.setVisibility(View.GONE);
                        resultTV.setVisibility(View.GONE);

                        String url = operation.getResultAnswer();
                        webView.loadUrl(url);
                    }
                }, 1);
                return;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    webView.setVisibility(View.GONE);
                    myPlayer.setVisibility(View.GONE);
                    resultTV.setVisibility(View.VISIBLE);
                    if(BuildConfig.DEBUG){
                        Log.i(StaticData.getTAG(),"Code was not found");
                    }
                    resultTV.setText(R.string.enterCorrectCode);
                }
            }, 1);
        }else{
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    webView.setVisibility(View.GONE);
                    myPlayer.setVisibility(View.GONE);
                    resultTV.setVisibility(View.VISIBLE);
                    if(BuildConfig.DEBUG){
                        Log.i(StaticData.getTAG(),"Empty input");
                    }
                    resultTV.setText(R.string.enterCode);
                }
            }, 1);

        }

    }

}
