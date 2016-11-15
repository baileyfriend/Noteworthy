package com.dev.baileyfreund.notes;

/**
 * Created by baileyfreund on 11/14/16.
 */

public class Note {
    int noteId;
    String title;
    String body;

    public Note(int noteId, String title, String body) {
        this.noteId = noteId;
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
