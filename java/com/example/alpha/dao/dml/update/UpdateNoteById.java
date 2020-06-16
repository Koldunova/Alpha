package com.example.alpha.dao.dml.update;

import android.util.Log;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Note;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateNoteById extends Thread{
    private Note note;
    private final String myUrl = "http://194.87.111.127/Php/updateNoteById.php";
    private String parammetrs;
    private String resultString;

    public void run() {
        parammetrs = "id=" + getNote().getId() + "&title=" + getNote().getTitle() +"&text="+getNote().getText_note();
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

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

}
