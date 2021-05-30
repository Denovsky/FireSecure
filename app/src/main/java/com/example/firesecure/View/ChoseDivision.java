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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.Adapters.CustomAdapterChoseBuilding;
import com.example.firesecure.Adapters.CustomAdapterChoseDivision;
import com.example.firesecure.Model.ChoseDivisionDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class ChoseDivision extends AppCompatActivity implements View.OnClickListener {

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChoseDivisionDatabase myDB;

    private ArrayList<String> id_divis = new ArrayList<>();
    private ArrayList<String> num_divis = new ArrayList<>();
    private ArrayList<String> depo_divis = new ArrayList<>();
    private ArrayList<String> town_divis = new ArrayList<>();

    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_division);

        init();
    }

    private void init() {
        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDB = new ChoseDivisionDatabase(ChoseDivision.this);

        if (!flag) {
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            clearAllArray();

            storeDataInArrays();
        }
        flag = false;

        for (int i = 0; i < num_divis.size(); i++) {
            Log.d("main", num_divis.get(i) + " - DB");
        }

        CustomAdapterChoseDivision customAdapterChoseDivision = new CustomAdapterChoseDivision(ChoseDivision.this, this,
                id_divis, num_divis, depo_divis, town_divis);
        recyclerView.setAdapter(customAdapterChoseDivision);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoseDivision.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddDivision.class);
                startActivity(intent);
                break;
            case R.id.search_fub:
                flag = true;
                String divisSearch = enter_search.getText().toString().trim();
                searchResult(divisSearch);
                break;
        }
    }

    private void clearAllArray(){
        id_divis.clear();
        num_divis.clear();
        depo_divis.clear();
        town_divis.clear();
    }

    private void searchResult(String divisSearch) {
        storeDataInArrays();

        if (num_divis.contains(divisSearch)) {

            ArrayList<String> searchArray = new ArrayList<>();
            int index = num_divis.indexOf(divisSearch);
            searchArray.add(id_divis.get(index));
            searchArray.add(num_divis.get(index));
            searchArray.add(depo_divis.get(index));
            searchArray.add(town_divis.get(index));

            id_divis.clear();
            num_divis.clear();
            depo_divis.clear();
            town_divis.clear();

            id_divis.add(searchArray.get(0));
            num_divis.add(searchArray.get(1));
            depo_divis.add(searchArray.get(2));
            town_divis.add(searchArray.get(3));

            searchArray.clear();

            onResume();

        } else if (divisSearch.equals("")) {
            flag = false;

            onResume();

        } else {

            id_divis.clear();
            num_divis.clear();
            depo_divis.clear();
            town_divis.clear();

            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            onResume();

        }
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            Log.d("main", String.valueOf(cursor.getCount()));
            while (cursor.moveToNext()) {
                id_divis.add(cursor.getString(0));
                num_divis.add(cursor.getString(1));
                depo_divis.add(cursor.getString(2));
                town_divis.add(cursor.getString(3));
            }
        }
    }
}
