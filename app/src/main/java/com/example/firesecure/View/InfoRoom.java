package com.example.firesecure.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.Model.ChoseRoomDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoRoom extends AppCompatActivity implements View.OnClickListener {

    private ChoseRoomDatabase myDB;
    private String id_room;
    private String id_floor;

    private TextView header;
    private TextView num_room,
            name_room,
            status_room,
            length_sleeve,
            state_num_people_in,
            approx_num_people_in,
            room_with_ahs,
            room_with_sanitary,
            rv_dm_vv_rtt,
            num_entry,
            area_size_room;
    private Button plan_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_room);

        init();
        fillTextViews(id_room);
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_room = extras.getString("id_room");
        id_floor = extras.getString("id_floor");
        myDB = new ChoseRoomDatabase(this);

        header = (TextView) findViewById(R.id.header);

        num_room = (TextView) findViewById(R.id.num_room);
        name_room = (TextView) findViewById(R.id.name_room);
        status_room = (TextView) findViewById(R.id.status_room);
        length_sleeve = (TextView) findViewById(R.id.length_sleeve);
        state_num_people_in = (TextView) findViewById(R.id.state_num_people_in);
        approx_num_people_in = (TextView) findViewById(R.id.approx_num_people_in);
        room_with_ahs = (TextView) findViewById(R.id.room_with_ahs);
        room_with_sanitary = (TextView) findViewById(R.id.room_with_sanitary);
        rv_dm_vv_rtt = (TextView) findViewById(R.id.rv_dm_vv_rtt);
        num_entry = (TextView) findViewById(R.id.num_entry);
        area_size_room = (TextView) findViewById(R.id.area_size_room);

        plan_btn = (Button) findViewById(R.id.plan_btn);
        plan_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DocumentPlanRoomInfo.class);
        intent.putExtra("id_room", id_room);
        startActivity(intent);
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {

            String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_ROOM));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NAME_ROOM));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(myDB.STATUS_ROOM));
            String length = cursor.getString(cursor.getColumnIndexOrThrow(myDB.LENGTH_LEVER));
            String state_num_people = cursor.getString(cursor.getColumnIndexOrThrow(myDB.STATE_NUM_IN));
            String approx_num_people = cursor.getString(cursor.getColumnIndexOrThrow(myDB.APPROX_NUM_IN));
            String ahs = cursor.getString(cursor.getColumnIndexOrThrow(myDB.AHS));
            String sanitary = cursor.getString(cursor.getColumnIndexOrThrow(myDB.SANITARY));
            String rv = cursor.getString(cursor.getColumnIndexOrThrow(myDB.RV_DM_VV_RTT));
            String entry = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_ENTRY));
            String size = cursor.getString(cursor.getColumnIndexOrThrow(myDB.SIZE_ROOM));

            String num_floor;

            ChoseFloorDatabase choseFloorDatabase = new ChoseFloorDatabase(this);
            Cursor cursorBuild = choseFloorDatabase.getData(id_floor);
            if (cursorBuild.moveToFirst()) {
                num_floor = cursorBuild.getString(cursorBuild.getColumnIndexOrThrow(choseFloorDatabase.NUMBER_FLOOR));
                header.setText("Этаж №" + num + ", Этаж №" + num_floor);
            }
            num_room.setText(num);
            name_room.setText(name);
            status_room.setText(status);
            length_sleeve.setText(length);
            state_num_people_in.setText(state_num_people);
            approx_num_people_in.setText(approx_num_people);
            room_with_ahs.setText(ahs);
            room_with_sanitary.setText(sanitary);
            rv_dm_vv_rtt.setText(rv);
            num_entry.setText(entry);
            area_size_room.setText(size);

        }
    }
}
