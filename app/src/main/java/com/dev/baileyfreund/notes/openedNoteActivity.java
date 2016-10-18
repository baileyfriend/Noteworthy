package com.dev.baileyfreund.notes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.baileyfreund.notes.db.NoteContract;
import com.dev.baileyfreund.notes.db.NoteDbHelper;

public class openedNoteActivity extends AppCompatActivity {


    private NoteDbHelper mHelper;
    private static int noteIndex;
    private TextView mNoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_note);

        mHelper = new NoteDbHelper(this);
        mNoteTextView = (TextView) findViewById(R.id.noteTextView);

        updateUI();
    }

    public static void setNoteIndex(int i) {

        noteIndex = i;
    }

    public int getNoteIndex(){
        return noteIndex;

    }

    public void updateUI() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NoteContract.NoteEntry.TABLE,
                new String[]{NoteContract.NoteEntry._ID, NoteContract.NoteEntry.COL_NOTE_TITLE},
                null, null, null, null, null);
        int IDint = cursor.getColumnIndex(NoteContract.NoteEntry._ID);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            int i = cursor.getColumnIndex(NoteContract.NoteEntry._ID); //@TODO this is only returning the column in which the id is found - not the actual index of the selected note. Figure out what the index of the selected note is!!
            Log.d("index : ", + i + "");
            Log.d("cursor string : ", cursor.getString(i)); //@TODO I made the assumption that the index was being numbered from 0 every time, but it will index on top of what has already happened. Must figure a way to get the correct note!
            if (cursor.getString(i).equals(getNoteIndex() )  ) {
                String note = cursor.getString(i);
                TextView textView = (TextView) findViewById(R.id.noteTextView);
                Log.d("textview contents : ", note);
                textView.setText(note);
        }
    }
    }
}
