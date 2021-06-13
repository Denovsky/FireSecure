package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseTechDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "tech.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tech";

        public static final String COLUMN_ID = "id_tech";
        public static final String MARK_TECH = "mark_tech";
        public static final String TYPE_TECH = "type_tech";
        public static final String COMBAT_TECH = "combat_tech";
        public static final String TANK_SIZE_TECH = "tank_size_tech";
        public static final String FOAM_SIZE_TECH = "foam_size_tech";
        public static final String NUM_SLEEVE_51 = "num_sleeve_51";
        public static final String NUM_SLEEVE_66 = "num_sleeve_66";
        public static final String NUM_SLEEVE_77 = "num_sleeve_77";
        public static final String NUM_NODE_TYPE_A = "num_node_type_A";
        public static final String NUM_NODE_TYPE_B = "num_node_type_B";
        public static final String NUM_NODE_UNIVERSAL = "num_node_universal";
        public static final String NUM_NODE_PORTABLE = "num_node_portable";
        public static final String NUM_NODE_STATIC = "num_node_static";
        public static final String ID_DIVISION = "id_division";

    public ChoseTechDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MARK_TECH + " TEXT, " +
                TYPE_TECH + " TEXT, " +
                COMBAT_TECH + " TEXT, " +
                TANK_SIZE_TECH + " TEXT, " +
                FOAM_SIZE_TECH + " TEXT, " +
                NUM_SLEEVE_51 + " TEXT, " +
                NUM_SLEEVE_66 + " TEXT, " +
                NUM_SLEEVE_77 + " TEXT, " +
                NUM_NODE_TYPE_A + " TEXT, " +
                NUM_NODE_TYPE_B + " TEXT, " +
                NUM_NODE_UNIVERSAL + " TEXT, " +
                NUM_NODE_PORTABLE + " TEXT, " +
                NUM_NODE_STATIC + " TEXT, " +
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

    public void addTech(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MARK_TECH, array.get(0));
        cv.put(TYPE_TECH, array.get(1));
        cv.put(COMBAT_TECH, array.get(2));
        cv.put(TANK_SIZE_TECH, array.get(3));
        cv.put(FOAM_SIZE_TECH, array.get(4));
        cv.put(NUM_SLEEVE_51, array.get(5));
        cv.put(NUM_SLEEVE_66, array.get(6));
        cv.put(NUM_SLEEVE_77, array.get(7));
        cv.put(NUM_NODE_TYPE_A, array.get(8));
        cv.put(NUM_NODE_TYPE_B, array.get(9));
        cv.put(NUM_NODE_UNIVERSAL, array.get(10));
        cv.put(NUM_NODE_PORTABLE, array.get(11));
        cv.put(NUM_NODE_STATIC, array.get(12));
        cv.put(ID_DIVISION, array.get(13));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTech(String id) {
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
