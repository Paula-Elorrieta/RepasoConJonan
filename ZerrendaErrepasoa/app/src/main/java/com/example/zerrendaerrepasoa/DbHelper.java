package com.example.zerrendaerrepasoa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "LekuakDB1.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_LEKUAK = "lekuak";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IZENA = "izena";
    public static final String COLUMN_DESKRIBAPENA = "deskribapena";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    // REAL es un tipo de dato que se usa para almacenar números decimales
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_LEKUAK + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IZENA + " TEXT NOT NULL, " +
                    COLUMN_DESKRIBAPENA + " TEXT NOT NULL, " +
                    COLUMN_LATITUDE + " REAL NOT NULL, " +
                    COLUMN_LONGITUDE + " REAL NOT NULL" +
                    ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEKUAK);
        onCreate(db);
    }
}

