package com.example.firesecure.View;

import android.app.Activity;
import android.content.Context;
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
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoFloor extends AppCompatActivity implements View.OnClickListener {

    private ChoseFloorDatabase myDB;
    private String id_floor, id_build, id_divis;

    private TextView header;
    private TextView num_floor, status_floor, entry_num, length_lever, area_size_floor;
    private Button plan_btn, places_btn, calculate_but;
    private FloatingActionButton del_fub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_floor);

        init();
        fillTextViews(id_floor);
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_floor = extras.getString("id_floor");
        id_build = extras.getString("id_build");
        id_divis = extras.getString("id_divis");

        myDB = new ChoseFloorDatabase(this);

        header = (TextView) findViewById(R.id.header);

        num_floor = (TextView) findViewById(R.id.num_floor);
        status_floor = (TextView) findViewById(R.id.status_floor);
        entry_num = (TextView) findViewById(R.id.entry_num);
        length_lever = (TextView) findViewById(R.id.length_lever);
        area_size_floor = (TextView) findViewById(R.id.area_size_floor);

        plan_btn = (Button) findViewById(R.id.plan_btn);
        plan_btn.setOnClickListener(this);

        places_btn = (Button) findViewById(R.id.places_btn);
        places_btn.setOnClickListener(this);

        calculate_but = (Button) findViewById(R.id.calculate_but);
        calculate_but.setOnClickListener(this);

        del_fub = (FloatingActionButton) findViewById(R.id.del_fub);
        del_fub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.plan_btn:
                intent = new Intent(this, DocumentPlanFloorInfo.class);
                intent.putExtra("id_floor", id_floor);
                startActivity(intent);
                break;
            case R.id.places_btn:
                intent = new Intent(this, ChoseRoom.class);
                intent.putExtra("id_floor", id_floor);
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.calculate_but:
                intent = new Intent(this, ChoseCalc.class);
                intent.putExtra("id_build", id_build);
                intent.putExtra("id_divis", id_divis);
                startActivity(intent);
                break;
            case R.id.del_fub:
                break;
        }
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_FLOOR));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(myDB.STATUS_FLOOR));
            String entry = cursor.getString(cursor.getColumnIndexOrThrow(myDB.ENTRY_NUM));
            String length = cursor.getString(cursor.getColumnIndexOrThrow(myDB.LENGTH_LEVER));
            String area = cursor.getString(cursor.getColumnIndexOrThrow(myDB.SIZE_FLOOR));

            String id_build = cursor.getString(cursor.getColumnIndexOrThrow(myDB.ID_BUILDING));

            String building;

            ChoseBuildingDatabase choseBuildingDatabase = new ChoseBuildingDatabase(this);
            Cursor cursorBuild = choseBuildingDatabase.getData(id_build);
            if (cursorBuild.moveToFirst()) {
                building = cursorBuild.getString(cursorBuild.getColumnIndexOrThrow(choseBuildingDatabase.NAME_BUILDING));
                header.setText("Этаж №" + num + ", Сооружение " + building);
            }
            num_floor.setText(num);
            status_floor.setText(status);
            entry_num.setText(entry);
            length_lever.setText(length);
            area_size_floor.setText(area);
        }
    }
}
