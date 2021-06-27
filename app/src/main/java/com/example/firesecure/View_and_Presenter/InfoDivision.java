package com.example.firesecure.View_and_Presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseDivisionDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoDivision extends AppCompatActivity implements View.OnClickListener {

    private TextView header;
    private ChoseDivisionDatabase myDB;
    private FloatingActionButton del_fub;
    private String divis_id; // принимает id division'a

    private Button construction_but, hydrants_but, waterpool_but, employees_but, tech_but, info_departure_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_division);

        init();
    }

    private void init() {
        header = (TextView) findViewById(R.id.header);
        myDB = new ChoseDivisionDatabase(InfoDivision.this);

        construction_but = (Button) findViewById(R.id.construction_but);
        construction_but.setOnClickListener(this);

        hydrants_but = (Button) findViewById(R.id.hydrants_but);
        hydrants_but.setOnClickListener(this);

        waterpool_but = (Button) findViewById(R.id.waterpool_but);
        waterpool_but.setOnClickListener(this);

        employees_but = (Button) findViewById(R.id.employees_but);
        employees_but.setOnClickListener(this);

        tech_but = (Button) findViewById(R.id.tech_but);
        tech_but.setOnClickListener(this);

        info_departure_btn = (Button) findViewById(R.id.info_departure_btn);
        info_departure_btn.setOnClickListener(this);

        del_fub = (FloatingActionButton) findViewById(R.id.del_fub);
        del_fub.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        divis_id = extras.getString("id");
        getDataOnId(divis_id);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.del_fub:
                confirmDialog();
                break;
            case R.id.construction_but:
                intent = new Intent(this, ChoseBuilding.class);
                intent.putExtra("id", divis_id);
                startActivity(intent);
                break;
            case R.id.hydrants_but:
                intent = new Intent(this, ChoseHydrant.class);
                intent.putExtra("id", divis_id);
                startActivity(intent);
                break;
            case R.id.waterpool_but:
                intent = new Intent(this, ChosePool.class);
                intent.putExtra("id", divis_id);
                startActivity(intent);
                break;
            case R.id.employees_but:
                intent = new Intent(this, ChoseEmployee.class);
                intent.putExtra("id", divis_id);
                startActivity(intent);
                break;
            case R.id.tech_but:
                intent = new Intent(this, ChoseTech.class);
                intent.putExtra("id", divis_id);
                startActivity(intent);
                break;
//            case R.id.info_departure_btn:
//                intent = new Intent(this, );
//                startActivity(intent);
//                break;
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить подразделение?");
        builder.setMessage("Вы уверены, что хотите удалить пожарное подразделение?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteOneDivision(divis_id);
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

    private void getDataOnId(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_DIVISION));
            String depo = cursor.getString(cursor.getColumnIndexOrThrow(myDB.DEPO_DIVISION));
            header.setText(num + " " + depo);
        }
    }
}
