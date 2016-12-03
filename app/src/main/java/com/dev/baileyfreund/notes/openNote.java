package com.dev.baileyfreund.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dev.baileyfreund.notes.db.NoteContract;
import com.dev.baileyfreund.notes.db.NoteDbHelper;

public class openNote extends AppCompatActivity {
    /**
     * This string represents a tag for the openNote Activity.
     */
    private static final String TAG = "openNote Activity";

    /*
     * @This is the edit text on the open note activity page - contains the note selected
     * in the main activity
     */
    private EditText editText;

    /**
     * This database helper will assist in forming a data
     * base out of notes.
     */
    private NoteDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);
        mHelper = new NoteDbHelper(this);


        Intent intent = getIntent();
        String note = intent.getStringExtra(MainActivity.EXTRA_NOTE);
        editText = new EditText(this);
        editText.setTextSize(40);
        editText.setText(note);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_open_note);
        layout.addView(editText);
        }

    /**
     * This method sets up the menu when the app is opened.
     * It will inflate the menu and add items to the action bar
     * if it is present.
     *
     * @param menu this is the menu that already exists. The
     *             method will take it and make it appear
     *             on the screen.
     * @return this method will return the menu created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_open_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method will delete a note from the screen and
     * remove it from the database. It does this by obtaining
     * the view and then scanning through it to find the
     * intended note to be deleted. Once deleted, the database
     * is closed and the UI updated.
     *
     * @param deleteIcon is the current view on the screen that the
     *             method uses to find which note to be deleted.
     */
    public void deleteNote(MenuItem deleteIcon) {
        String note = String.valueOf(editText.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(NoteContract.NoteEntry.TABLE,
                NoteContract.NoteEntry.COL_NOTE_TITLE + " = ?",
                new String[]{note});
        db.close();
        goToMain();
    }

    /**
     * This method will save the note from the screen and
     * save it to the database.
     *
     * @param saveIcon is the current view on the screen that the
     *             method uses to find which note to be saved.
     */
    public void saveNote(MenuItem saveIcon) {
        Intent intent = getIntent();
        String originalNote = intent.getStringExtra(MainActivity.EXTRA_NOTE);
        String note = String.valueOf(editText.getText());
        SQLiteDatabase db = mHelper.getReadableDatabase();

        long id = getIdFromNote(originalNote);
        mHelper.updateRow(id, note);

        goToMain();

}

//        // Gets the data repository in write mode
//        //SQLiteDatabase db = mHelper.getReadableDatabase();

//
//        // New value for one column
//        ContentValues values = new ContentValues();
//        values.put(NoteContract.NoteEntry.COL_NOTE_TITLE, note);
//        Log.d(TAG, "CONTENT VALUES: " + values);
//
//        // Which row to update, based on the title
//        String selection = NoteContract.NoteEntry.COL_NOTE_TITLE + " LIKE ?";
//        Log.d(TAG, "The selection is: " + selection);
//        String[] selectionArgs = { getIdFromNote(originalNote)+  "" };
//        Log.d(TAG, "PAST SELECTION ARGS " + originalNote);
//
//        int count = db.update(
//                NoteContract.DB_NAME,
//                values,
//                selection,
//                selectionArgs);
//        Log.d(TAG, "count " + count);
//        db.close();


        //        String query = "UPDATE " + NoteContract.DB_NAME  +
//                "SET COL_NOTE_TITLE = note\n" +
//                "WHERE COL_NOTE_TITLE = originalNote;";
//        Cursor cursor = db.query(NoteContract.NoteEntry.TABLE,
//                new String[]{NoteContract.NoteEntry._ID, NoteContract.NoteEntry.COL_NOTE_TITLE},
//                null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int i = cursor.getColumnIndex(NoteContract.NoteEntry.COL_NOTE_TITLE);
//            noteList.add(cursor.getString(i));//gets the string at the index in the database
//
//
//        }

    private void updateItemForId(long id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.getRow(id);
        if(cursor.moveToFirst()){
            String note = String.valueOf(editText.getText());
            mHelper.updateRow(id, note);
        }
        cursor.close();
    }



    public long getIdFromNote(String note){
        String query = "SELECT rowid" +
                " FROM " + NoteContract.DB_NAME +
                " WHERE " + NoteContract.NoteEntry.COL_NOTE_TITLE + " = ?;";
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return DatabaseUtils.longForQuery(db, query, new String[]{ note });
    }

    /**
     * This is called when the current note is deleted. Because the current note has been deleted,
     * the user is sent back to the main activity as there is nothing left in the openNote activity
     */
    public void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
