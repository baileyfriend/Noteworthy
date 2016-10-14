package com.dev.baileyfreund.notes.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by baileyfreund on 10/10/16.
 */

public class NoteDbHelper extends SQLiteOpenHelper {

//    public static final String DATABASE_NAME = "note.db";
//    public static final String TABLE_NAME = "note_table";
//    public static final String COL_0 = "TITLE";
//    public static final String COL_1 = "NOTE";

    public NoteDbHelper(Context context){
        super(context, NoteContract.DB_NAME, null, NoteContract.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + NoteContract.NoteEntry.TABLE + " ( " +
                NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                NoteContract.NoteEntry.COL_NOTE_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE);
        onCreate(db);
    }

//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor result = db.rawQuery("select * from " + TABLE_NAME,null);
//        return result;
//    }


}
