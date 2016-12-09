package com.dev.baileyfreund.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dev.baileyfreund.notes.db.NoteContract;
import com.dev.baileyfreund.notes.db.NoteDbHelper;

/**
 * This class will take care of any operations regarding
 * notes. It will also implement any buttons clicked on
 * the menu, updating the UI based on what the user clicked.
 *
 * @author Bailey Fruend and Jamie Penzien
 */
public class OpenNote extends AppCompatActivity {

    /**
     * This string represents a tag for the openNote Activity.
     */
    private static final String TAG = "openNote Activity";

    /**
     * This contains the note selected in the main activity.
     */
    private EditText editText;

    /**
     * This database helper will assist in forming a data
     * base out of notes.
     */
    private NoteDbHelper mHelper;


    /**
     * This method will set up the activity whenever a note is
     * openend. When opened, the app will set the content view,
     * make a toolbar, and initialize the Database Helper and the
     * Note List View.
     *
     * @param savedInstanceState This saved state is a previous
     *                           setup of the app that the user
     *                           had. If there is a saved instance,
     *                           the app will be set up in that manner
     *                           using this parameter.
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);
        mHelper = new NoteDbHelper(this);


        final int newText = (40);
        Intent intent = getIntent();
        String note = intent.getStringExtra(MainActivity.EXTRA_NOTE);
        editText = new EditText(this);
        editText.setTextSize(newText);
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
    public final boolean onCreateOptionsMenu(final Menu menu) {
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
    public final void deleteNote(final MenuItem deleteIcon) {
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
    public final void saveNote(final MenuItem saveIcon) {
        Intent intent = getIntent();
        String originalNote = intent.getStringExtra(MainActivity.EXTRA_NOTE);
        String note = String.valueOf(editText.getText());
        SQLiteDatabase db = mHelper.getReadableDatabase();

        long id = getIdFromNote(originalNote);
        updateItemForId(id);
        goToMain();
    }

    /**
     * This class will update the note for id. It
     * will save any changes made.
     *
     * @param id is the id of the item being
     *           updated.
     */
    private void updateItemForId(final long id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(NoteContract.NoteEntry.TABLE,
                new String[]{NoteContract.NoteEntry._ID,
                        NoteContract.NoteEntry.COL_NOTE_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getLong(0) == id) {
                String note = String.valueOf(editText.getText());
                Log.d(TAG, "Note: " + note);
                mHelper.updateRow(id, note);
            }
        }
        cursor.close();
        db.close();
        goToMain();
    }


    /**
     * This method will get the row's id from the
     * string of the note.
     *
     * @param note the note that you want to get the id of
     * @return the id of the note string as a long
     */
    public final long getIdFromNote(final String note) {
        String query = "SELECT rowid"
                + " FROM " + NoteContract.NoteEntry.TABLE
                + " WHERE " + NoteContract.NoteEntry.COL_NOTE_TITLE
                + " = ?;";
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return DatabaseUtils.longForQuery(db, query, new String[] {note});
    }

    /**
     * This is called when the current note is deleted.
     * Because the current note has been deleted, the user
     * is sent back to the main activity as there is
     * nothing left in the openNote activity.
     */
    public final void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
