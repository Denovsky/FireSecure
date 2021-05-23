package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ChoseDivisionDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fire_division.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "fire_division";
    public static final String COLUMN_ID = "id_divis";
    public static final String NUMBER_DIVISION = "num_divis";
    public static final String DEPO_DIVISION = "depo_divis";
    public static final String TOWN_DIVISION = "town_divis";



    public ChoseDivisionDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NUMBER_DIVISION + " TEXT, " +
                DEPO_DIVISION + " TEXT, " +
                TOWN_DIVISION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void addDivision(String one, String two, String three){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NUMBER_DIVISION, one);
        cv.put(DEPO_DIVISION, two);
        cv.put(TOWN_DIVISION, three);
        long result = db.insert(TABLE_NAME,null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneDivision(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id_divis=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Успешно удалено!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "id_divis = ?", new String[] {id}, null, null, null);
        return cursor;
    }
}
