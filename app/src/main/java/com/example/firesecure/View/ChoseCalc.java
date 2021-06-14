package com.example.firesecure.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseDataDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class ChoseCalc extends AppCompatActivity implements View.OnClickListener {
    // главное инфо
    private Double main_area_size;
    private String intensity_info;
    private String linear_speed_info;

    private ChoseBuildingDatabase myDB;
    private ChoseDataDatabase DataDB;
    private String id_build, id_divis, name_build;

    private Spinner build_spinner;
    private Button calc_area, calc_all;
    private TextView linear_speed;
    private EditText intensity, area_size;

    private ArrayList<String> id_building_array = new ArrayList<>();
    private ArrayList<String> name_building_array = new ArrayList<>();
    private ArrayList<String> id_division_array = new ArrayList<>();

    private ArrayList<String> id_building_array_final = new ArrayList<>();
    private ArrayList<String> name_building_array_final = new ArrayList<>();
    private ArrayList<String> id_division_array_final = new ArrayList<>();

    private ArrayList<String> intensity_water_info_array = new ArrayList<>();
    private ArrayList<String> linear_speed_of_fire_info_array = new ArrayList<>();
    private ArrayList<String> id_build_info_array = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_calc);

        init();
        fillSpinner();
        fillInfo();
        spinnerClickListener();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        try {
            id_build = extras.getString("id_build");
            id_divis = extras.getString("id_divis");
        } catch (Exception e) {
            id_divis = null;
        }


        myDB = new ChoseBuildingDatabase(this);
        DataDB = new ChoseDataDatabase(this);

        build_spinner = (Spinner) findViewById(R.id.build_spinner);

        calc_area = (Button) findViewById(R.id.calc_area);
        if (id_divis != null) {
            calc_area.setOnClickListener(this);
        }
        area_size = (EditText) findViewById(R.id.area_size);

        intensity = (EditText) findViewById(R.id.intensity);

        linear_speed = (TextView) findViewById(R.id.linear_speed);

        calc_all = (Button) findViewById(R.id.calc_all);
        calc_all.setOnClickListener(this);
    }

    private void fillSpinner() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() != 0) { // если БД пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_building_array.add(cursor.getString(0));
                name_building_array.add(cursor.getString(1));
                id_division_array.add(cursor.getString(4));
            }
            if (id_divis != null){
                checkDivision();
            }
        } else {
            name_building_array_final.add("Нет информации");
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_division_array.size(); i++) {
            if (id_divis.equals(id_division_array.get(i))) {
                id_building_array_final.add(id_building_array.get(i));
                name_building_array_final.add(name_building_array.get(i));
                id_division_array_final.add(id_division_array.get(i));
            }
        }
    }

    private void spinnerClickListener() {
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, name_building_array_final.toArray(new String[name_building_array_final.size()]));
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        build_spinner.setAdapter(langAdapter);
        build_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                id_build = id_building_array_final.get(position);
                name_build = name_building_array_final.get(position);
                if (id_build_info_array.contains(id_build)) {
                    int index = id_build_info_array.indexOf(id_build);
                    intensity.setText(intensity_water_info_array.get(index));
                    linear_speed.setText(linear_speed_of_fire_info_array.get(index));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void fillInfo() {
        Cursor cursor = DataDB.readAllData();
        if (cursor.getCount() != 0) { // если БД пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                intensity_water_info_array.add(cursor.getString(2));
                linear_speed_of_fire_info_array.add(cursor.getString(3));
                id_build_info_array.add(cursor.getString(4));
            }
        } else {
            linear_speed.setText("Нет информации");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.calc_area:
                intent = new Intent(this, CalcArea.class);
                intent.putExtra("id_build", id_build);
                intent.putExtra("name_build", name_build);
                startActivityForResult(intent, 1);
                break;
            case R.id.calc_all:
                intent = new Intent(this, CalcAll.class);
                intent.putExtra("area_size", area_size.getText().toString().trim());
                intent.putExtra("intensity", intensity.getText().toString().trim());
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        main_area_size = Double.valueOf(data.getStringExtra("main_area_size"));
        area_size.setText(String.valueOf(main_area_size));
    }
}
