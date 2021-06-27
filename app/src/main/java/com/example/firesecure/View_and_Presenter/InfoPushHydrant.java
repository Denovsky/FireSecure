package com.example.firesecure.View_and_Presenter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChosePushOfHydrantDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class InfoPushHydrant extends AppCompatActivity implements View.OnClickListener {

    private ChosePushOfHydrantDatabase myDB;
    private String id_facility;

    private TextView header;
    private Spinner type_network;

    private TextView diameter_network, press_network, push_network;

    private ArrayList<String> type_network_array = new ArrayList<>();
    private ArrayList<String> diameter_network_array = new ArrayList<>();
    private ArrayList<String> press_network_array = new ArrayList<>();
    private ArrayList<String> push_network_array = new ArrayList<>();

    private FloatingActionButton add_fub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_push_hydrant);

        init();
    }

    private void spinnerClickListener() {
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, type_network_array.toArray(new String[type_network_array.size()]));
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        type_network.setAdapter(langAdapter);
        type_network.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                diameter_network.setText(diameter_network_array.get(position));
                press_network.setText(press_network_array.get(position));
                push_network.setText(push_network_array.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearAllArray();
        fillSpinners();
        spinnerClickListener();
    }

    private void init() {

        myDB = new ChosePushOfHydrantDatabase(this);

        header = (TextView) findViewById(R.id.header);

        type_network = (Spinner) findViewById(R.id.type_network);

        diameter_network = (TextView) findViewById(R.id.diameter_network);
        press_network = (TextView) findViewById(R.id.press_network);
        push_network = (TextView) findViewById(R.id.push_network);
        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

    }

    private void clearAllArray() {
        type_network_array.clear();
        diameter_network_array.clear();
        press_network_array.clear();
        push_network_array.clear();
    }

    private void fillSpinners() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() != 0) { // если БД пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                type_network_array.add(cursor.getString(1));
                diameter_network_array.add(cursor.getString(2));
                press_network_array.add(cursor.getString(3));
                push_network_array.add(cursor.getString(4));
            }
        } else {
            diameter_network.setText("Нет информации");
            press_network.setText("Нет информации");
            push_network.setText("Нет информации");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddPushHydrant.class);
                startActivity(intent);
                break;
        }
    }
}
