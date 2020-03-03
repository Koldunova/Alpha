package service;

import java.util.ArrayList;

import dao.NoteDao;
import domain.Note;

public class NoteService {

    public static void createNote(Note note) {
        NoteDao noteDao = new NoteDao();
        noteDao.createNewNote(note);
    }
    
    public static ArrayList<Note> selectNotes(String idUser){
        NoteDao noteDao = new NoteDao();
        return noteDao.selectNoteByIdUser(idUser);
    }

    public static void deleteNote(int id) {
        NoteDao noteDao = new NoteDao();
        noteDao.deleteNote(id);
    }
}
