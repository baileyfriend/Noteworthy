package com.dev.baileyfreund.notes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.dev.baileyfreund.notes.db.NoteContract;
import com.dev.baileyfreund.notes.db.NoteDbHelper;

import java.util.ArrayList;

import static com.dev.baileyfreund.notes.R.styleable.View;

/**
 * This class will set up the UI so that way the user can
 * interact with the app. It allows them to click on buttons,
 * open the menu, and do things like add a note.
 *
 * @author Bailey Freund and Jamie Penzien
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This string represents a tag for the Main Activity.
     */
    private static final String TAG = "MainActivity";

    /**
     * This database helper will assist in forming a data
     * base out of notes.
     */
    private NoteDbHelper mHelper;

    /**
     * This will be the layout for the application.
     */
    private ListView mNoteListView;

    /**
     * This array adapter will hold an array of strings that
     * contain information about the layout of the application.
     */
    private ArrayAdapter<String> mAdapter;


    /**
     * This method will set up the app when it is created or
     * 'opened.' When opened, the app will set the content view,
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHelper = new NoteDbHelper(this);
        mNoteListView = (ListView) findViewById(R.id.list_todo);

        updateUI();
    }

    /**
     * This method will update what the user sees on the screen.
     * It will check to see if there is a new note added to the
     * array, and if there is it will add it.
     */
    private void updateUI() {

        //This is the array of the list of notes
        ArrayList<String> noteList = new ArrayList<>();

        //This uses the helper for the note database
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NoteContract.NoteEntry.TABLE,
                new String[]{NoteContract.NoteEntry._ID,
                        NoteContract.NoteEntry.COL_NOTE_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex(NoteContract.
                    NoteEntry.COL_NOTE_TITLE);
            //Gets the string at the index in the database
            noteList.add(cursor.getString(i));
            Log.d(TAG, "Note: " + cursor.getString(i));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this,
                    R.layout.item_todo, //Setting up the layout for the items
                    R.id.note_title, //Placing the string of data
                    noteList); //Finding where to get all of the data
            //Set it as the adapter for the ListView instance
            mNoteListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(noteList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method will allow a menu to open up and let the user
     * add a new note from it. It will then add the new note to
     * the database and make sure the UI is updated.
     *
     * @param item This is the existing menu; the method uses
     *            it to make a menu that user can use to add
     *             a new note.
     * @return this method will return the item selected.
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new note")
                        .setMessage("What do you want to take note of?")
                        .setView(taskEditText)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                                final int which) {
                                String note = String.valueOf(
                                        taskEditText.getText());
                                SQLiteDatabase db =
                                        mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(NoteContract.NoteEntry.
                                        COL_NOTE_TITLE, note);
                                db.insertWithOnConflict(NoteContract.
                                        NoteEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method will delete a note from the screen and
     * remove it from the database. It does this by obtaining
     * the view and then scanning through it to find the
     * intended note to be deleted. Once deleted, the database
     * is closed and the UI updated.
     *
     * @param view is the current view on the screen that the
     *             method uses to find which note to be delted.
     */
    private void deleteNote(final View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.note_title);
        String note = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(NoteContract.NoteEntry.TABLE,
                NoteContract.NoteEntry.COL_NOTE_TITLE + " = ?",
                new String[]{note});
        db.close();
        updateUI();
    }
}

