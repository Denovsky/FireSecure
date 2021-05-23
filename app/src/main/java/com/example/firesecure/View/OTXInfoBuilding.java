package com.example.firesecure.View;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OTXInfoBuilding extends AppCompatActivity implements View.OnClickListener {

    private String build_id;
    private ChoseBuildingDatabase myDB;

    private TextView header;
    private FloatingActionButton save_fub;

    private EditText build_info_1;
    private EditText build_info_2;
    private EditText build_info_3;
    private EditText build_info_4;
    private EditText build_info_5;
    private EditText build_info_6;
    private EditText build_info_7;
    private EditText build_info_8;
    private EditText build_info_9;
    private EditText build_info_10;
    private EditText build_info_11;
    private EditText build_info_12;
    private EditText build_info_13;
    private EditText build_info_14;
    private EditText build_info_15;
    private EditText build_info_16;
    private EditText build_info_17;
    private EditText build_info_18;
    private EditText build_info_19;
    private EditText build_info_20;

    private String build_info_1_str;
    private String build_info_2_str;
    private String build_info_3_str;
    private String build_info_4_str;
    private String build_info_5_str;
    private String build_info_6_str;
    private String build_info_7_str;
    private String build_info_8_str;
    private String build_info_9_str;
    private String build_info_10_str;
    private String build_info_11_str;
    private String build_info_12_str;
    private String build_info_13_str;
    private String build_info_14_str;
    private String build_info_15_str;
    private String build_info_16_str;
    private String build_info_17_str;
    private String build_info_18_str;
    private String build_info_19_str;
    private String build_info_20_str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.otx_info_building);

        init();

        Bundle extras = getIntent().getExtras();
        build_id = extras.getString("id");
        getDataOnId(build_id);
    }

    @Override
    public void onClick(View v) {
        confirmDialog();
    }

    void confirmDialog() {
        String someTextForReloading = "someText";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить изменения?");
        builder.setMessage("Вы уверены, что хотите сохранить изменения?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.addPacificInfo(getAllEditText(), build_id, someTextForReloading);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void getDataOnId(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NAME_BUILDING));
            header.setText("ОТХ " + name);
        }
    }

    private void init(){
        header = (TextView) findViewById(R.id.header);
        save_fub = (FloatingActionButton) findViewById(R.id.save_fub);
        save_fub.setOnClickListener(this);
        myDB = new ChoseBuildingDatabase(OTXInfoBuilding.this);

        build_info_1 = (EditText) findViewById(R.id.build_info_1);
        build_info_2 = (EditText) findViewById(R.id.build_info_2);
        build_info_3 = (EditText) findViewById(R.id.build_info_3);
        build_info_4 = (EditText) findViewById(R.id.build_info_4);
        build_info_5 = (EditText) findViewById(R.id.build_info_5);
        build_info_6 = (EditText) findViewById(R.id.build_info_6);
        build_info_7 = (EditText) findViewById(R.id.build_info_7);
        build_info_8 = (EditText) findViewById(R.id.build_info_8);
        build_info_9 = (EditText) findViewById(R.id.build_info_9);
        build_info_10 = (EditText) findViewById(R.id.build_info_10);
        build_info_11 = (EditText) findViewById(R.id.build_info_11);
        build_info_12 = (EditText) findViewById(R.id.build_info_12);
        build_info_13 = (EditText) findViewById(R.id.build_info_13);
        build_info_14 = (EditText) findViewById(R.id.build_info_14);
        build_info_15 = (EditText) findViewById(R.id.build_info_15);
        build_info_16 = (EditText) findViewById(R.id.build_info_16);
        build_info_17 = (EditText) findViewById(R.id.build_info_17);
        build_info_18 = (EditText) findViewById(R.id.build_info_18);
        build_info_19 = (EditText) findViewById(R.id.build_info_19);
        build_info_20 = (EditText) findViewById(R.id.build_info_20);
    }

    private ArrayList<String> getAllEditText() {
        ArrayList<String> array = new ArrayList<>();

        build_info_1_str = build_info_1.getText().toString().trim();
        build_info_2_str = build_info_2.getText().toString().trim();
        build_info_3_str = build_info_3.getText().toString().trim();
        build_info_4_str = build_info_4.getText().toString().trim();
        build_info_5_str = build_info_5.getText().toString().trim();
        build_info_6_str = build_info_6.getText().toString().trim();
        build_info_7_str = build_info_7.getText().toString().trim();
        build_info_8_str = build_info_8.getText().toString().trim();
        build_info_9_str = build_info_9.getText().toString().trim();
        build_info_10_str = build_info_10.getText().toString().trim();
        build_info_11_str = build_info_11.getText().toString().trim();
        build_info_12_str = build_info_12.getText().toString().trim();
        build_info_13_str = build_info_13.getText().toString().trim();
        build_info_14_str = build_info_14.getText().toString().trim();
        build_info_15_str = build_info_15.getText().toString().trim();
        build_info_16_str = build_info_16.getText().toString().trim();
        build_info_17_str = build_info_17.getText().toString().trim();
        build_info_18_str = build_info_18.getText().toString().trim();
        build_info_19_str = build_info_19.getText().toString().trim();
        build_info_20_str = build_info_20.getText().toString().trim();

        array.add(build_info_1_str);
        array.add(build_info_2_str);
        array.add(build_info_3_str);
        array.add(build_info_4_str);
        array.add(build_info_5_str);
        array.add(build_info_6_str);
        array.add(build_info_7_str);
        array.add(build_info_8_str);
        array.add(build_info_9_str);
        array.add(build_info_10_str);
        array.add(build_info_11_str);
        array.add(build_info_12_str);
        array.add(build_info_13_str);
        array.add(build_info_14_str);
        array.add(build_info_15_str);
        array.add(build_info_16_str);
        array.add(build_info_17_str);
        array.add(build_info_18_str);
        array.add(build_info_19_str);
        array.add(build_info_20_str);

        return array;
    }
}
