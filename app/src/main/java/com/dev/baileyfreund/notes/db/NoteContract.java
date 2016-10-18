
package com.dev.baileyfreund.notes.db;

import android.provider.BaseColumns;

/**
 * This class represents the database set up that is made up
 * of objects of notes. It contains the class necessary to make
 * said notes. This class has a string for the name of the
 * database and a counting integer that will keep track of the
 * version of the database.
 *
 * @author Bailey Freund and Jamie Penzien
 */
public class NoteContract {

    /**
     * This String represents the name of the database.
     */
    public static final String DB_NAME = "com.dev.baileyfreund.notes.db";

    /**
     * This int will keep track of which version of the
     * database is being used.
     */
    public static final int DB_VERSION = 1;

    /**
     * This class is an object that can be used to create
     * a note entry. It contains a string for the note itself
     * and a string for the title of the note.
     */
    public static class NoteEntry implements BaseColumns {

        /**
         * This String contains the actual note part of the note.
         */
        public static final String TABLE = "notes";

        /**
         * This string contains the title of the note.
         */
        public static final String COL_NOTE_TITLE = "title";
    }
}
