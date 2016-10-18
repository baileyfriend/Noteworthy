
package com.dev.baileyfreund.notes.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * This class assists with the making of a database that
 * can help store the information of the notes.
 *
 * @author Bailey Freund and Jamie Penzien
 */
public class NoteDbHelper extends SQLiteOpenHelper {

    /**
     * This method is the constructor. Given context, the
     * constructor will instantiate a NoteContract database
     * and set its name to null.
     *
     * @param context this parameter is the information that
     *                will be used to establish the database.
     */
    public NoteDbHelper(final Context context) {
        super(context, NoteContract.DB_NAME, null, NoteContract.DB_VERSION);
    }

    /**
     * This method sets up the database once the app is
     * created or opened. It takes a parameter of a database
     * and then uses that to instantiate the database. This is done
     * by creating a string of information containing the information
     * necessary to make a NoteContract database.
     *
     * @param db This is the database of information that is going
     *           to be used to establish the NoteContract database.
     */
    @Override
    public void onCreate(final SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + NoteContract.NoteEntry.TABLE
                + " ( " + NoteContract.NoteEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NoteContract.NoteEntry.COL_NOTE_TITLE
                + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    /**
     * This method will take an old version of the database and
     * update it with a new version which is provided in the parameter.
     * It will also increase the counting integer that keeps track of
     * which version the database is.
     *
     * @param db this is the new database that will replace the old.
     * @param oldVersion this is the old version number of the database.
     * @param newVersion this is the new version number of the database.
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE);
        onCreate(db);
    }
}
