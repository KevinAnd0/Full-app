package com.company;

import express.Express;
import express.middleware.Middleware;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class JExpress {

    public void callExpress() {

        Express app = new Express();
        Database db = new Database();

        app.get("/rest/notes", (request, response) -> {
            List<Note> notes = db.getNotes();

            response.json(notes);
        });


        app.get("/rest/notes/:id", (request, response) -> {

            int id = 0;
            try {
                id = Integer.parseInt(request.getParam("id"));

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Note note = db.getNoteByID(id);
            response.json(note);
        });

        app.get("/rest/getNotesOrderByTitle", (request, response) -> {
            List<Note> notes = db.getNotesOrderByTitle();

            response.json(notes);
        });

        app.get("/rest/getNotesOrderByLastUpdateAsc", (request, response) -> {
            List<Note> notes = db.getNotesOrderByLastupdateAsc();

            response.json(notes);
        });

        app.get("/rest/getNotesOrderByLastUpdateDesc", (request, response) -> {
            List<Note> notes = db.getNotesOrderByLastupdateDesc();

            response.json(notes);
        });

        app.get("/rest/notes/search/:searchString", (request, response) -> {

            String searchString = request.getParam("searchString");
            List<Note> notes = db.searchDatabaseByTitle(searchString);

            response.json(notes);
        });

        app.post("/rest/notes", (request, response) -> {
            Note note = (Note) request.getBody(Note.class);

            db.createNote(note);
            response.send();
        });


        app.post("/api/images-upload",(request, response) -> {
            String imageUrl = null;

            try {
                List<FileItem> images = request.getFormData("images");
                imageUrl = db.uploadImage(images.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.send(imageUrl);
        });

        app.post("/api/file-upload", (request, response) -> {
            String fileUrl = null;

            try {
                List<FileItem> files = request.getFormData("files");
                fileUrl = db.uploadFile(files.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.send(fileUrl);
        });

        app.put("/rest/notes/id", (request, response) -> {
            Note note = (Note) request.getBody(Note.class);
//
            db.updateNote(note);

            response.send("Update Successful!");
        });

        app.delete("/rest/notes/:id", (request, response) -> {

            int id = 0;
            try {
                id = Integer.parseInt(request.getParam("id"));


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            db.deleteNote(id);
            response.json(id);
        });
        
        try {
            app.use(Middleware.statics(Paths.get("src/frontend").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

        app.listen(2000);
        System.out.println("Server started on port 2000");

    }
}
