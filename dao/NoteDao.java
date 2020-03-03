package dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.cj.jdbc.CallableStatement;

import DB.MyConnection;
import domain.Note;

public class NoteDao {
    
    public void deleteNote(int id) {
        String sql="call alpha.deleteNoteById("+id+")";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            callableStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Exception in NoteUser deleteNote");
            System.out.println(e.getMessage());
        }
    }
    
    public void createNewNote(Note note) {
        String sql = "call alpha.insertNoteById('"+note.getTitle()+"', '"+note.getTextNote()+"',"+note.getIdUser()+" );";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            callableStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Exception in NoteUser createNewNote");
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<Note> selectNoteByIdUser(String idUser) {
        String sql="call alpha.selectNoteByIdUser("+idUser+");";
        ArrayList<Note> notes = new ArrayList<>();
        try {
            try {
                CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
                ResultSet resultSet = callableStatement.executeQuery();
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setIdUser(idUser);
                    note.setDate(resultSet.getString("date_note"));
                    note.setTitle(resultSet.getString("title"));
                    note.setTextNote( resultSet.getString("text_note"));
                    note.setIdNote(resultSet.getInt("id_note"));
                    notes.add(note);
                }
            }catch (Exception e) {
                System.out.println("Exception in NoteUser selectNoteById");
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println("Exception in NoteUser selectNoteById");
            System.out.println(e.getMessage());
        }
        return notes;
    }
}
