package com.example.beetrueapplication;

public class Note {

    // Model class of the Note object

    String noteTitle, noteContent, noteID;

    public Note(String noteTitle, String noteContent, String noteID) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteID = noteID;
    }

    public Note() {
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }
}
