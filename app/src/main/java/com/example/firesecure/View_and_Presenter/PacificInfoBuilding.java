package com.example.firesecure.View_and_Presenter;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PacificInfoBuilding extends AppCompatActivity implements View.OnClickListener {

    private String build_id;

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

    private String build_info_1_str;
    private String build_info_2_str;
    private String build_info_3_str;
    private String build_info_4_str;
    private String build_info_5_str;
    private String build_info_6_str;
    private String build_info_7_str;
    private String build_info_8_str;
    private String build_info_9_str;

    private ChoseBuildingDatabase myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pacific_info_building);

        init();

        Bundle extras = getIntent().getExtras();
        build_id = extras.getString("id");
        getDataOnId(build_id);
    }

    private void init() {
        header = (TextView) findViewById(R.id.header);
        save_fub = (FloatingActionButton) findViewById(R.id.save_fub);
        save_fub.setOnClickListener(this);
        myDB = new ChoseBuildingDatabase(PacificInfoBuilding.this);

        build_info_1 = (EditText) findViewById(R.id.build_info_1);
        build_info_2 = (EditText) findViewById(R.id.build_info_2);
        build_info_3 = (EditText) findViewById(R.id.build_info_3);
        build_info_4 = (EditText) findViewById(R.id.build_info_4);
        build_info_5 = (EditText) findViewById(R.id.build_info_5);
        build_info_6 = (EditText) findViewById(R.id.build_info_6);
        build_info_7 = (EditText) findViewById(R.id.build_info_7);
        build_info_8 = (EditText) findViewById(R.id.build_info_8);
        build_info_9 = (EditText) findViewById(R.id.build_info_9);
    }

    @Override
    public void onClick(View v) {
        confirmDialog();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить изменения?");
        builder.setMessage("Вы уверены, что хотите сохранить изменения?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.addPacificInfo(getAllEditText(), build_id);
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
            header.setText("ОБЩИЕ СВЕДЕНИЯ ОБ " + name);
            try {
                String test_obj = cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_1));
                Log.d("TAGER", test_obj);
                save_fub.setVisibility(View.GONE);
                fillEditTexts(cursor);
            } catch (Exception e) {
            }
        }
    }

    public void fillEditTexts(Cursor cursor) {
        if (cursor.moveToFirst()) {
            build_info_1.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_1)));
            build_info_2.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_2)));
            build_info_3.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_3)));
            build_info_4.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_4)));
            build_info_5.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_5)));
            build_info_6.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_6)));
            build_info_7.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_7)));
            build_info_8.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_8)));
            build_info_9.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_9)));
        }
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

        array.add(build_info_1_str);
        array.add(build_info_2_str);
        array.add(build_info_3_str);
        array.add(build_info_4_str);
        array.add(build_info_5_str);
        array.add(build_info_6_str);
        array.add(build_info_7_str);
        array.add(build_info_8_str);
        array.add(build_info_9_str);

        return array;
    }
}
