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
import com.example.firesecure.Adapters.CustomAdapterChoseHydrant;
import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.Model.ChoseHydrantDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseHydrant extends AppCompatActivity implements View.OnClickListener {

    private String id_divis;
    private boolean flag = false;

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChoseHydrantDatabase myDB;

    private ArrayList<String> id_hydrant = new ArrayList<>();
    private ArrayList<String> num_hydrant = new ArrayList<>();
    private ArrayList<String> address_hydrant = new ArrayList<>();
    private ArrayList<String> id_push_hydrant = new ArrayList<>();
    private ArrayList<String> type_network = new ArrayList<>();
    private ArrayList<String> diameter_network = new ArrayList<>();
    private ArrayList<String> press_network = new ArrayList<>();
    private ArrayList<String> push_network = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    private ArrayList<String> id_hydrant_final = new ArrayList<>();
    private ArrayList<String> num_hydrant_final = new ArrayList<>();
    private ArrayList<String> address_hydrant_final = new ArrayList<>();
    private ArrayList<String> id_push_hydrant_final = new ArrayList<>();
    private ArrayList<String> type_network_final = new ArrayList<>();
    private ArrayList<String> diameter_network_final = new ArrayList<>();
    private ArrayList<String> press_network_final = new ArrayList<>();
    private ArrayList<String> push_network_final = new ArrayList<>();
    private ArrayList<String> id_division_final = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_hydrant);

        init();
    }

    private void init() {
        myDB = new ChoseHydrantDatabase(ChoseHydrant.this);

        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id");

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

        for (int i = 0; i < num_hydrant_final.size(); i++) {
            Log.d("main", id_hydrant_final.get(i) + " - id");
            Log.d("main", num_hydrant_final.get(i) + " - name");
        }

        CustomAdapterChoseHydrant customAdapterChoseHydrant =
                new CustomAdapterChoseHydrant(ChoseHydrant.this, this,
                        id_hydrant_final,
                        num_hydrant_final,
                        address_hydrant_final,
                        type_network_final,
                        diameter_network_final,
                        press_network_final,
                        push_network_final,
                        id_division_final);
        recyclerView.setAdapter(customAdapterChoseHydrant);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoseHydrant.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddHydrant.class);
                intent.putExtra("id_divis", id_divis);
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
        if (num_hydrant_final.contains(numFloor) || address_hydrant_final.contains(numFloor)) {
            if (num_hydrant_final.contains(numFloor)) {
                index = num_hydrant_final.indexOf(numFloor);
            }
            if (address_hydrant_final.contains(numFloor)) {
                index = address_hydrant_final.indexOf(numFloor);
            }
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_hydrant_final.get(index));
            array.add(num_hydrant_final.get(index));
            array.add(address_hydrant_final.get(index));
            array.add(type_network_final.get(index));
            array.add(diameter_network_final.get(index));
            array.add(press_network_final.get(index));
            array.add(push_network_final.get(index));
            array.add(id_division_final.get(index));

            id_hydrant_final.clear();
            num_hydrant_final.clear();
            address_hydrant_final.clear();
            type_network_final.clear();
            diameter_network_final.clear();
            press_network_final.clear();
            push_network_final.clear();
            id_division_final.clear();

            id_hydrant_final.add(array.get(0));
            num_hydrant_final.add(array.get(1));
            address_hydrant_final.add(array.get(2));
            type_network_final.add(array.get(3));
            diameter_network_final.add(array.get(4));
            press_network_final.add(array.get(5));
            push_network_final.add(array.get(6));
            id_division_final.add(array.get(7));

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
        id_hydrant.clear();
        num_hydrant.clear();
        address_hydrant.clear();
        type_network.clear();
        diameter_network.clear();
        press_network.clear();
        push_network.clear();
        id_division.clear();

        id_hydrant_final.clear();
        num_hydrant_final.clear();
        address_hydrant_final.clear();
        type_network_final.clear();
        diameter_network_final.clear();
        press_network_final.clear();
        push_network_final.clear();
        id_division_final.clear();
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
                id_hydrant.add(cursor.getString(0));
                num_hydrant.add(cursor.getString(1));
                address_hydrant.add(cursor.getString(2));
                type_network.add(cursor.getString(4));
                diameter_network.add(cursor.getString(5));
                press_network.add(cursor.getString(6));
                push_network.add(cursor.getString(7));
                id_division.add(cursor.getString(8));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_hydrant_final.size() == 0) { // если в пожарном подразделении нету сооружений
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_hydrant.clear();
            num_hydrant.clear();
            address_hydrant.clear();
            type_network.clear();
            diameter_network.clear();
            press_network.clear();
            push_network.clear();
            id_division.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_division.size(); i++) {
            if (id_divis.equals(id_division.get(i))) {
                id_hydrant_final.add(id_hydrant.get(i));
                num_hydrant_final.add(num_hydrant.get(i));
                address_hydrant_final.add(address_hydrant.get(i));
                type_network_final.add(type_network.get(i));
                diameter_network_final.add(diameter_network.get(i));
                press_network_final.add(press_network.get(i));
                push_network_final.add(push_network.get(i));
                id_division_final.add(id_division.get(i));
            }
        }
    }
}
