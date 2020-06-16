package com.example.alpha.dao.dml.select;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Person;
import com.example.alpha.parsers.MyJSONParser;

public class SelectPersonByDNA extends Thread {
    private Person person;
    private String value;
    private final String myUrl = "http://194.87.111.127/Php/selectPersonByDNA.php";
    private String parammetrs = "DNA=";

    public void run() {
        parammetrs += getValue();
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
                resultString="code not 200";
            }

            MyJSONParser jsonParser = new MyJSONParser();
            setPerson(jsonParser.pesronJSON(resultString));

        } catch (Exception e) {
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
