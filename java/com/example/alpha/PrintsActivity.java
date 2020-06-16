package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.select.SelectPersonByDNA;
import com.example.alpha.dao.dml.select.SelectPersonByPrint;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Person;
import com.example.alpha.myactivity.MyNavigation;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PrintsActivity extends MyNavigation {

    SurfaceView cameraPre;
    TextView namePerson;
    ImageView imagePerson;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermission =1001;

    String previousCode ="";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RequestCameraPermission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(cameraPre.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.stop();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prints);
        mHandler= new WeakHandler();

        //barcodeDetector
        barcodeDetector = new BarcodeDetector.Builder(this).
                setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector).
                setRequestedPreviewSize(640,480).build();

        //scroll for description
        final TextView descriptionPerson = findViewById(R.id.textDescription);

        descriptionPerson.setMovementMethod(new ScrollingMovementMethod());

        //set objects
        cameraPre=findViewById(R.id.cameraPreview);
        namePerson=findViewById(R.id.textResult);
        imagePerson=findViewById(R.id.picturePerson);

        //description behavior of barcodeDetector
        cameraPre.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraPre.getHolder());
                } catch (IOException e) {
                    if(BuildConfig.DEBUG){
                        Log.e(StaticData.getTAG(),e.getMessage());
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                        cameraSource.stop();
                    }
                });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {
                        final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                        if (qrcodes.size() != 0) {
                            namePerson.post(new Runnable() {
                                @Override
                                public void run() {
                                    final String qrResult = qrcodes.valueAt(0).displayValue;
                                    if (!qrResult.equals(previousCode)) {
                                        Vibrator vibrator = (Vibrator) getApplicationContext().
                                                getSystemService(Context.VIBRATOR_SERVICE);
                                        vibrator.vibrate(100);
                                        imagePerson.setVisibility(View.VISIBLE);
                                        Person person;
                                        try {
                                            Integer.parseInt(qrResult);

                                            final SelectPersonByDNA selectPersonByDNA =
                                                    new SelectPersonByDNA();
                                            selectPersonByDNA.setValue(qrResult);
                                            selectPersonByDNA.start();
                                            try {
                                                selectPersonByDNA.join();
                                            } catch (InterruptedException e) {
                                                if(BuildConfig.DEBUG){
                                                    Log.e(StaticData.getTAG(),e.getMessage());
                                                }
                                            }
                                            person = selectPersonByDNA.getPerson();
                                        } catch (NumberFormatException e) {
                                            if(BuildConfig.DEBUG) {
                                                Log.i(StaticData.getTAG(), "It is not simple number");
                                            }
                                            final SelectPersonByPrint selectPersonByPrint =
                                                    new SelectPersonByPrint();
                                            selectPersonByPrint.setValue(qrResult);
                                            selectPersonByPrint.start();
                                            try {
                                                selectPersonByPrint.join();
                                            } catch (InterruptedException ex) {
                                                Log.e(StaticData.getTAG(),ex.getMessage());
                                            }
                                            person = selectPersonByPrint.getPerson();
                                        }
                                        if (person != null) {
                                            namePerson.setText(person.getPerson_name());
                                            descriptionPerson.setText(person.getDescription().
                                                    replace('\r', ' '));
                                            Picasso.get().load(person.getPicture()).into(imagePerson);
                                            previousCode =qrResult;
                                        } else {
                                            namePerson.setText(R.string.unknownCode);
                                            descriptionPerson.setText(R.string.unknownCode);
                                            imagePerson.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
    }

}