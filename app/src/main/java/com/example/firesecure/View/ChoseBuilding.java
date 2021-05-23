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

import com.example.firesecure.Adapters.CustomAdapterChoseBuilding;
import com.example.firesecure.Adapters.CustomAdapterChoseDivision;
import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseDivisionDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseBuilding extends AppCompatActivity implements View.OnClickListener {

    private String id_divis;

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChoseBuildingDatabase myDB;
    private ArrayList<String> id_building, name_building, depo_building, address_building, id_divis_array;
    private ArrayList<String> id_building_final, name_building_final, depo_building_final, address_building_final, id_divis_array_final;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_building);

        init();
    }

    private void init(){
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
        myDB = new ChoseBuildingDatabase(ChoseBuilding.this);

        id_building = new ArrayList<>();
        name_building = new ArrayList<>();
        depo_building = new ArrayList<>();
        address_building = new ArrayList<>();
        id_divis_array = new ArrayList<>();

//        for (int i = 0; i < name_building.size(); i++){
//            Log.d("main", id_building.get(i) + " - id");
//            Log.d("main", name_building.get(i) + " - name");
//        }

        if (storeDataInArrays()){
            CustomAdapterChoseBuilding customAdapterChoseBuilding = new CustomAdapterChoseBuilding(ChoseBuilding.this,this,
                    id_building_final, name_building_final, depo_building_final, address_building_final, id_divis_array_final);
            recyclerView.setAdapter(customAdapterChoseBuilding);
            recyclerView.setLayoutManager(new LinearLayoutManager(ChoseBuilding.this));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_fub:
                Intent intent = new Intent(this, AddBuilding.class);
                intent.putExtra("id", id_divis);
                startActivity(intent);
                break;
            case R.id.search_fub:
                break;
        }
    }

    boolean storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            return false;
        } else {
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            Log.d("main", String.valueOf(cursor.getCount()));
            while (cursor.moveToNext()) {
                id_building.add(cursor.getString(0));
                name_building.add(cursor.getString(1));
                depo_building.add(cursor.getString(2));
                address_building.add(cursor.getString(3));
                id_divis_array.add(cursor.getString(4));
            }
            checkDivision();
            if (id_building_final.size() == 0){
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                return false;
            } else {
                return true;
            }
        }
    }

    private void checkDivision() {

        id_building_final = new ArrayList<>();
        name_building_final = new ArrayList<>();
        depo_building_final = new ArrayList<>();
        address_building_final = new ArrayList<>();
        id_divis_array_final = new ArrayList<>();

        for (int i = 0; i < id_divis_array.size(); i++) {
            if (id_divis.equals(id_divis_array.get(i))){
                id_building_final.add(id_building.get(i));
                name_building_final.add(name_building.get(i));
                depo_building_final.add(depo_building.get(i));
                address_building_final.add(address_building.get(i));
                id_divis_array_final.add(id_divis_array.get(i));
            }
        }
        for (int i = 0; i < id_building_final.size(); i++){
            Log.d("main", id_building_final.get(i) + " - id");
            Log.d("main", name_building_final.get(i) + " - name");
        }
    }
}
