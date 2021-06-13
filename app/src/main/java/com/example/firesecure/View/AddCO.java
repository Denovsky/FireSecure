package com.example.firesecure.View;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseCODatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddCO extends AppCompatActivity implements View.OnClickListener {

    private ChoseCODatabase myDB;
    private String id_CO, id_build;

    private String id_CO_column_final;
    private String building_R_1_final;
    private String building_E_1_final;
    private String building_REI_1_final;
    private String building_RE_1_final;
    private String building_R_2_final;
    private String building_REI_2_final;
    private String building_R_3_final;
    private String id_CO_str_final;
    private String id_build_final;

    private TextView header;
    private EditText building_R_1,
            building_E_1,
            building_REI_1,
            building_RE_1,
            building_R_2,
            building_REI_2,
            building_R_3;
    private TextView building_R_1_text,
            building_E_1_text,
            building_REI_1_text,
            building_RE_1_text,
            building_R_2_text,
            building_REI_2_text,
            building_R_3_text;
    private FloatingActionButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_co);

        init();
    }

    private void init() {
        myDB = new ChoseCODatabase(AddCO.this);

        header = (TextView) findViewById(R.id.header);

        building_R_1_text = (TextView) findViewById(R.id.building_R_1_text);
        building_E_1_text = (TextView) findViewById(R.id.building_E_1_text);
        building_REI_1_text = (TextView) findViewById(R.id.building_REI_1_text);
        building_RE_1_text = (TextView) findViewById(R.id.building_RE_1_text);
        building_R_2_text = (TextView) findViewById(R.id.building_R_2_text);
        building_REI_2_text = (TextView) findViewById(R.id.building_REI_2_text);
        building_R_3_text = (TextView) findViewById(R.id.building_R_3_text);

        building_R_1 = (EditText) findViewById(R.id.building_R_1);
        building_E_1 = (EditText) findViewById(R.id.building_E_1);
        building_REI_1 = (EditText) findViewById(R.id.building_REI_1);
        building_RE_1 = (EditText) findViewById(R.id.building_RE_1);
        building_R_2 = (EditText) findViewById(R.id.building_R_2);
        building_REI_2 = (EditText) findViewById(R.id.building_REI_2);
        building_R_3 = (EditText) findViewById(R.id.building_R_3);

        save_btn = (FloatingActionButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_CO = extras.getString("id_CO");
        id_build = extras.getString("id_build");

        header.setText("C.O. " + id_CO);

        if (haveInfo()) {
            save_btn.setVisibility(View.GONE);
            fillInfo();
        }
    }

    private boolean haveInfo() {
        Cursor cursor = myDB.getData(id_build);
        if (cursor.getCount() != 0) {
            ArrayList<String> id_CO_column = new ArrayList<>();
            ArrayList<String> building_R_1 = new ArrayList<>();
            ArrayList<String> building_E_1 = new ArrayList<>();
            ArrayList<String> building_REI_1 = new ArrayList<>();
            ArrayList<String> building_RE_1 = new ArrayList<>();
            ArrayList<String> building_R_2 = new ArrayList<>();
            ArrayList<String> building_REI_2 = new ArrayList<>();
            ArrayList<String> building_R_3 = new ArrayList<>();
            ArrayList<String> id_CO_array = new ArrayList<>();
            ArrayList<String> id_build = new ArrayList<>();

            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_CO_column.add(cursor.getString(0));
                building_R_1.add(cursor.getString(1));
                building_E_1.add(cursor.getString(2));
                building_REI_1.add(cursor.getString(3));
                building_RE_1.add(cursor.getString(4));
                building_R_2.add(cursor.getString(5));
                building_REI_2.add(cursor.getString(6));
                building_R_3.add(cursor.getString(7));
                id_CO_array.add(cursor.getString(8));
                id_build.add(cursor.getString(9));
            }
            if (id_CO_array.contains(id_CO)) {
                int index = id_CO_array.indexOf(id_CO);
                building_R_1_final = building_R_1.get(index);
                building_E_1_final = building_E_1.get(index);
                building_REI_1_final = building_REI_1.get(index);
                building_RE_1_final = building_RE_1.get(index);
                building_R_2_final = building_R_2.get(index);
                building_REI_2_final = building_REI_2.get(index);
                building_R_3_final = building_R_3.get(index);
                return true;
            }
        } else if (cursor.getCount() == 0) {
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        confirmDialog();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить C.O. " + id_CO + "?");
        builder.setMessage("Вы уверены, что хотите сохранить C.O. " + id_CO + "?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addCOToDB();
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

    private void addCOToDB() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(building_R_1.getText().toString().trim());
        arrayList.add(building_E_1.getText().toString().trim());
        arrayList.add(building_REI_1.getText().toString().trim());
        arrayList.add(building_RE_1.getText().toString().trim());
        arrayList.add(building_R_2.getText().toString().trim());
        arrayList.add(building_REI_2.getText().toString().trim());
        arrayList.add(building_R_3.getText().toString().trim());
        arrayList.add(id_CO);
        arrayList.add(id_build);

        myDB.addCO(arrayList);
    }

    private void fillInfo() {
        building_R_1.setText(building_R_1_final);
        building_E_1.setText(building_E_1_final);
        building_REI_1.setText(building_REI_1_final);
        building_RE_1.setText(building_RE_1_final);
        building_R_2.setText(building_R_2_final);
        building_REI_2.setText(building_REI_2_final);
        building_R_3.setText(building_R_3_final);

        building_R_1_text.setVisibility(View.VISIBLE);
        building_E_1_text.setVisibility(View.VISIBLE);
        building_REI_1_text.setVisibility(View.VISIBLE);
        building_RE_1_text.setVisibility(View.VISIBLE);
        building_R_2_text.setVisibility(View.VISIBLE);
        building_REI_2_text.setVisibility(View.VISIBLE);
        building_R_3_text.setVisibility(View.VISIBLE);
    }
}
