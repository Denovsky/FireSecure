package com.example.firesecure.View_and_Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.Adapters.CustomAdapterChoseAreaCalc;
import com.example.firesecure.Model.ChoseRoomDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CalcArea extends AppCompatActivity {

    private String id_build, name_build;
    private Double main_area_size;

    public void setMain_area_size(Double main_area_size) {
        this.main_area_size = main_area_size;
    }

    private ImageView empty_imageview;
    public TextView no_data, header, area_size;
    private RecyclerView recyclerView;
    private ChoseRoomDatabase myDB;
    private FloatingActionButton close_all;

    private Context context;

    private ArrayList<String> num_room = new ArrayList<>();
    private ArrayList<String> name_room = new ArrayList<>();
    private ArrayList<String> area_size_room = new ArrayList<>();
    private ArrayList<String> id_build_array = new ArrayList<>();

    private ArrayList<String> num_room_final = new ArrayList<>();
    private ArrayList<String> name_room_final = new ArrayList<>();
    private ArrayList<String> area_size_room_final = new ArrayList<>();
    private ArrayList<String> id_build_array_final = new ArrayList<>();

    public CalcArea(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calc_area);

        init();
        close_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Toast.makeText(context, "Нажатие", Toast.LENGTH_LONG).show();
                if (!area_size.getText().toString().trim().equals("")){
                    setMain_area_size(Double.valueOf(area_size.getText().toString().trim()));
                    intent.putExtra("main_area_size", String.valueOf(main_area_size));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(context, "Выберите помещения", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        context = this;

        myDB = new ChoseRoomDatabase(CalcArea.this);

        header = (TextView) findViewById(R.id.header);
        area_size = (TextView) findViewById(R.id.area_size);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        close_all = (FloatingActionButton) findViewById(R.id.close_all);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id_build");
        name_build = extras.getString("name_build");

        header.setText(name_build);
    }

    @Override
    protected void onResume() {
        super.onResume();

        empty_imageview.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        clearAllArray();

        storeDataInArrays(); // на выход получаю правильно заполненые final массивы

        CustomAdapterChoseAreaCalc customAdapterChoseAreaCalc =
                new CustomAdapterChoseAreaCalc(CalcArea.this, this,
                        num_room_final,
                        name_room_final,
                        area_size_room_final,
                        id_build);
        recyclerView.setAdapter(customAdapterChoseAreaCalc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void clearAllArray() {
        num_room.clear();
        name_room.clear();
        area_size_room.clear();
        id_build_array.clear();

        num_room_final.clear();
        name_room_final.clear();
        area_size_room_final.clear();
        id_build_array_final.clear();
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) { // если БД пустой
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else { // если БД не пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                num_room.add(cursor.getString(1));
                name_room.add(cursor.getString(2));
                area_size_room.add(cursor.getString(11));
                id_build_array.add(cursor.getString(13));
            }
            fillArrays(); // делаю рабочими конечные массивы
            if (num_room_final.size() == 0) { // если в сооружении нету комнат
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            num_room.clear();
            name_room.clear();
            area_size_room.clear();
            id_build_array.clear();
        }
    }

    private void fillArrays() {
        for (int i = 0; i < id_build_array.size(); i++) {
            if (id_build.equals(id_build_array.get(i))) {
                num_room_final.add(num_room.get(i));
                name_room_final.add(name_room.get(i));
                area_size_room_final.add(area_size_room.get(i));
                id_build_array_final.add(id_build_array.get(i));
            }
        }
    }
}