package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QuotableDB";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "FavoriteQuotes";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TAGS = "Tags";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_CONTENT = "CONTENT";

    private static final String createFavorite =
            "CREATE TABLE IF NOT EXISTS " + DB_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TAGS + " TEXT," +
                    COLUMN_AUTHOR + " TEXT," +
                    COLUMN_CONTENT + " TEXT)";

    public static final String dropFavorite =
            "DROP TABLE IF EXISTS " + DB_TABLE;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public boolean dbInsert (Favorite fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TAGS, fav.tags);
        cv.put(COLUMN_AUTHOR, fav.author);
        cv.put(COLUMN_CONTENT, fav.content);
        long result = db.insert(DB_TABLE, null, cv);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void dbDelete (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,COLUMN_ID+"="+id, null);
    }

    public Cursor getAllData() {
        String query = "SELECT * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createFavorite);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropFavorite);
    }
}
