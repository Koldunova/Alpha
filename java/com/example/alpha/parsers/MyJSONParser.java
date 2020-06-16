package com.example.alpha.parsers;

import android.util.Log;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.alpha.BuildConfig;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Examination;
import com.example.alpha.domain.Note;
import com.example.alpha.domain.Person;
import com.example.alpha.domain.Report;
import com.example.alpha.domain.User;

public class MyJSONParser {
    public Person pesronJSON(final String JSONLine) {
            try {
                final JSONObject json_data = new JSONObject(JSONLine);
                Person person = new Person();
                person.setPerson_name((json_data.getString("person_name")));
                person.setDescription((json_data.getString("description")));
                person.setPosition((json_data.getString("position")));
                person.setPicture((json_data.getString("picture")));
                return person;
            }catch (Exception e){
                if(BuildConfig.DEBUG){
                    Log.e(StaticData.getTAG(),e.getMessage());
                }
            }
        return null;
    }

    public String isExist(String JSONLine) {
        if (JSONLine.length() >= 12) {
            JSONLine = "none exist";
        } else {
            JSONLine = "exist";
        }
        return JSONLine;
    }

    public User userJSON(String JSONLine) {
            try {
                JSONObject json_data = new JSONObject(JSONLine);
                User user = new User();
                user.setIdUser(Integer.parseInt(json_data.getString("id_user")));
                user.setMoney(Integer.parseInt(json_data.getString("money")));
                return user;
            }catch (Exception e){
                if(BuildConfig.DEBUG){
                    Log.e(StaticData.getTAG(),e.getMessage());
                }
            }
        return null;
    }

    public Report reportJSON(String JSONLine) {
            try {
                JSONObject json_data = new JSONObject(JSONLine);
                Report report = new Report();
                report.setDocument(json_data.getString("document"));
                report.setPerson_name(json_data.getString("person_name"));
                return report;
            }catch (Exception e){
                if(BuildConfig.DEBUG){
                    Log.e(StaticData.getTAG(),e.getMessage());
                }
            }
        return null;
    }

    public String infJSON(String JSONLine, String type) {
        try {
            JSONObject json_data = new JSONObject(JSONLine);
            if (type.equals("money")) {
                return json_data.getString("money");
            }
            if (type.equals("alert")) {
                return json_data.getString("alert");
            }
            if (type.equals("game")) {
                return json_data.getString("link");
            }
        }catch (Exception e){
            if(BuildConfig.DEBUG){
                Log.e(StaticData.getTAG(),e.getMessage());
            }
        }
        return null;
    }

    public ArrayList<Note> notesJSON(String JSONLine) {
            try {
                JSONArray json_array = new JSONArray(JSONLine);
                ArrayList<Note> notes = new ArrayList<>();
                for (int i=0;i<json_array.length();i++){
                    JSONObject json_data = new JSONObject((String) json_array.get(i));
                    Note note = new Note();
                    note.setId(json_data.getString("id_note"));
                    note.setText_note(json_data.getString("text_note"));
                    note.setTitle(json_data.getString("title"));
                    note.setDate(json_data.getString("date_note"));
                    notes.add(note);
                }
                return notes;
            }catch (Exception e) {
                if(BuildConfig.DEBUG){
                    Log.e(StaticData.getTAG(),e.getMessage());
                }
            }
        return null;
    }

    public Examination examinationJSON(String JSONLine) {
            try {
                JSONObject json_data = new JSONObject(JSONLine);
                Examination examination = new Examination();
                examination.setAudio(json_data.getString("audio"));
                examination.setDescription(json_data.getString("description"));
                examination.setPerson_name(json_data.getString("person_name"));
                examination.setTitle(json_data.getString("title"));
                return examination;
            }catch (Exception e){
                if(BuildConfig.DEBUG){
                    Log.e(StaticData.getTAG(),e.getMessage());
                }
            }
        return null;
    }
}
