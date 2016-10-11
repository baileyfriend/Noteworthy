package com.dev.baileyfreund.notes.db;

import android.provider.BaseColumns;

public class NoteContract {
    public static final String DB_NAME = "com.dev.baileyfreund.notes.db";
    public static final int DB_VERSION = 1;

    public class NoteEntry implements BaseColumns {
        public static final String TABLE = "notes";

        public static final String COL_NOTE_TITLE = "title";
    }
}