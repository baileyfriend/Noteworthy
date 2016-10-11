package com.dev.baileyfreund.notes.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by baileyfreund on 10/10/16.
 */

public class NoteDbHelper extends SQLiteOpenHelper {

    public NoteDbHelper(Context context){
        super(context, NoteContract.DB_NAME, null, NoteContract.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + NoteContract.NoteEntry.TABLE + " ( " +
                NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                NoteContract.NoteEntry.COL_NOTE_TITLE + " TEXT NOT NULL);";
        System.out.println("0 " + db);
        db.execSQL(createTable);
        System.out.println("1 " + db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE);
        System.out.println("2 " +db);
        onCreate(db);
        System.out.println("3 " +db);
    }


}
