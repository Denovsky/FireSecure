package com.example.firesecure.View_and_Presenter;

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

import com.example.firesecure.Adapters.CustomAdapterChosePool;
import com.example.firesecure.Model.ChosePoolDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChosePool extends AppCompatActivity implements View.OnClickListener {

    private String id_divis;
    private boolean flag = false;

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChosePoolDatabase myDB;

    private ArrayList<String> id_pool = new ArrayList<>();
    private ArrayList<String> num_pool = new ArrayList<>();
    private ArrayList<String> address_pool = new ArrayList<>();
    private ArrayList<String> size_pool = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    private ArrayList<String> id_pool_final = new ArrayList<>();
    private ArrayList<String> num_pool_final = new ArrayList<>();
    private ArrayList<String> address_pool_final = new ArrayList<>();
    private ArrayList<String> size_pool_final = new ArrayList<>();
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
        myDB = new ChosePoolDatabase(ChosePool.this);

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

        for (int i = 0; i < num_pool_final.size(); i++) {
            Log.d("main", id_pool_final.get(i) + " - id");
            Log.d("main", num_pool_final.get(i) + " - name");
        }

        CustomAdapterChosePool customAdapterChosePool =
                new CustomAdapterChosePool(ChosePool.this, this,
                        id_pool_final,
                        num_pool_final,
                        address_pool_final,
                        size_pool_final,
                        id_division_final);
        recyclerView.setAdapter(customAdapterChosePool);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChosePool.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddPool.class);
                intent.putExtra("id_divis", id_divis);
                startActivity(intent);
                break;
            case R.id.search_fub:
                searchResult(enter_search.getText().toString().trim());
                break;
        }
    }

    private void searchResult(String numPool) {

        storeDataInArrays(); // на выход получаю правильно заполненые final массивы

        int index = 0;
        ArrayList<String> array = new ArrayList<>();
        if (num_pool_final.contains(numPool) || address_pool_final.contains(numPool)) {
            if (num_pool_final.contains(numPool)) {
                index = num_pool_final.indexOf(numPool);
            }
            if (address_pool_final.contains(numPool)) {
                index = address_pool_final.indexOf(numPool);
            }
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_pool_final.get(index));
            array.add(num_pool_final.get(index));
            array.add(address_pool_final.get(index));
            array.add(size_pool_final.get(index));
            array.add(id_division_final.get(index));

            id_pool_final.clear();
            num_pool_final.clear();
            address_pool_final.clear();
            size_pool_final.clear();
            id_division_final.clear();

            id_pool_final.add(array.get(0));
            num_pool_final.add(array.get(1));
            address_pool_final.add(array.get(2));
            size_pool_final.add(array.get(3));
            id_division_final.add(array.get(4));

            array.clear();

            onResume();

        } else if (numPool.equals("")) {
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
        id_pool.clear();
        num_pool.clear();
        address_pool.clear();
        size_pool.clear();
        id_division.clear();

        id_pool_final.clear();
        num_pool_final.clear();
        address_pool_final.clear();
        size_pool_final.clear();
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
                id_pool.add(cursor.getString(0));
                num_pool.add(cursor.getString(1));
                address_pool.add(cursor.getString(2));
                size_pool.add(cursor.getString(3));
                id_division.add(cursor.getString(4));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_pool_final.size() == 0) { // если в пожарном подразделении нету сооружений
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_pool.clear();
            num_pool.clear();
            address_pool.clear();
            size_pool.clear();
            id_division.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_division.size(); i++) {
            if (id_divis.equals(id_division.get(i))) {
                id_pool_final.add(id_pool.get(i));
                num_pool_final.add(num_pool.get(i));
                address_pool_final.add(address_pool.get(i));
                size_pool_final.add(size_pool.get(i));
                id_division_final.add(id_division.get(i));
            }
        }
    }
}
