package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseEmployeeDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "employee.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "employee";

    public static final String COLUMN_ID = "id_employee";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String EMPLOYEE_RANK = "employee_rank";
    public static final String START_WORK_DATE = "start_work_date";
    public static final String POST = "post";
    public static final String SENTRY_ONE = "sentry_one";
    public static final String SENTRY_TWO = "sentry_two";
    public static final String QUALIFICATION = "qualification";
    public static final String ID_DIVISION = "id_division";

    public ChoseEmployeeDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMPLOYEE_NAME + " TEXT, " +
                EMPLOYEE_RANK + " TEXT, " +
                START_WORK_DATE + " TEXT, " +
                POST + " TEXT, " +
                SENTRY_ONE + " TEXT, " +
                SENTRY_TWO + " TEXT, " +
                QUALIFICATION + " TEXT, " +
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

    public void addEmployee(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EMPLOYEE_NAME, array.get(0));
        cv.put(EMPLOYEE_RANK, array.get(1));
        cv.put(START_WORK_DATE, array.get(2));
        cv.put(POST, array.get(3));
        cv.put(SENTRY_ONE, array.get(4));
        cv.put(SENTRY_TWO, array.get(5));
        cv.put(QUALIFICATION, array.get(6));
        cv.put(ID_DIVISION, array.get(7));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteEmployee(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
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

