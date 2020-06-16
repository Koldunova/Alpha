package com.example.alpha;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.example.alpha.dao.dml.delete.DeleteNoteById;
import com.example.alpha.dao.dml.insert.InsertNoteById;
import com.example.alpha.dao.dml.select.SelectNoteByIdUser;
import com.example.alpha.dao.dml.update.UpdateNoteById;
import com.example.alpha.data.StaticData;
import com.example.alpha.domain.Note;
import com.example.alpha.myactivity.MyNavigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ListNoteActivity extends MyNavigation {

    private ArrayList<Note> notes;
    ListView listView;

    String positionOfSelected="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        mHandler=new WeakHandler();

        selNote =findViewById(R.id.butNoteSel);
        newNote=findViewById(R.id.butNewNote);

        listView=findViewById(R.id.noteList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //load inf in blocks
                if(BuildConfig.DEBUG){
                    Log.i(StaticData.getTAG(),"Click on item");
                }

                positionOfSelected=Integer.toString(position);
                countDelete=0;

                ((EditText)findViewById(R.id.titleNote)).setText(notes.get(position).getTitle());
                ((EditText)findViewById(R.id.textNote)).setText(notes.get(position).getText_note());
                ((TextView)findViewById(R.id.dateNote)).setText(notes.get(position).getDate());
                //unable blocks
                findViewById(R.id.titleNote).setEnabled(false);
                findViewById(R.id.textNote).setEnabled(false);
                //change buttons
                selNote.setVisibility(View.VISIBLE);
                newNote.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListView();
    }

    LinearLayout newNote;
    LinearLayout selNote;

    public void onNewNoteClick(View v){
        isEdit=false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(BuildConfig.DEBUG){
                    Log.i(StaticData.getTAG(),"Click on new note");
                }
                //clean blocks
                ((EditText)findViewById(R.id.titleNote)).setText("");
                ((TextView)findViewById(R.id.dateNote)).setText("");
                ((EditText)findViewById(R.id.textNote)).setText("");
                //change buttons
                newNote.setVisibility(View.VISIBLE);
                selNote.setVisibility(View.GONE);
                //enable blocks
                findViewById(R.id.titleNote).setEnabled(true);
                findViewById(R.id.textNote).setEnabled(true);
            }
        },1);

    }

    boolean isEdit=false;

    public void onSaveClick(View v){
        final String title=((EditText)findViewById(R.id.titleNote)).getText().toString();
        final String description =((EditText)findViewById(R.id.textNote)).getText().toString();
        Toast toast;
        if (isEdit){
            if(BuildConfig.DEBUG){
                Log.i(StaticData.getTAG(),"Save on edit mode");
            }
            if (!Pattern.matches("(\\w+\\s*)", title)){
                toast = Toast.makeText(this,"Please. enter correct title",Toast.LENGTH_LONG);
                toast.show();
            }else{
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateNoteById updateNote = new UpdateNoteById();
                        Note note = new Note();
                        note.setTitle(title);
                        note.setText_note(description);
                        note.setId(notes.get(Integer.parseInt(positionOfSelected)).getId());

                        updateNote.setNote(note);
                        updateNote.start();
                        try {
                            updateNote.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },1);

                toast = Toast.makeText(this,"Success, note save",Toast.LENGTH_LONG);
                toast.show();
                ((TextView)findViewById(R.id.dateNote)).setText("");
                ((EditText)findViewById(R.id.titleNote)).setText("");
                ((EditText)findViewById(R.id.textNote)).setText("");

                findViewById(R.id.butNewNote).setVisibility(View.GONE);

                updateListView();
            }
        }else{
            if(BuildConfig.DEBUG){
                Log.i(StaticData.getTAG(),"Save on new note mode");
            }
            if (!Pattern.matches("(\\w+\\s*)", title)){
                toast = Toast.makeText(this,"Please. enter correct title",Toast.LENGTH_LONG);
                toast.show();
            }else{
                InsertNoteById insertNote = new InsertNoteById();
                insertNote.setIdUser(StaticData.getUser().getIdUser());

                Note note= new Note();
                note.setText_note(description);
                note.setTitle(title);
                insertNote.setNote(note);

                insertNote.start();
                try {
                    insertNote.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                toast = Toast.makeText(this,"Success, note save",Toast.LENGTH_LONG);
                toast.show();
                ((TextView)findViewById(R.id.dateNote)).setText("");
                ((EditText)findViewById(R.id.titleNote)).setText("");
                ((EditText)findViewById(R.id.textNote)).setText("");

                findViewById(R.id.butNewNote).setVisibility(View.GONE);

                updateListView();
            }
        }
        updateListView();
    }

    public void onMyBackClick(View v){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //disable text
                findViewById(R.id.textNote).setEnabled(false);
                findViewById(R.id.titleNote).setEnabled(false);

                if (isEdit){
                    if(BuildConfig.DEBUG){
                        Log.i(StaticData.getTAG(),"Back on edit mode");
                    }
                    //change buttons
                    newNote.setVisibility(View.GONE);
                    selNote.setVisibility(View.VISIBLE);

                    //clear all
                    ((EditText)findViewById(R.id.titleNote)).setText(notes.get(Integer.parseInt(positionOfSelected)).getTitle());
                    ((EditText)findViewById(R.id.textNote)).setText(notes.get(Integer.parseInt(positionOfSelected)).getText_note());
                    ((TextView)findViewById(R.id.dateNote)).setText(notes.get(Integer.parseInt(positionOfSelected)).getDate());

                    //unable blocks
                    findViewById(R.id.titleNote).setEnabled(false);
                    findViewById(R.id.textNote).setEnabled(false);
                }else{
                    if(BuildConfig.DEBUG){
                        Log.i(StaticData.getTAG(),"Back on new note mode");
                    }
                    //clean all
                    ((EditText)findViewById(R.id.titleNote)).setText("");
                    ((TextView)findViewById(R.id.dateNote)).setText("");
                    ((EditText)findViewById(R.id.textNote)).setText("");

                    //hide buttons
                    newNote.setVisibility(View.GONE);
                    selNote.setVisibility(View.GONE);
                }
            }
        },1);

    }

    int countDelete=0;

    public void onDeleteClick(View v){
        Toast toast;
        if (countDelete==0){
            if(BuildConfig.DEBUG){
                Log.i(StaticData.getTAG(),"Delete need accept");
            }
            //accept
            countDelete++;
            toast = Toast.makeText(this,"Please, click on Delete one more, for accept",Toast.LENGTH_LONG);
            toast.show();
        }else{
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(BuildConfig.DEBUG){
                        Log.i(StaticData.getTAG(),"Delete need accept");
                    }
                    //delete
                    countDelete=0;
                    DeleteNoteById deleteNoteById = new DeleteNoteById();
                    deleteNoteById.setValue(notes.get(Integer.parseInt(positionOfSelected)).getId());
                    deleteNoteById.start();
                    try {
                        deleteNoteById.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //clean all
                    ((EditText)findViewById(R.id.titleNote)).setText("");
                    ((TextView)findViewById(R.id.dateNote)).setText("");
                    ((EditText)findViewById(R.id.textNote)).setText("");

                    updateListView();
                }
            },1);
        }
    }

    public void onEditClick(View v){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countDelete=0;
                if(BuildConfig.DEBUG){
                    Log.i(StaticData.getTAG(),"Edit note");
                }
                isEdit=true;
                //changeBut
                newNote.setVisibility(View.VISIBLE);
                selNote.setVisibility(View.GONE);

                //enable input text
                findViewById(R.id.textNote).setEnabled(true);
                findViewById(R.id.titleNote).setEnabled(true);
            }
        },1);

    }

    SimpleAdapter listAdapter;

    public void updateListView(){
        if(BuildConfig.DEBUG){
            Log.i(StaticData.getTAG(),"ListView updated");
        }
        SelectNoteByIdUser selectNoteByIdUser = new SelectNoteByIdUser();
        selectNoteByIdUser.setValue(Integer.toString(StaticData.getUser().getIdUser()));
        selectNoteByIdUser.start();

        try {
            selectNoteByIdUser.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notes=selectNoteByIdUser.getNotes();

        ListView listView=findViewById(R.id.noteList);

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        HashMap<String,String> map;

        if (notes!=null) {
            for (Note note : notes) {
                map = new HashMap<>();
                map.put("Title", note.getTitle());
                map.put("Date", "Created: " + note.getDate());
                arrayList.add(map);
            }

            listAdapter = new SimpleAdapter(this, arrayList,
                    R.layout.rowlayout, new String[]{"Title", "Date"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listView.setAdapter(listAdapter);
        }
    }
}
