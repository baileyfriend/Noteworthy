package com.dev.baileyfreund.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class openNote extends AppCompatActivity {
    Note selectedNote;
    String title = getNoteTitle();
    int id = getNoteId();
    String body = getNoteBody();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);
        TextView t = (TextView)findViewById(R.id.noteBody);
        t.setText(body);
    }

    public int getNoteId(){
        return selectedNote.getNoteId();
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public String getNoteTitle(){
        return selectedNote.getTitle();
    }

    public String getNoteBody(){
        return selectedNote.getBody();
    }
}
