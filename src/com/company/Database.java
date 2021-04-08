package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import express.utils.Utils;
import org.apache.commons.fileupload.FileItem;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class Database {

    private Connection conn;

    public Database(){

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:pim-group1.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Get all notes from database
    public List<Note> getNotes(){
        List<Note> notes=null;
        String query = "SELECT * FROM notes;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Note[] notesFromRS = (Note[]) Utils.readResultSetToObject(rs,Note[].class);
            notes = List.of(notesFromRS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notes;
    }

    // Get all notes from database ordered by title ASC
    public List<Note> getNotesOrderByTitle(){
        List<Note> notes=null;
        String query = "SELECT * FROM notes ORDER BY title;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Note[] notesFromRS = (Note[]) Utils.readResultSetToObject(rs, Note[].class);
            notes = List.of(notesFromRS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notes;
    }

    // Get all notes from database ordered by lastUpdate (ASC)
    public List<Note> getNotesOrderByLastupdateAsc(){
        List<Note> notes=null;
        String query = "SELECT * FROM notes ORDER BY lastUpdate;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Note[] notesFromRS = (Note[]) Utils.readResultSetToObject(rs, Note[].class);
            notes = List.of(notesFromRS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notes;
    }

    // Get all notes from database ordered by lastUpdate DESC
    public List<Note> getNotesOrderByLastupdateDesc(){
        List<Note> notes=null;
        String query = "SELECT * FROM notes ORDER BY lastUpdate DESC;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Note[] notesFromRS = (Note[]) Utils.readResultSetToObject(rs, Note[].class);
            notes = List.of(notesFromRS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notes;
    }


    // Get one specific note from database by id
    public Note getNoteByID(int id){
        Note note = null;
        String query = "SELECT * FROM notes WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Note[] notesFromRS = (Note[]) Utils.readResultSetToObject(rs,Note[].class);
            note = notesFromRS[0];
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return note;
    }


    // Add new note into database
    public void createNote(Note note){
        String query = "INSERT INTO notes (title, description, lastUpdate, imageUrl, fileUrl) VALUES (?,?, CURRENT_TIMESTAMP,?,?);";

        try {
            PreparedStatement stmt =conn.prepareStatement(query);
            stmt.setString(1, changeTitleToCapitalLetterFirst(note.getTitle()));
            stmt.setString(2,note.getDescription());
            stmt.setString(3,note.getImageUrl());
            stmt.setString(4, note.getFileUrl());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Update note in database
    public void updateNote(Note note) {
        String query = "UPDATE notes SET title = ?, description =?, lastUpdate = CURRENT_TIMESTAMP, imageUrl = ?, fileUrl = ? WHERE id = ?;";
        try {
            PreparedStatement stmt= conn.prepareStatement(query);
            stmt.setString(1, changeTitleToCapitalLetterFirst(note.getTitle()));
            stmt.setString(2,note.getDescription());
            stmt.setString(3, note.getImageUrl());
            stmt.setString(4, note.getFileUrl());
            stmt.setInt(5, note.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Delete note in database
    public void deleteNote(int id) {
        String query = "DELETE FROM notes WHERE id = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

   // Search in database om title-field
    public List<Note> searchDatabaseByTitle (String searchString){
        List<Note> notes = null;
        String query = "SELECT * FROM notes WHERE LOWER(title) LIKE ('%'||?||'%') ORDER BY TITLE;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,searchString);
            ResultSet rs = stmt.executeQuery();
            Note[] notesFromRS = (Note[]) Utils.readResultSetToObject(rs,Note[].class);
            notes = List.of(notesFromRS);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public String changeTitleToCapitalLetterFirst (String input) {
        char[] temp = input.toCharArray();
        char oldFirstLetter = input.charAt(0);
        char newFirstLetter = Character.toUpperCase(oldFirstLetter);
        temp[0] = newFirstLetter;
        String output = String.valueOf(temp);
        return output;
    }

    public String uploadImage(FileItem image) {
        String imageUrl = "/images/" + image.getName();

        try(var oStream = new FileOutputStream(Paths.get("src/frontend" + imageUrl).toString())) {

            oStream.write(image.get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return imageUrl;
    }

    public String uploadFile(FileItem file) {
        String fileUrl = "/files/" + file.getName();

        try(var oStream = new FileOutputStream(Paths.get("src/frontend" + fileUrl).toString())) {
            oStream.write(file.get());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return fileUrl;
    }
}


