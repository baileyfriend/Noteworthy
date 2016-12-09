package com.dev.baileyfreund.notes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.baileyfreund.notes.db.NoteContract;
import com.dev.baileyfreund.notes.db.NoteDbHelper;

import java.util.ArrayList;

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
     * This will be the java representation of the
     * TextView in our main activity.
     */
    private ListView mNoteListView;

    /**
     * This array adapter will hold an array of strings that
     * contain information about the layout of the application.
     */
    private ArrayAdapter<String> mAdapter;
    /**
     * This will be the java representation of the
     * TextView in the main activity.
     */
    private TextView noteTextView;
    /**
     * This will be the java representation of the
     * DeleteButton in the main activity.
     */
    //private Button deleteButton;
    /**
     * This will be the java representation of the
     * Toolbar in the main activity.
     */
    private Toolbar toolbar;

    /**
     * This string contain the location
     * of the note.
     */
    public final static String EXTRA_NOTE
            = "com.dev.baileyfreund.notes.note_item";

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
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHelper = new NoteDbHelper(this); //initializing the dbhelper
        mNoteListView = (ListView) findViewById(R.id.list_todo);
        noteTextView = (TextView) findViewById(R.id.note_item);
        //deleteButton = (Button) findViewById(R.id.note_delete);
        //noteTextView.setTextSize(16);

        updateUI();
    }

    /**
     * This method will update what the user sees on the screen.
     * It will check to see if there is a new note added to the
     * database, and if there is it will add it.
     */
    private void updateUI() {

        //This is the array of the list of notes that
        // is passed to the adapter to be displayed
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
            noteList.add(cursor.getString(i));
            Log.d(TAG, "Note: " + cursor.getString(i));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this,
                    R.layout.item_todo, //what view to use for the items
                    R.id.note_item, //where to put the string of data
                    noteList); //where to get all the data
            mNoteListView.setAdapter(mAdapter);
            //set it as the adapter for the ListView instance
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the
        // action bar if present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method will allow a menu to open up and let the user
     * add a new note from it. It will then add the new note to
     * the database and make sure the UI is updated.
     *
     * @param item This is the new MenuItem being passed
     *             to the method; the method uses it to make
     *             a menu that user can use to add
     *             a new note.
     * @return this method will return true if new item
     *          has been added to notes.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new note")
                        .setMessage("Type noteworthy note here")
                        .setView(taskEditText)
                        .setPositiveButton("Add",
                                new DialogInterface.
                                        OnClickListener() {
                            @Override
                            public void onClick
                                    (DialogInterface dialog,
                                     int which) {
                                String note = String.
                                        valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(NoteContract.NoteEntry.COL_NOTE_TITLE, note);
                                db.insertWithOnConflict(NoteContract.NoteEntry.TABLE,
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
     * This method is called whenever a note is clicked in the Main activity.
     * When this method is called, it passes the selected note's title to the next activity
     * (openNote) so that the note can be loaded into the primary TextView on that activity.
     * @param v is the view being clicked on - in this case it is a TextView
     */
    public void onNoteClick(View v){
        //Creates a new intent to go to the openNote Activity
        Intent intent = new Intent(this, openNote.class);
        TextView textView = (TextView) findViewById(R.id.note_item);
        //Gets the string in the textview and passes it to the next activity to be displayed
        String note = textView.getText().toString();
        //the extra note is the note contained in the clicked note
        intent.putExtra(EXTRA_NOTE, note);
        //Then Start the activity
        startActivity(intent);
    }


}
