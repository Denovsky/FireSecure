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
            try {
                String test_obj = cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_10));
                Log.d("TAGER", test_obj);
                save_fub.setVisibility(View.GONE);
                fillEditTexts(cursor);
            } catch (Exception e) {

            }
        }
    }

    public void fillEditTexts(Cursor cursor) {
        if (cursor.moveToFirst()) {
            build_info_1.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_10)));
            build_info_2.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_11)));
            build_info_3.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_12)));
            build_info_4.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_13)));
            build_info_5.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_14)));
            build_info_6.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_15)));
            build_info_7.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_16)));
            build_info_8.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_17)));
            build_info_9.setText (cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_18)));
            build_info_10.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_19)));
            build_info_11.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_20)));
            build_info_12.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_21)));
            build_info_13.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_22)));
            build_info_14.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_23)));
            build_info_15.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_24)));
            build_info_16.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_25)));
            build_info_17.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_26)));
            build_info_18.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_27)));
            build_info_19.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_28)));
            build_info_20.setText(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PACIFIC_INFO_29)));
        }
    }

    private void init() {
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

        array.add(build_info_1.getText().toString().trim());
        array.add(build_info_2.getText().toString().trim());
        array.add(build_info_3.getText().toString().trim());
        array.add(build_info_4.getText().toString().trim());
        array.add(build_info_5.getText().toString().trim());
        array.add(build_info_6.getText().toString().trim());
        array.add(build_info_7.getText().toString().trim());
        array.add(build_info_8.getText().toString().trim());
        array.add(build_info_9.getText().toString().trim());
        array.add(build_info_10.getText().toString().trim());
        array.add(build_info_11.getText().toString().trim());
        array.add(build_info_12.getText().toString().trim());
        array.add(build_info_13.getText().toString().trim());
        array.add(build_info_14.getText().toString().trim());
        array.add(build_info_15.getText().toString().trim());
        array.add(build_info_16.getText().toString().trim());
        array.add(build_info_17.getText().toString().trim());
        array.add(build_info_18.getText().toString().trim());
        array.add(build_info_19.getText().toString().trim());
        array.add(build_info_20.getText().toString().trim());

        return array;
    }
}
