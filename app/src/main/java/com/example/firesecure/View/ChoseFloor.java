package com.example.firesecure.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.Adapters.CustomAdapterChoseFloor;
import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseFloor extends AppCompatActivity implements View.OnClickListener {

    private String id_build;
    private boolean flag = false;

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChoseFloorDatabase myDB;

    private ArrayList<String> id_floor = new ArrayList<>();
    private ArrayList<String> num_floor = new ArrayList<>();
    private ArrayList<String> status_floor = new ArrayList<>();
    private ArrayList<String> entry_num = new ArrayList<>();
    private ArrayList<String> length_lever = new ArrayList<>();
    private ArrayList<String> area_size_floor = new ArrayList<>();
    private ArrayList<String> id_building = new ArrayList<>();

    private ArrayList<String> id_floor_final = new ArrayList<>();
    private ArrayList<String> num_floor_final = new ArrayList<>();
    private ArrayList<String> status_floor_final = new ArrayList<>();
    private ArrayList<String> entry_num_final = new ArrayList<>();
    private ArrayList<String> length_lever_final = new ArrayList<>();
    private ArrayList<String> area_size_floor_final = new ArrayList<>();
    private ArrayList<String> id_building_final = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_floor);

        init();
    }

    private void init() {
        myDB = new ChoseFloorDatabase(ChoseFloor.this);

        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id");

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

        for (int i = 0; i < num_floor.size(); i++) {
            Log.d("main", id_floor.get(i) + " - id");
            Log.d("main", num_floor.get(i) + " - name");
        }

        CustomAdapterChoseFloor CustomAdapterChoseFloor =
                new CustomAdapterChoseFloor(ChoseFloor.this, this,
                id_floor_final,
                num_floor_final,
                status_floor_final,
                entry_num_final,
                length_lever_final,
                area_size_floor_final,
                id_building_final);
        recyclerView.setAdapter(CustomAdapterChoseFloor);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoseFloor.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddFloor.class);
                intent.putExtra("id", id_build);
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
        if (num_floor_final.contains(numFloor)) {
            index = num_floor_final.indexOf(numFloor);
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_floor_final.get(index));
            array.add(num_floor_final.get(index));
            array.add(status_floor_final.get(index));
            array.add(entry_num_final.get(index));
            array.add(length_lever_final.get(index));
            array.add(area_size_floor_final.get(index));
            array.add(id_building_final.get(index));

            id_floor_final.clear();
            num_floor_final.clear();
            status_floor_final.clear();
            entry_num_final.clear();
            length_lever_final.clear();
            area_size_floor_final.clear();
            id_building_final.clear();

            id_floor_final.add(array.get(0));
            num_floor_final.add(array.get(1));
            status_floor_final.add(array.get(2));
            entry_num_final.add(array.get(3));
            length_lever_final.add(array.get(4));
            area_size_floor_final.add(array.get(5));
            id_building_final.add(array.get(6));

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
        id_floor.clear();
        num_floor.clear();
        status_floor.clear();
        entry_num.clear();
        length_lever.clear();
        area_size_floor.clear();
        id_building.clear();

        id_floor_final.clear();
        num_floor_final.clear();
        status_floor_final.clear();
        entry_num_final.clear();
        length_lever_final.clear();
        area_size_floor_final.clear();
        id_building_final.clear();
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) { // если БД пустой
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else { // если БД не пустой
            Log.d("main", String.valueOf(cursor.getCount())); // смотрю курсор
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_floor.add(cursor.getString(0));
                num_floor.add(cursor.getString(1));
                status_floor.add(cursor.getString(2));
                entry_num.add(cursor.getString(3));
                length_lever.add(cursor.getString(4));
                area_size_floor.add(cursor.getString(5));
                id_building.add(cursor.getString(6));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_building_final.size() == 0) { // если в пожарном подразделении нету сооружений
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_floor.clear();
            num_floor.clear();
            status_floor.clear();
            entry_num.clear();
            length_lever.clear();
            area_size_floor.clear();
            id_building.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_building.size(); i++) {
            if (id_build.equals(id_building.get(i))) {

                id_floor_final.add(id_floor.get(i));
                num_floor_final.add(num_floor.get(i));
                status_floor_final.add(status_floor.get(i));
                entry_num_final.add(entry_num.get(i));
                length_lever_final.add(length_lever.get(i));
                area_size_floor_final.add(area_size_floor.get(i));
                id_building_final.add(id_building.get(i));
            }
        }
    }
}
