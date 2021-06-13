package com.example.firesecure.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.Model.ChoseHydrantDatabase;
import com.example.firesecure.Model.ChosePushOfHydrantDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddHydrant extends AppCompatActivity implements View.OnClickListener {

    private String id_divis,
            id_push_hydrant_str,
            type_network_str,
            diameter_network_str,
            press_network_str,
            push_network_str;
    private boolean flag;
    private ChoseHydrantDatabase myDB;
    private ChosePushOfHydrantDatabase pushDB;
    private ArrayList<String> id_push_hydrant_array = new ArrayList<>();
    private ArrayList<String> type_network_array = new ArrayList<>();
    private ArrayList<String> diameter_network_array = new ArrayList<>();
    private ArrayList<String> press_network_array = new ArrayList<>();
    private ArrayList<String> push_network_array = new ArrayList<>();

    private EditText num_hydrant, address_hydrant;
    private Spinner type_network;
    private TextView diameter_network;
    private ImageButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_hydrant);

        init();
        fillPushArray();
    }

    private void init() {
        myDB = new ChoseHydrantDatabase(this);
        pushDB = new ChosePushOfHydrantDatabase(this);

        num_hydrant = (EditText) findViewById(R.id.num_hydrant);
        address_hydrant = (EditText) findViewById(R.id.address_hydrant);
        type_network = (Spinner) findViewById(R.id.type_network);
        diameter_network = (TextView) findViewById(R.id.diameter_network);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id_divis");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                confirmDialog();
                break;
        }
    }

    private void fillPushArray() {
        Cursor cursor = pushDB.readAllData();
        if (cursor.getCount() == 0) { // если БД пустой
            flag = false;
            type_network_array.add("Нет информации");
            spinnerClickListener();
        } else { // если БД не пустой
            flag = true;
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_push_hydrant_array.add(cursor.getString(0));
                type_network_array.add(cursor.getString(1));
                diameter_network_array.add(cursor.getString(2));
                press_network_array.add(cursor.getString(3));
                push_network_array.add(cursor.getString(4));
                spinnerClickListener();
            }
        }
    }

    private void spinnerClickListener() {
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, type_network_array.toArray(new String[type_network_array.size()]));
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        type_network.setAdapter(langAdapter);
        type_network.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (flag) {
                    id_push_hydrant_str = id_push_hydrant_array.get(position);
                    type_network_str = type_network_array.get(position);
                    diameter_network_str = diameter_network_array.get(position);
                    press_network_str = press_network_array.get(position);
                    push_network_str = push_network_array.get(position);

                    diameter_network.setText(diameter_network_str);
                } else {
                    diameter_network.setText("Нет информации");
                    save_btn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить гидрант?");
        builder.setMessage("Вы уверены, что хотите сохранить гидрант?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addHydrantToDB();
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void addHydrantToDB() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(num_hydrant.getText().toString().trim());
        arrayList.add(address_hydrant.getText().toString().trim());
        arrayList.add(id_push_hydrant_str);
        arrayList.add(type_network_str);
        arrayList.add(diameter_network_str);
        arrayList.add(press_network_str);
        arrayList.add(push_network_str);
        arrayList.add(id_divis);

        myDB.addHydrant(arrayList);
    }
}
