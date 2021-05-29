package com.example.firesecure.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChoseRoomDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "rooms.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "rooms";

    public static final String COLUMN_ID = "id_room";
    public static final String NUMBER_ROOM = "num_room";
    public static final String NAME_ROOM = "name_room";
    public static final String STATUS_ROOM = "status_room";
    public static final String LENGTH_LEVER = "length_sleeve";
    public static final String STATE_NUM_IN = "state_num_people_in";
    public static final String APPROX_NUM_IN = "approx_num_people_in";
    public static final String AHS = "room_with_ahs";
    public static final String SANITARY = "room_with_sanitary";
    public static final String RV_DM_VV_RTT = "rv_dm_vv_rtt";
    public static final String NUM_ENTRY = "num_entry";
    public static final String SIZE_ROOM = "area_size_room";
    public static final String ID_FLOOR = "id_floor";

    public static final String PLAN_ROOM = "plan_room";

    public ChoseRoomDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NUMBER_ROOM + " TEXT, " +
                NAME_ROOM + " TEXT, " +
                STATUS_ROOM + " TEXT, " +
                LENGTH_LEVER + " TEXT, " +
                STATE_NUM_IN + " TEXT, " +
                APPROX_NUM_IN + " TEXT, " +
                AHS + " TEXT, " +
                SANITARY + " TEXT, " +
                RV_DM_VV_RTT + " TEXT, " +
                NUM_ENTRY + " TEXT, " +
                SIZE_ROOM + " TEXT, " +
                ID_FLOOR + " TEXT, " +
                PLAN_ROOM + " TEXT);";
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

    public void addRoom(ArrayList<String> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NUMBER_ROOM, array.get(0));
        cv.put(NAME_ROOM, array.get(1));
        cv.put(STATUS_ROOM, array.get(2));
        cv.put(LENGTH_LEVER, array.get(3));
        cv.put(STATE_NUM_IN, array.get(4));
        cv.put(APPROX_NUM_IN, array.get(5));
        cv.put(AHS, array.get(6));
        cv.put(SANITARY, array.get(7));
        cv.put(RV_DM_VV_RTT, array.get(8));
        cv.put(NUM_ENTRY, array.get(9));
        cv.put(SIZE_ROOM, array.get(10));
        cv.put(ID_FLOOR, array.get(11));
        cv.put(PLAN_ROOM, array.get(12));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавлено успешно", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRoom(String id) {
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
