package com.example.firesecure.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseBuildingDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fire_building.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "fire_building";
    public static final String COLUMN_ID = "id_building";
    public static final String NAME_BUILDING = "name_building";
    public static final String DEPO_BUILDING = "depo_building";
    public static final String ADDRESS_BUILDING = "address_building";
    public static final String ID_DIVIS = "id_divis";

    public static final String PACIFIC_INFO_1 = "pacific_build_info_1";
    public static final String PACIFIC_INFO_2 = "pacific_build_info_2";
    public static final String PACIFIC_INFO_3 = "pacific_build_info_3";
    public static final String PACIFIC_INFO_4 = "pacific_build_info_4";
    public static final String PACIFIC_INFO_5 = "pacific_build_info_5";
    public static final String PACIFIC_INFO_6 = "pacific_build_info_6";
    public static final String PACIFIC_INFO_7 = "pacific_build_info_7";
    public static final String PACIFIC_INFO_8 = "pacific_build_info_8";
    public static final String PACIFIC_INFO_9 = "pacific_build_info_9";

    public static final String WAY = "way";
    public static final String MAIN_PLAN = "main_plan";
    public static final String PACIFIC_INFO_10 = "pacific_build_info_10";
    public static final String PACIFIC_INFO_11 = "pacific_build_info_11";
    public static final String PACIFIC_INFO_12 = "pacific_build_info_12";
    public static final String PACIFIC_INFO_13 = "pacific_build_info_13";
    public static final String PACIFIC_INFO_14 = "pacific_build_info_14";
    public static final String PACIFIC_INFO_15 = "pacific_build_info_15";
    public static final String PACIFIC_INFO_16 = "pacific_build_info_16";
    public static final String PACIFIC_INFO_17 = "pacific_build_info_17";
    public static final String PACIFIC_INFO_18 = "pacific_build_info_18";
    public static final String PACIFIC_INFO_19 = "pacific_build_info_19";
    public static final String PACIFIC_INFO_20 = "pacific_build_info_20";
    public static final String PACIFIC_INFO_21 = "pacific_build_info_21";
    public static final String PACIFIC_INFO_22 = "pacific_build_info_22";
    public static final String PACIFIC_INFO_23 = "pacific_build_info_23";
    public static final String PACIFIC_INFO_24 = "pacific_build_info_24";
    public static final String PACIFIC_INFO_25 = "pacific_build_info_25";
    public static final String PACIFIC_INFO_26 = "pacific_build_info_26";
    public static final String PACIFIC_INFO_27 = "pacific_build_info_27";
    public static final String PACIFIC_INFO_28 = "pacific_build_info_28";
    public static final String PACIFIC_INFO_29 = "pacific_build_info_29";
    public static final String PACIFIC_INFO_30 = "pacific_build_info_30";
    public static final String ID_FACILITY = "id_facility";
    public static final String ID_FIRE_RES = "id_fire_res";

    public ChoseBuildingDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_BUILDING + " TEXT, " +
                DEPO_BUILDING + " TEXT, " +
                ADDRESS_BUILDING + " TEXT, " +
                ID_DIVIS + " TEXT, " +

                PACIFIC_INFO_1 + " TEXT, " +
                PACIFIC_INFO_2 + " TEXT, " +
                PACIFIC_INFO_3 + " TEXT, " +
                PACIFIC_INFO_4 + " TEXT, " +
                PACIFIC_INFO_5 + " TEXT, " +
                PACIFIC_INFO_6 + " TEXT, " +
                PACIFIC_INFO_7 + " TEXT, " +
                PACIFIC_INFO_8 + " TEXT, " +
                PACIFIC_INFO_9 + " TEXT, " +

                WAY + " TEXT, " +
                MAIN_PLAN + " TEXT, " +
                PACIFIC_INFO_10 + " TEXT, " +
                PACIFIC_INFO_11 + " TEXT, " +
                PACIFIC_INFO_12 + " TEXT, " +
                PACIFIC_INFO_13 + " TEXT, " +
                PACIFIC_INFO_14 + " TEXT, " +
                PACIFIC_INFO_15 + " TEXT, " +
                PACIFIC_INFO_16 + " TEXT, " +
                PACIFIC_INFO_17 + " TEXT, " +
                PACIFIC_INFO_18 + " TEXT, " +
                PACIFIC_INFO_19 + " TEXT, " +
                PACIFIC_INFO_20 + " TEXT, " +
                PACIFIC_INFO_21 + " TEXT, " +
                PACIFIC_INFO_22 + " TEXT, " +
                PACIFIC_INFO_23 + " TEXT, " +
                PACIFIC_INFO_24 + " TEXT, " +
                PACIFIC_INFO_25 + " TEXT, " +
                PACIFIC_INFO_26 + " TEXT, " +
                PACIFIC_INFO_27 + " TEXT, " +
                PACIFIC_INFO_28 + " TEXT, " +
                PACIFIC_INFO_29 + " TEXT, " +
                PACIFIC_INFO_30 + " TEXT, " +
                ID_FACILITY + " TEXT, " +
                ID_FIRE_RES + " TEXT);";
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

    public void addMainBuildingInfo(String one, String two, String three, String four) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_BUILDING, one);
        cv.put(DEPO_BUILDING, two);
        cv.put(ADDRESS_BUILDING, three);
        cv.put(ID_DIVIS, four);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPacificInfo(ArrayList<String> array, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 1; i <= 9; i++) {
            String column = String.format("pacific_build_info_%s", i);
            String query = "update " + TABLE_NAME + " set " + column + " = ? where " + COLUMN_ID + " like " + id;
            String[] bindArgs = {array.get(i - 1)};
            try {
                db.execSQL(query, bindArgs);
            } catch (Exception ex) {
                Log.d("tag", String.valueOf(ex));
            }
        }
    }

    public void addPacificInfo(ArrayList<String> array, String id, String sec) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (sec.equals("img")){
            String query = "update " + TABLE_NAME + " set " + MAIN_PLAN + " = ? where " + COLUMN_ID + " like " + id;
            String[] bindArgs = {array.get(0)};
            try {
                db.execSQL(query, bindArgs);
            } catch (Exception e) {
                Log.d("tag", String.valueOf(e));
            }
        } else if (sec.equals("img1")) {
            String query = "update " + TABLE_NAME + " set " + WAY + " = ? where " + COLUMN_ID + " like " + id;
            String[] bindArgs = {array.get(0)};
            try {
                db.execSQL(query, bindArgs);
            } catch (Exception e) {
                Log.d("tag", String.valueOf(e));
            }
        } else {
            for (int i = 10; i < 30; i++) {
                String column = String.format("pacific_build_info_%s", i);
                String query = "update " + TABLE_NAME + " set " + column + " = ? where " + COLUMN_ID + " like " + id;
                String[] bindArgs = {array.get(i - 10)};
                try {
                    db.execSQL(query, bindArgs);
                } catch (Exception e) {
                    Log.d("tag", String.valueOf(e));
                }
            }
        }
        array.clear();
    }

    public void deleteOneBuilding(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id_building=?", new String[]{id});
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
        Cursor cursor = db.query(TABLE_NAME, null, "id_building = ?", new String[]{id}, null, null, null);
        return cursor;
    }
}
