package com.example.zerrendaerrepasoa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LekuaDAO {

    private final DbHelper dbHelper;

    public LekuaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public long gehituLekua(Lekua lekua) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_IZENA, lekua.getIzena());
        values.put(DbHelper.COLUMN_DESKRIBAPENA, lekua.getDeskribapena());
        values.put(DbHelper.COLUMN_LATITUDE, lekua.getLatitude());
        values.put(DbHelper.COLUMN_LONGITUDE, lekua.getLongitude());
        long id = db.insert(DbHelper.TABLE_LEKUAK, null, values);
        db.close();

        return id;
    }

    public int ezabatuLekua(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int is_ezabatuta = db.delete(DbHelper.TABLE_LEKUAK,
                DbHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return is_ezabatuta;
    }

    public int eguneratuLekua(Lekua lekua) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_IZENA, lekua.getIzena());
        values.put(DbHelper.COLUMN_DESKRIBAPENA, lekua.getDeskribapena());
        values.put(DbHelper.COLUMN_LATITUDE, lekua.getLatitude());
        values.put(DbHelper.COLUMN_LONGITUDE, lekua.getLongitude());
        int is_eguneratuta = db.update(DbHelper.TABLE_LEKUAK, values,
                DbHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(lekua.getId())});
        db.close();

        return is_eguneratuta;
    }

    // Modo lectura si solo hay una  tabla
    public ArrayList<Lekua> getLekuakConSoloUnaTabla() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Lekua> lekuak = new ArrayList<>();
        return lekuak;
    }

    public ArrayList<Lekua> getLekuak() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Especificar de que tabla
        String[] columns = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_IZENA,
                DbHelper.COLUMN_DESKRIBAPENA,
                DbHelper.COLUMN_LATITUDE,
                DbHelper.COLUMN_LONGITUDE
        };

        Cursor cursor = db.query(DbHelper.TABLE_LEKUAK,
                columns,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Lekua> lekuak = new ArrayList<>();
        // el getColumnIndexOrThrow es para asegurarse de que la columna existe
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
            String izena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_IZENA));
            String deskribapena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DESKRIBAPENA));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LONGITUDE));

            Lekua lekua = new Lekua(id, izena, deskribapena, latitude, longitude);
            lekuak.add(lekua);
        }
        cursor.close();
        db.close();

        return lekuak;
    }

    public Lekua getLekua(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_IZENA,
                DbHelper.COLUMN_DESKRIBAPENA,
                DbHelper.COLUMN_LATITUDE,
                DbHelper.COLUMN_LONGITUDE
        };

        Cursor cursor = db.query(DbHelper.TABLE_LEKUAK, columns,
                DbHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        Lekua lekua = null;
        // La diferencia con moveToFirst() es que este no devuelve un booleano
        if (cursor.moveToNext()) {
            long lekuaId = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
            String izena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_IZENA));
            String deskribapena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DESKRIBAPENA));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LONGITUDE));

            lekua = new Lekua(lekuaId, izena, deskribapena, latitude, longitude);
        }
        cursor.close();
        db.close();

        return lekua;
    }

}
