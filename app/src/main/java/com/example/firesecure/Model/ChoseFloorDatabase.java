package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseFloorDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "floor_building.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "floor_building";

    public static final String COLUMN_ID = "id_floor";
    public static final String NUMBER_FLOOR = "num_floor";
    public static final String STATUS_FLOOR = "status_floor";
    public static final String ENTRY_NUM = "entry_num";
    public static final String LENGTH_LEVER = "length_lever";
    public static final String SIZE_FLOOR = "area_size_floor";
    public static final String ID_BUILDING = "id_building";

    public static final String PLAN_FLOOR = "plan_floor";


    public ChoseFloorDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NUMBER_FLOOR + " TEXT, " +
                STATUS_FLOOR + " TEXT, " +
                ENTRY_NUM + " TEXT, " +
                LENGTH_LEVER + " TEXT, " +
                SIZE_FLOOR + " TEXT, " +
                ID_BUILDING + " TEXT, " +
                PLAN_FLOOR + " TEXT);";
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

    public void addFloor(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NUMBER_FLOOR, array.get(0));
        cv.put(STATUS_FLOOR, array.get(1));
        cv.put(ENTRY_NUM, array.get(2));
        cv.put(LENGTH_LEVER, array.get(3));
        cv.put(SIZE_FLOOR, array.get(4));

        cv.put(ID_BUILDING, array.get(5));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDivision(String id) {
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
