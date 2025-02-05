package com.example.zerrendasqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zerrendasqllite.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ZerrendaDAO {

    private final DbHelper dbHelper;

    // Klasearen konstruktorea: Datu-basearen laguntzailea sortu
    public ZerrendaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // CREATE: Lengoaia bat datu-basean gehitu
    public long gehituLengoaia(String izena, String deskribapena, boolean librea) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Idazteko moduan ireki datu-basea
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_IZENA, izena);
        values.put(DbHelper.COLUMN_DESKRIBAPENA, deskribapena);
        if (librea) {
            values.put(DbHelper.COLUMN_LIBREA, 1);
        } else {
            values.put(DbHelper.COLUMN_LIBREA, 0);
        }

        long id = db.insert(DbHelper.TABLE_LENGOAIAK, null, values);
        db.close();
        return id;
    }

    // READ: Lengoaia guztiak datu-basetik irakurri
    public List<Lenguaia> lortuLengoaiak() {
        List<Lenguaia> lengoaienZerrenda = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Irakurtzeko moduan ireki datu-basea

        // Lortu nahi diren zutabeak definitu
        String[] zutabeak = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_IZENA,
                DbHelper.COLUMN_DESKRIBAPENA,
                DbHelper.COLUMN_LIBREA
        };

        // Kontsulta egin taulari
        Cursor cursor = db.query(DbHelper.TABLE_LENGOAIAK, zutabeak,
                null, null, null, null, null);

        // Datu guztiak irakurri eta zerrendan gehitu
        if (cursor.moveToFirst()) {
            do {
                String izena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_IZENA));
                String deskribapena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DESKRIBAPENA));
                Boolean librea = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LIBREA)) > 0;
                Lenguaia lengoaia = new Lenguaia(izena, deskribapena, librea);
                lengoaienZerrenda.add(lengoaia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lengoaienZerrenda;
    }

    // READ: Lengoaia zehatz bat ID bidez lortu
    public Lenguaia lortuLengoaia(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Irakurtzeko moduan ireki datu-basea

        // Lortu nahi diren zutabeak definitu
        String[] zutabeak = {
                DbHelper.COLUMN_IZENA,
                DbHelper.COLUMN_DESKRIBAPENA,
                DbHelper.COLUMN_LIBREA
        };

        // Kontsulta egin ID zehatzari
        Cursor cursor = db.query(DbHelper.TABLE_LENGOAIAK, zutabeak,
                DbHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, // parametroak gehitzeko metodo segurua, sql injection erasoak sahiestu
                null, null, null);

        // Datuak irakurri eta itzuli
        if (cursor != null && cursor.moveToFirst()) {
            String izena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_IZENA));
            String deskribapena = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DESKRIBAPENA));
            Boolean librea = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LIBREA)) > 0;
            cursor.close(); // Cursor itxi
            db.close(); // Datu-basea itxi
            return new Lenguaia(izena, deskribapena, librea); // Lengoaia itzuli
        } else {
            return null; // Daturik ez badago, null itzuli
        }
    }

    // UPDATE: Lengoaia bat eguneratu ID bidez
    public int eguneratuLengoaia(int id, String izena, String deskribapena, boolean librea) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Idazteko moduan ireki datu-basea
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_IZENA, izena); // Izena eguneratu
        values.put(DbHelper.COLUMN_DESKRIBAPENA, deskribapena); // Deskribapena eguneratu
        if (librea) {
            values.put(DbHelper.COLUMN_LIBREA, 1); // Librea eguneratu
        } else {
            values.put(DbHelper.COLUMN_LIBREA, 0); // Librea eguneratu
        }

        // Eguneraketa egin ID zehatzari
        int eguneratutakoLerroKop = db.update(DbHelper.TABLE_LENGOAIAK, values,
                DbHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return eguneratutakoLerroKop;
    }

    // DELETE: Lengoaia bat ezabatu ID bidez
    public int ezabatuLengoaia(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Ezabaketa egin ID zehatzari
        int ezabatuak = db.delete(DbHelper.TABLE_LENGOAIAK,
                DbHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return ezabatuak;
    }
}