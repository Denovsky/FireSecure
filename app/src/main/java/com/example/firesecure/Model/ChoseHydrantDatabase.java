package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseHydrantDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fire_hydrant.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "fire_hydrant";

    public static final String COLUMN_ID = "id_hydrant";
    public static final String NUMBER_HYDRANT = "num_hydrant";
    public static final String ADDRESS_HYDRANT = "address_hydrant";
    public static final String ID_PUSH_HYDRANT = "id_push_hydrant";
    public static final String TYPE_NETWORK = "type_network";
    public static final String DIAMETER_NETWORK = "diameter_network";
    public static final String PRESS_NETWORK = "press_network";
    public static final String PUSH_NETWORK = "push_network";
    public static final String ID_DIVISION = "id_division";


    public ChoseHydrantDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NUMBER_HYDRANT + " TEXT, " +
                ADDRESS_HYDRANT + " TEXT, " +
                ID_PUSH_HYDRANT + " TEXT, " +
                TYPE_NETWORK + " TEXT, " +
                DIAMETER_NETWORK + " TEXT, " +
                PRESS_NETWORK + " TEXT, " +
                PUSH_NETWORK + " TEXT, " +
                ID_DIVISION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void addHydrant(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NUMBER_HYDRANT, array.get(0));
        cv.put(ADDRESS_HYDRANT, array.get(1));
        cv.put(ID_PUSH_HYDRANT, array.get(2));
        cv.put(TYPE_NETWORK, array.get(3));
        cv.put(DIAMETER_NETWORK, array.get(4));
        cv.put(PRESS_NETWORK, array.get(5));
        cv.put(PUSH_NETWORK, array.get(6));
        cv.put(ID_DIVISION, array.get(7));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteHydrant(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Успешно удалено!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{id}, null, null, null);
        return cursor;
    }
}

