package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseDataDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "intensity_linear_speed.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "intensity_linear_speed";

        public static final String COLUMN_ID = "id_facility";
        public static final String OBJECT_AND_MATERIALS = "objects_and_materials";
        public static final String INTENSITY_WATER = "intensity_water";
        public static final String LINEAR_SPEED_OF_FIRE = "linear_speed_of_fire";
        public static final String ID_BUILD = "id_build";

    public ChoseDataDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OBJECT_AND_MATERIALS + " TEXT, " +
                INTENSITY_WATER + " TEXT, " +
                LINEAR_SPEED_OF_FIRE + " TEXT, " +
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

    public void addData(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(OBJECT_AND_MATERIALS, array.get(0));
        cv.put(INTENSITY_WATER, array.get(1));
        cv.put(LINEAR_SPEED_OF_FIRE, array.get(2));
        cv.put(ID_BUILD, array.get(3));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteData(String id) {
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
        Cursor cursor = db.query(TABLE_NAME, null,  COLUMN_ID + " = ?", new String[]{id}, null, null, null);
        return cursor;
    }
}
