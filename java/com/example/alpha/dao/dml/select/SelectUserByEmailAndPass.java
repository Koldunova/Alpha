package com.example.alpha.dao.dml.select;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.User;
import com.example.alpha.parsers.MyJSONParser;

public class SelectUserByEmailAndPass extends Thread {
    private User user;
    private String valueEmail;
    private String valuePass;
    private final String myUrl = "http://194.87.111.127/Php/selectUserByEmailAndPass.php";
    private String parammetrs;

    public void run() {
        parammetrs = "Email=" + getValueEmail() + "&Password=" + getValuePass();
        byte[] data = null;
        InputStream is = null;
        String resultString = "";

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

            MyJSONParser jsonParser = new MyJSONParser();
            setUser(jsonParser.userJSON(resultString));
        } catch (Exception e) {
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }
    }

    public String getValueEmail() {
        return valueEmail;
    }

    public void setValueEmail(String valueEmail) {
        this.valueEmail = valueEmail;
    }

    public String getValuePass() {
        return valuePass;
    }

    public void setValuePass(String valuePass) {
        this.valuePass = valuePass;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
