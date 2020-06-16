package com.example.alpha.dao.dml.update;

import android.util.Log;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UpdateUserPassword extends Thread {
    private String password;
    private int idUser;
    private final String myUrl = "http://194.87.111.127/Php/updateUserPassword.php";
    private String parammetrs;
    private String resultString;

    public void run() {
        parammetrs = "id=" + idUser + "&password=" + password;
        byte[] data = null;

        String resultString = "";
        InputStream is = null;
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
            OutputStream os = conn.getOutputStream();
            data = parammetrs.getBytes("UTF-8");
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
                resultString = new String(data, "UTF-8");
            } else {
                resultString = "code not 200";
            }
        } catch (Exception e) {
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }

    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


