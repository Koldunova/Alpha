package com.example.alpha.dao.dml.insert;

import android.os.Build;
import android.util.Log;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import androidx.annotation.RequiresApi;

public class InsertNewUser extends Thread {
    private String login;
    private String password;
    private final String MY_URL = "http://194.87.111.127/Php/insertNewUser.php";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run() {
        String parameters = "login=" + login + "&password=" + password;
        System.out.println(parameters);
        byte[] data = null;
        String resultString = "";
        InputStream is = null;
        
        try {
            URL url = new URL(MY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            OutputStream os = conn.getOutputStream();
            data = parameters.getBytes(StandardCharsets.UTF_8);
            os.write(data);
            data = null;

            conn.connect();
            int responseCode = conn.getResponseCode();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (responseCode == 200) {
                is = conn.getInputStream();
                byte[] buffer = new byte[8129];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                data = baos.toByteArray();
                resultString = new String(data, StandardCharsets.UTF_8);
            } else {
                resultString = "code not 200";
            }
        } catch (Exception e) {
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
