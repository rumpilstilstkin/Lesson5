package com.geekbrains.sqlietedatabaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteDataSource {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String[] notesAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NOTE,
            DatabaseHelper.COLUMN_TITLE
    };

    public NoteDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Note addNote(String title, String note) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOTE, note);
        values.put(DatabaseHelper.COLUMN_TITLE, title);
        long insertId = database.insert(DatabaseHelper.TABLE_NOTES, null,
                values);
        Note newNote = new Note();
        newNote.setNote(note);
        newNote.setTitle(title);
        newNote.setId(insertId);
        return newNote;
    }

    public void editNote(long id, String note, String title) {
        ContentValues editedNote = new ContentValues();
        editedNote.put(dbHelper.COLUMN_ID, id);
        editedNote.put(dbHelper.COLUMN_NOTE, note);
        editedNote.put(dbHelper.COLUMN_TITLE, title);

        database.update(dbHelper.TABLE_NOTES,
                editedNote,
                dbHelper.COLUMN_ID + "=" + id,
                null);
    }

    public void deleteNote(Note note) {
        long id = note.getId();
        database.delete(DatabaseHelper.TABLE_NOTES, DatabaseHelper.COLUMN_ID
                                                    + " = " + id, null);
    }

    public void deleteAll() {
        database.delete(DatabaseHelper.TABLE_NOTES, null, null);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_NOTES,
                notesAllColumn, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setNote(cursor.getString(1));
        note.setTitle(cursor.getString(2));
        return note;
    }

}
