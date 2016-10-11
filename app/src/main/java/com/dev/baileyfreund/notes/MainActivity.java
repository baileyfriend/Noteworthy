package com.dev.baileyfreund.notes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

import com.dev.baileyfreund.notes.db.NoteContract;
import com.dev.baileyfreund.notes.db.NoteDbHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private NoteDbHelper mHelper;
    private ListView mNoteListView;
    private ArrayAdapter<String> mAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHelper = new NoteDbHelper(this); //initializing the dbhelper
        mNoteListView = (ListView) findViewById(R.id.list_todo);

        updateUI();
    }

    private void updateUI(){

        ArrayList<String> noteList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NoteContract.NoteEntry.TABLE,
                new String[]{NoteContract.NoteEntry._ID, NoteContract.NoteEntry.COL_NOTE_TITLE},
                null, null, null, null,null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(NoteContract.NoteEntry.COL_NOTE_TITLE);
            Log.d(TAG, "Note: " + cursor.getString(idx));
        }

        if(mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this,
                    R.layout.item_todo, //what view to use for the items
                    R.id.note_title, //where to put the string of data
                    noteList); //where to get all the data
            mNoteListView.setAdapter(mAdapter); //set it as the adapter fo the ListView instance
        } else {
            mAdapter.clear();
            mAdapter.addAll(noteList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add a new note")
                    .setMessage("What do you want to take note of?")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String note = String.valueOf(taskEditText.getText());
                            Log.d(TAG, "Note to add: " + note);
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(NoteContract.NoteEntry.COL_NOTE_TITLE, note);
                            db.insertWithOnConflict(NoteContract.NoteEntry.TABLE,
                                    null,
                                    values,
                                    SQLiteDatabase.CONFLICT_REPLACE
                                    );
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
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Main Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        //client.connect();
////        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
////        AppIndex.AppIndexApi.end(client, getIndexApiAction());
////        client.disconnect();
//    }
}
