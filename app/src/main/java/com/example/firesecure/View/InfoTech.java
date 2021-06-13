package com.example.firesecure.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.Model.ChoseRoomDatabase;
import com.example.firesecure.Model.ChoseTechDatabase;
import com.example.firesecure.R;

public class InfoTech extends AppCompatActivity {

    private ChoseTechDatabase myDB;
    private String id_tech;

    private TextView header,
            mark,
            type,
            combat,
            tank_size,
            foam_size,
            num_sleeve_51,
            num_sleeve_66,
            num_sleeve_77,
            num_node_type_A,
            num_node_type_B,
            num_node_universal,
            num_node_portable,
            num_node_static;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_tech);

        init();
        fillTextViews(id_tech);
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_tech = extras.getString("id_tech");
        myDB = new ChoseTechDatabase(this);

        header = (TextView) findViewById(R.id.header);
        mark = (TextView) findViewById(R.id.mark);
        type = (TextView) findViewById(R.id.type);
        combat = (TextView) findViewById(R.id.combat);
        tank_size = (TextView) findViewById(R.id.tank_size);
        foam_size = (TextView) findViewById(R.id.foam_size);
        num_sleeve_51 = (TextView) findViewById(R.id.num_sleeve_51);
        num_sleeve_66 = (TextView) findViewById(R.id.num_sleeve_66);
        num_sleeve_77 = (TextView) findViewById(R.id.num_sleeve_77);
        num_node_type_A = (TextView) findViewById(R.id.num_node_type_A);
        num_node_type_B = (TextView) findViewById(R.id.num_node_type_B);
        num_node_universal = (TextView) findViewById(R.id.num_node_universal);
        num_node_portable = (TextView) findViewById(R.id.num_node_portable);
        num_node_static = (TextView) findViewById(R.id.num_node_static);
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String mark_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.MARK_TECH));
            String type_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.TYPE_TECH));
            String combat_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.COMBAT_TECH));
            String tank_size_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.TANK_SIZE_TECH));
            String foam_size_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.FOAM_SIZE_TECH));
            String num_sleeve_51_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_SLEEVE_51));
            String num_sleeve_66_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_SLEEVE_66));
            String num_sleeve_77_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_SLEEVE_77));
            String num_node_type_A_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_NODE_TYPE_A));
            String num_node_type_B_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_NODE_TYPE_B));
            String num_node_universal_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_NODE_UNIVERSAL));
            String num_node_portable_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_NODE_PORTABLE));
            String num_node_static_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUM_NODE_STATIC));

            header.setText(type_str);
            mark.setText(mark_str);
            type.setText(type_str);
            combat.setText(combat_str);
            tank_size.setText(tank_size_str);
            foam_size.setText(foam_size_str);
            num_sleeve_51.setText(num_sleeve_51_str);
            num_sleeve_66.setText(num_sleeve_66_str);
            num_sleeve_77.setText(num_sleeve_77_str);
            num_node_type_A.setText(num_node_type_A_str);
            num_node_type_B.setText(num_node_type_B_str);
            num_node_universal.setText(num_node_universal_str);
            num_node_portable.setText(num_node_portable_str);
            num_node_static.setText(num_node_static_str);
        }
    }
}
