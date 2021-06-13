package com.example.firesecure.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.Adapters.CustomAdapterChoseData;
import com.example.firesecure.Model.ChoseDataDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseData extends AppCompatActivity implements View.OnClickListener {

    private String id_build_str;
    private boolean flag = false;

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChoseDataDatabase myDB;

    private ArrayList<String> id_facility = new ArrayList<>();
    private ArrayList<String> objects_and_materials = new ArrayList<>();
    private ArrayList<String> intensity_water = new ArrayList<>();
    private ArrayList<String> linear_speed_of_fire = new ArrayList<>();
    private ArrayList<String> id_build = new ArrayList<>();

    private ArrayList<String> id_facility_final = new ArrayList<>();
    private ArrayList<String> objects_and_materials_final = new ArrayList<>();
    private ArrayList<String> intensity_water_final = new ArrayList<>();
    private ArrayList<String> linear_speed_of_fire_final = new ArrayList<>();
    private ArrayList<String> id_build_final = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_data);

        init();
    }

    private void init() {
        myDB = new ChoseDataDatabase(ChoseData.this);

        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        id_build_str = extras.getString("id_build");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!flag) {
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            clearAllArray();

            storeDataInArrays(); // на выход получаю правильно заполненые final массивы
        }

        flag = false;

        CustomAdapterChoseData customAdapterChoseData =
                new CustomAdapterChoseData(ChoseData.this, this,
                        id_facility_final,
                        objects_and_materials_final,
                        intensity_water_final,
                        linear_speed_of_fire_final,
                        id_build_final);
        recyclerView.setAdapter(customAdapterChoseData);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoseData.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddData.class);
                intent.putExtra("id_build", id_build_str);
                startActivity(intent);
                break;
            case R.id.search_fub:
                searchResult(enter_search.getText().toString().trim());
                break;
        }
    }

    private void searchResult(String numFloor) {

        storeDataInArrays(); // на выход получаю правильно заполненые final массивы

        int index = 0;
        ArrayList<String> array = new ArrayList<>();
        if (objects_and_materials_final.contains(numFloor)) {
            index = objects_and_materials_final.indexOf(numFloor);
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_facility_final.get(index));
            array.add(objects_and_materials_final.get(index));
            array.add(intensity_water_final.get(index));
            array.add(linear_speed_of_fire_final.get(index));
            array.add(id_build_final.get(index));

            id_facility_final.clear();
            objects_and_materials_final.clear();
            intensity_water_final.clear();
            linear_speed_of_fire_final.clear();
            id_build_final.clear();

            id_facility_final.add(array.get(0));
            objects_and_materials_final.add(array.get(1));
            intensity_water_final.add(array.get(2));
            linear_speed_of_fire_final.add(array.get(3));
            id_build_final.add(array.get(4));

            array.clear();

            onResume();

        } else if (numFloor.equals("")) {
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            onResume();
        } else {
            flag = true;
            clearAllArray();

            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            onResume();
        }
    }

    private void clearAllArray() {
        id_facility.clear();
        objects_and_materials.clear();
        intensity_water.clear();
        linear_speed_of_fire.clear();
        id_build.clear();

        id_facility_final.clear();
        objects_and_materials_final.clear();
        intensity_water_final.clear();
        linear_speed_of_fire_final.clear();
        id_build_final.clear();
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) { // если БД пустой
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else { // если БД не пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_facility.add(cursor.getString(0));
                objects_and_materials.add(cursor.getString(1));
                intensity_water.add(cursor.getString(2));
                linear_speed_of_fire.add(cursor.getString(3));
                id_build.add(cursor.getString(4));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_facility_final.size() == 0) { // если в пожарном подразделении нету сооружений
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_facility.clear();
            objects_and_materials.clear();
            intensity_water.clear();
            linear_speed_of_fire.clear();
            id_build.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_facility.size(); i++) {
            if (id_build_str.equals(id_build.get(i))) {
                id_facility_final.add(id_facility.get(i));
                objects_and_materials_final.add(objects_and_materials.get(i));
                intensity_water_final.add(intensity_water.get(i));
                linear_speed_of_fire_final.add(linear_speed_of_fire.get(i));
                id_build_final.add(id_build.get(i));
            }
        }
    }
}
