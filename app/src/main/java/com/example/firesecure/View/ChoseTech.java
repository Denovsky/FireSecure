package com.example.firesecure.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.Adapters.CustomAdapterChoseTech;
import com.example.firesecure.Model.ChoseEmployeeDatabase;
import com.example.firesecure.Model.ChoseTechDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseTech extends AppCompatActivity implements View.OnClickListener {

    private String id_divis;
    private boolean flag = false, flag_check = false;
    private ImageView empty_imageview;
    private TextView no_data;
    private ArrayList<String> isChecker = new ArrayList<>();

    private EditText enter_search;
    private FloatingActionButton search_fub, add_fub;
    private CheckBox main, sentry, special, helps;
    private RecyclerView recyclerView;
    private ChoseTechDatabase myDB;

    private ArrayList<String> id_tech = new ArrayList<>();
    private ArrayList<String> mark_tech = new ArrayList<>();
    private ArrayList<String> type_tech = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    private ArrayList<String> id_tech_final = new ArrayList<>();
    private ArrayList<String> mark_tech_final = new ArrayList<>();
    private ArrayList<String> type_tech_final = new ArrayList<>();
    private ArrayList<String> id_division_final = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_tech);

        init();
    }

    private void init() {
        myDB = new ChoseTechDatabase(ChoseTech.this);

        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        main = (CheckBox) findViewById(R.id.main);
        main.setOnClickListener(this);

        sentry = (CheckBox) findViewById(R.id.sentry);
        sentry.setOnClickListener(this);

        special = (CheckBox) findViewById(R.id.special);
        special.setOnClickListener(this);

        helps = (CheckBox) findViewById(R.id.helps);
        helps.setOnClickListener(this);

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
        flag_check = false;
        flag = false;

        for (int i = 0; i < mark_tech_final.size(); i++) {
            Log.d("main", id_tech_final.get(i) + " - id");
            Log.d("main", mark_tech_final.get(i) + " - name");
        }

        CustomAdapterChoseTech customAdapterChoseEmployee =
                new CustomAdapterChoseTech(ChoseTech.this, this,
                        id_tech_final,
                        mark_tech_final,
                        type_tech_final,
                        id_division_final);
        recyclerView.setAdapter(customAdapterChoseEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoseTech.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddTech.class);
                intent.putExtra("id_divis", id_divis);
                startActivity(intent);
                break;
            case R.id.search_fub:
                searchResult(enter_search.getText().toString().trim());
                break;
            case R.id.main:
                if (main.isChecked()){
                    isChecker.add("Основная");
                } else {
                    isChecker.remove("Основная");
                }
                break;
            case R.id.sentry:
                if (sentry.isChecked()){
                    isChecker.add("Целевая");
                } else {
                    isChecker.remove("Целевая");
                }
                break;
            case R.id.special:
                if (special.isChecked()){
                    isChecker.add("Специальная");
                } else {
                    isChecker.remove("Специальная");
                }
                break;
            case R.id.helps:
                if (helps.isChecked()){
                    isChecker.add("Вспомогательная");
                } else {
                    isChecker.remove("Вспомогательная");
                }
                break;
        }
    }

    private void searchResult(String numFloor) {

        clearAllArray();

        storeDataInArrays(); // на выход получаю правильно заполненые final массивы

        CheckerOfChecker();

        int index = 0;
        ArrayList<String> array = new ArrayList<>();
        if (type_tech_final.contains(numFloor)) {
            index = type_tech_final.indexOf(numFloor);
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_tech_final.get(index));
            array.add(mark_tech_final.get(index));
            array.add(type_tech_final.get(index));
            array.add(id_division_final.get(index));

            id_tech_final.clear();
            mark_tech_final.clear();
            type_tech_final.clear();
            id_division_final.clear();

            id_tech_final.add(array.get(0));
            mark_tech_final.add(array.get(1));
            type_tech_final.add(array.get(2));
            id_division_final.add(array.get(3));

            array.clear();

            onResume();

        } else if (numFloor.equals("") && flag_check) {

            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            onResume();
        } else if (numFloor.equals("") && !flag_check) {
            flag = true;
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

    private void CheckerOfChecker(){

        if (isChecker.size() != 0){

            ArrayList<String> one = new ArrayList<>();
            ArrayList<String> two = new ArrayList<>();
            ArrayList<String> three = new ArrayList<>();
            ArrayList<String> four = new ArrayList<>();

            Log.d("ILYA2228", String.valueOf(isChecker));

            for (int i = 0; i < mark_tech_final.size(); i++){
                if (isChecker.contains(mark_tech_final.get(i))){
                    one.add(id_tech_final.get(i));
                    two.add(mark_tech_final.get(i));
                    three.add(type_tech_final.get(i));
                    four.add(id_division_final.get(i));
                }
            }
            id_tech_final.clear();
            mark_tech_final.clear();
            type_tech_final.clear();
            id_division_final.clear();

            id_tech_final = one;
            mark_tech_final = two;
            type_tech_final = three;
            id_division_final = four;

        } else if (isChecker.size() == 0) {
            flag_check = true;
        }
    }

    private void clearAllArray() {
        id_tech.clear();
        mark_tech.clear();
        type_tech.clear();
        id_division.clear();

        id_tech_final.clear();
        mark_tech_final.clear();
        type_tech_final.clear();
        id_division_final.clear();
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) { // если БД пустой
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else { // если БД не пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_tech.add(cursor.getString(0));
                mark_tech.add(cursor.getString(1));
                type_tech.add(cursor.getString(2));
                id_division.add(cursor.getString(14));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_tech_final.size() == 0) { // если в пожарном подразделении нету сооружений
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_tech.clear();
            mark_tech.clear();
            type_tech.clear();
            id_division.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_division.size(); i++) {
            if (id_divis.equals(id_division.get(i))) {
                id_tech_final.add(id_tech.get(i));
                mark_tech_final.add(mark_tech.get(i));
                type_tech_final.add(type_tech.get(i));
                id_division_final.add(id_division.get(i));
            }
        }
    }
}
