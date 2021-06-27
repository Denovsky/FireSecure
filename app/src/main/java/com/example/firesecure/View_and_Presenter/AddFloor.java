package com.example.firesecure.View_and_Presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddFloor extends AppCompatActivity implements View.OnClickListener {

    private String id_build;
    private ChoseFloorDatabase myDB;

    private String strokeUriPath;
    private TextView header;
    private EditText num_floor, status_floor, entry_num, length_lever, area_size_floor;
    private Button add_plan;
    private ImageButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_floor);

        init();
    }
    private void init() {
        myDB = new ChoseFloorDatabase(AddFloor.this);

        num_floor = (EditText) findViewById(R.id.num_floor);
        status_floor = (EditText) findViewById(R.id.status_floor);
        entry_num = (EditText) findViewById(R.id.entry_num);
        length_lever = (EditText) findViewById(R.id.length_lever);
        area_size_floor = (EditText) findViewById(R.id.area_size_floor);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        add_plan = (Button) findViewById(R.id.add_plan);
        add_plan.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id");

        header = (TextView) findViewById(R.id.header);
        FillHeader(id_build);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                confirmDialog();
                break;
            case R.id.add_plan:
                Intent intent = new Intent(this, DocumentPlanFloor.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        strokeUriPath = data.getStringExtra("Uri");
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить этаж?");
        builder.setMessage("Вы уверены, что хотите сохранить этаж?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addFloorToDB();
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

    public void addFloorToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(num_floor.getText().toString().trim());
        arrayList.add(status_floor.getText().toString().trim());
        arrayList.add(entry_num.getText().toString().trim());
        arrayList.add(length_lever.getText().toString().trim());
        arrayList.add(area_size_floor.getText().toString().trim());
        arrayList.add(id_build);
        arrayList.add(strokeUriPath);

        myDB.addFloor(arrayList);
    }

    private void FillHeader(String id) {
        ChoseBuildingDatabase myBuildings = new ChoseBuildingDatabase(AddFloor.this);
        Cursor cursor = myBuildings.getData(id);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myBuildings.NAME_BUILDING));
            header.setText("Добавить этаж на " + name);
        }
    }
}
