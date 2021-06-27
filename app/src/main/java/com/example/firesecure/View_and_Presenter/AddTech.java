package com.example.firesecure.View_and_Presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseTechDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddTech extends AppCompatActivity implements View.OnClickListener {

    private ChoseTechDatabase myDB;
    private Context context;
    private String id_divis, mark_str;
    private String[] posts = {"Основная", "Целевая", "Специальная", "Вспомогательная"};

    private TextView header;
    private Spinner mark;
    private EditText type,
                     combat,
                     tank_size,
                     foam_size,
                     num_sleeve_51,
                     num_sleeve_66,
                     num_sleeve_77,
                     num_node_type_A,
                     num_node_type_B,
                     num_node_universal,
                     num_node_portable,
                     num_node_static;
    private FloatingActionButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_tech);

        init();
    }

    private void init() {
        context = AddTech.this;

        myDB = new ChoseTechDatabase(this);

        header = (TextView) findViewById(R.id.header);

        mark = (Spinner) findViewById(R.id.mark);
        type = (EditText) findViewById(R.id.type);
        combat = (EditText) findViewById(R.id.combat);
        tank_size = (EditText) findViewById(R.id.tank_size);
        foam_size = (EditText) findViewById(R.id.foam_size);
        num_sleeve_51 = (EditText) findViewById(R.id.num_sleeve_51);
        num_sleeve_66 = (EditText) findViewById(R.id.num_sleeve_66);
        num_sleeve_77 = (EditText) findViewById(R.id.num_sleeve_77);
        num_node_type_A = (EditText) findViewById(R.id.num_node_type_A);
        num_node_type_B = (EditText) findViewById(R.id.num_node_type_B);
        num_node_universal = (EditText) findViewById(R.id.num_node_universal);
        num_node_portable = (EditText) findViewById(R.id.num_node_portable);
        num_node_static = (EditText) findViewById(R.id.num_node_static);

        save_btn = (FloatingActionButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id_divis");

        spinnerClickListener();
    }

    private void spinnerClickListener(){
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, posts);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        mark.setAdapter(langAdapter);
        mark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) { // "Основная", "Целевая", "Специальная", "Вспомогательная"
                    case 0: // Основная
                        mark_str = "Основная";
                        break;
                    case 1: // Целевая
                        mark_str = "Целевая";
                        break;
                    case 2: // Специальная
                        mark_str = "Специальная";
                        break;
                    case 3: // Вспомогательная
                        mark_str = "Вспомогательная";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                confirmDialog();
                break;
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить технику?");
        builder.setMessage("Вы уверены, что хотите сохранить технику?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addTechToDB();
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

    public void addTechToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(mark_str);
        arrayList.add(type.getText().toString().trim());
        arrayList.add(combat.getText().toString().trim());
        arrayList.add(tank_size.getText().toString().trim());
        arrayList.add(foam_size.getText().toString().trim());
        arrayList.add(num_sleeve_51.getText().toString().trim());
        arrayList.add(num_sleeve_66.getText().toString().trim());
        arrayList.add(num_sleeve_77.getText().toString().trim());
        arrayList.add(num_node_type_A.getText().toString().trim());
        arrayList.add(num_node_type_B.getText().toString().trim());
        arrayList.add(num_node_universal.getText().toString().trim());
        arrayList.add(num_node_portable.getText().toString().trim());
        arrayList.add(num_node_static.getText().toString().trim());
        arrayList.add(id_divis);

        myDB.addTech(arrayList);
    }
}
