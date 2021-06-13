package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseCODatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fireresCO.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "fireresCO";

    public static final String COLUMN_ID = "id_CO_column";
    public static final String BUILDING_R_1 = "building_R_1";
    public static final String BUILDING_E_1 = "building_E_1";
    public static final String BUILDING_REI_1 = "building_REI_1";
    public static final String BUILDING_RE_1 = "building_RE_1";
    public static final String BUILDING_R_2 = "building_R_2";
    public static final String BUILDING_REI_2 = "building_REI_2";
    public static final String BUILDING_R_3 = "building_R_3";
    public static final String ID_CO = "id_CO";
    public static final String ID_BUILD = "id_build";

    public ChoseCODatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BUILDING_R_1 + " TEXT, " +
                BUILDING_E_1 + " TEXT, " +
                BUILDING_REI_1 + " TEXT, " +
                BUILDING_RE_1 + " TEXT, " +
                BUILDING_R_2 + " TEXT, " +
                BUILDING_REI_2 + " TEXT, " +
                BUILDING_R_3 + " TEXT, " +
                ID_CO + " TEXT, " +
                ID_BUILD + " TEXT);";
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

    public void addCO(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BUILDING_R_1, array.get(0));
        cv.put(BUILDING_E_1, array.get(1));
        cv.put(BUILDING_REI_1, array.get(2));
        cv.put(BUILDING_RE_1, array.get(3));
        cv.put(BUILDING_R_2, array.get(4));
        cv.put(BUILDING_REI_2, array.get(5));
        cv.put(BUILDING_R_3, array.get(6));
        cv.put(ID_CO, array.get(7));
        cv.put(ID_BUILD, array.get(8));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCO(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Успешно удалено", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME, null, ID_BUILD + " = ?", new String[]{id}, null, null, null);
    }
}
