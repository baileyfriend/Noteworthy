package com.dev.baileyfreund.notes;

import android.content.Intent;
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

    /*
     * @TODO generate some comment on this
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
// @TODO implement this method and add it to the onClick listener for the save icon in the menu of this activity - remember to change the onclick for the saveIcon!!!!!!!!!!!
//    /**
//     * This method will delete a note from the screen and
//     * remove it from the database. It does this by obtaining
//     * the view and then scanning through it to find the
//     * intended note to be deleted. Once deleted, the database
//     * is closed and the UI updated.
//     *
//     * @param saveIcon is the current view on the screen that the
//     *             method uses to find which note to be deleted.
//     */
//    public void saveNote(MenuItem saveIcon) {
//        String note = String.valueOf(editText.getText());
//        // Gets the data repository in write mode
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//        // New value for one column
//        ContentValues values = new ContentValues();
//        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
//
//        // Which row to update, based on the title
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
//        String[] selectionArgs = { "MyTitle" };
//
//        int count = db.update(
//                FeedReaderDbHelper.FeedEntry.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        db.close();
//        goToMain();
//    }

    /**
     * This is called when the current note is deleted. Because the current note has been deleted,
     * the user is sent back to the main activity as there is nothing left in the openNote activity
     */
    public void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
