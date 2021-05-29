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
import com.example.firesecure.Adapters.CustomAdapterChoseRoom;
import com.example.firesecure.Model.ChoseRoomDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseRoom extends AppCompatActivity implements View.OnClickListener {

    private String id_floor;
    private boolean flag = false;

    private EditText enter_search;
    private ImageView empty_imageview;
    private TextView no_data;
    private FloatingActionButton search_fub, add_fub;
    private RecyclerView recyclerView;
    private ChoseRoomDatabase myDB;

    private ArrayList<String> id_room = new ArrayList<>();
    private ArrayList<String> num_room = new ArrayList<>();
    private ArrayList<String> name_room = new ArrayList<>();
    private ArrayList<String> status_room = new ArrayList<>();
    private ArrayList<String> length_sleeve = new ArrayList<>();
    private ArrayList<String> state_num_people_in = new ArrayList<>();
    private ArrayList<String> approx_num_people_in = new ArrayList<>();
    private ArrayList<String> room_with_ahs = new ArrayList<>();
    private ArrayList<String> room_with_sanitary = new ArrayList<>();
    private ArrayList<String> rv_dm_vv_rtt = new ArrayList<>();
    private ArrayList<String> num_entry = new ArrayList<>();
    private ArrayList<String> area_size_room = new ArrayList<>();
    private ArrayList<String> id_floor_array = new ArrayList<>();

    private ArrayList<String> id_room_final = new ArrayList<>();
    private ArrayList<String> num_room_final = new ArrayList<>();
    private ArrayList<String> name_room_final = new ArrayList<>();
    private ArrayList<String> status_room_final = new ArrayList<>();
    private ArrayList<String> length_sleeve_final = new ArrayList<>();
    private ArrayList<String> state_num_people_in_final = new ArrayList<>();
    private ArrayList<String> approx_num_people_in_final = new ArrayList<>();
    private ArrayList<String> room_with_ahs_final = new ArrayList<>();
    private ArrayList<String> room_with_sanitary_final = new ArrayList<>();
    private ArrayList<String> rv_dm_vv_rtt_final = new ArrayList<>();
    private ArrayList<String> num_entry_final = new ArrayList<>();
    private ArrayList<String> area_size_room_final = new ArrayList<>();
    private ArrayList<String> id_floor_array_final = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_room);

        init();
    }

    private void init() {
        myDB = new ChoseRoomDatabase(ChoseRoom.this);

        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        id_floor = extras.getString("id");

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDB = new ChoseRoomDatabase(this);

        if (!flag) {
            clearAllArray();

            storeDataInArrays(); // на выход получаю правильно заполненые final массивы
        }

        flag = false;

        for (int i = 0; i < num_room.size(); i++) {
            Log.d("main", num_room.get(i) + " - num");
        }

        CustomAdapterChoseRoom customAdapterChoseRoom =
                new CustomAdapterChoseRoom(ChoseRoom.this, this,
                        id_room_final,
                        num_room_final,
                        name_room_final,
                        status_room_final,
                        length_sleeve_final,
                        state_num_people_in_final,
                        approx_num_people_in_final,
                        room_with_ahs_final,
                        room_with_sanitary_final,
                        rv_dm_vv_rtt_final,
                        num_entry_final,
                        area_size_room_final,
                        id_floor_array_final);
        recyclerView.setAdapter(customAdapterChoseRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddRoom.class);
                intent.putExtra("id", id_floor);
                startActivity(intent);
                break;
            case R.id.search_fub:
                searchResult(enter_search.getText().toString().trim());
                break;
        }
    }

    private void searchResult(String numRoom) {

        storeDataInArrays(); // на выход получаю правильно заполненые final массивы

        int index = 0;
        ArrayList<String> array = new ArrayList<>();
        if (num_room_final.contains(numRoom)) {
            index = num_room_final.indexOf(numRoom);
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_room_final.get(index));
            array.add(num_room_final.get(index));
            array.add(name_room_final.get(index));
            array.add(status_room_final.get(index));
            array.add(length_sleeve_final.get(index));
            array.add(state_num_people_in_final.get(index));
            array.add(approx_num_people_in_final.get(index));
            array.add(room_with_ahs_final.get(index));
            array.add(room_with_sanitary_final.get(index));
            array.add(rv_dm_vv_rtt_final.get(index));
            array.add(num_entry_final.get(index));
            array.add(area_size_room_final.get(index));
            array.add(id_floor_array_final.get(index));

            id_room_final.clear();
            num_room_final.clear();
            name_room_final.clear();
            status_room_final.clear();
            length_sleeve_final.clear();
            state_num_people_in_final.clear();
            approx_num_people_in_final.clear();
            room_with_ahs_final.clear();
            room_with_sanitary_final.clear();
            rv_dm_vv_rtt_final.clear();
            num_entry_final.clear();
            area_size_room_final.clear();
            id_floor_array_final.clear();

            id_room_final.add(array.get(0));
            num_room_final.add(array.get(1));
            name_room_final.add(array.get(2));
            status_room_final.add(array.get(3));
            length_sleeve_final.add(array.get(4));
            state_num_people_in_final.add(array.get(5));
            approx_num_people_in_final.add(array.get(6));
            room_with_ahs_final.add(array.get(7));
            room_with_sanitary_final.add(array.get(8));
            rv_dm_vv_rtt_final.add(array.get(9));
            num_entry_final.add(array.get(10));
            area_size_room_final.add(array.get(11));
            id_floor_array_final.add(array.get(12));

            array.clear();

            onResume();

        } else if (numRoom.equals("")) {
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
        id_room.clear();
        num_room.clear();
        name_room.clear();
        status_room.clear();
        length_sleeve.clear();
        state_num_people_in.clear();
        approx_num_people_in.clear();
        room_with_ahs.clear();
        room_with_sanitary.clear();
        rv_dm_vv_rtt.clear();
        num_entry.clear();
        area_size_room.clear();
        id_floor_array.clear();

        id_room_final.clear();
        num_room_final.clear();
        name_room_final.clear();
        status_room_final.clear();
        length_sleeve_final.clear();
        state_num_people_in_final.clear();
        approx_num_people_in_final.clear();
        room_with_ahs_final.clear();
        room_with_sanitary_final.clear();
        rv_dm_vv_rtt_final.clear();
        num_entry_final.clear();
        area_size_room_final.clear();
        id_floor_array_final.clear();
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
                id_room.add(cursor.getString(0));
                num_room.add(cursor.getString(1));
                name_room.add(cursor.getString(2));
                status_room.add(cursor.getString(3));
                length_sleeve.add(cursor.getString(4));
                state_num_people_in.add(cursor.getString(5));
                approx_num_people_in.add(cursor.getString(6));
                room_with_ahs.add(cursor.getString(7));
                room_with_sanitary.add(cursor.getString(8));
                rv_dm_vv_rtt.add(cursor.getString(9));
                num_entry.add(cursor.getString(10));
                area_size_room.add(cursor.getString(11));
                id_floor_array.add(cursor.getString(12));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_room_final.size() == 0) { // если в сооружении нету комнат
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_room.clear();
            num_room.clear();
            name_room.clear();
            status_room.clear();
            length_sleeve.clear();
            state_num_people_in.clear();
            approx_num_people_in.clear();
            room_with_ahs.clear();
            room_with_sanitary.clear();
            rv_dm_vv_rtt.clear();
            num_entry.clear();
            area_size_room.clear();
            id_floor_array.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_floor_array.size(); i++) {
            if (id_floor.equals(id_floor_array.get(i))) {
                id_room_final.add(id_room.get(i));
                num_room_final.add(num_room.get(i));
                name_room_final.add(name_room.get(i));
                status_room_final.add(status_room.get(i));
                length_sleeve_final.add(length_sleeve.get(i));
                state_num_people_in_final.add(state_num_people_in.get(i));
                approx_num_people_in_final.add(approx_num_people_in.get(i));
                room_with_ahs_final.add(room_with_ahs.get(i));
                room_with_sanitary_final.add(room_with_sanitary.get(i));
                rv_dm_vv_rtt_final.add(rv_dm_vv_rtt.get(i));
                num_entry_final.add(num_entry.get(i));
                area_size_room_final.add(area_size_room.get(i));
                id_floor_array_final.add(id_floor_array.get(i));
            }
        }
    }
}
