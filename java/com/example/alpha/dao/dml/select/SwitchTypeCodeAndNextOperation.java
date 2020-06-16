package com.example.alpha.dao.dml.select;

import android.util.Log;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Examination;
import com.example.alpha.domain.Report;
import com.example.alpha.parsers.MyJSONParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class SwitchTypeCodeAndNextOperation extends Thread {
    private int idUser;
    private String code;
    private String type="";
    private String resultAnswer;
    private Report report;
    private Examination examination;
    private final String myUrl = "http://194.87.111.127/Php/switchTypeCodeAndNextOperation.php";
    private String parammetrs;
    
    
    public String getType() {
        return type;
    }

    public String getResultAnswer() {
        return resultAnswer;
    }

    public Report getReport() {
        return report;
    }

    public Examination getExamination() {
        return examination;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public void run() {
        parammetrs = "id=" + idUser + "&code=" + code;
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
            if(resultString.equals("\"none exist\"")) {
                type="none";
                return;
            }
            JSONObject json_data = new JSONObject(resultString);
            type = (json_data.getString("type"));
            if (type.equals("report")) {
                report = jsonParser.reportJSON(resultString);
            }
            if (type.equals("exam")) {
                examination = jsonParser.examinationJSON(resultString);
            }
            if (type.equals("money") || type.equals("alert") || type.equals("game")) {
                resultAnswer = jsonParser.infJSON(resultString, type);
            }
        } catch (Exception e) {
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }
    }
    
    
}
