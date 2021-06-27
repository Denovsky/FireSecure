package com.example.firesecure.View_and_Presenter;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseDataDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddData extends AppCompatActivity implements View.OnClickListener{

    private String id_build;
    private EditText objects_and_materials, intensity_water, linear_speed_of_fire;
    private ImageButton save_btn;
    private ChoseDataDatabase myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_data);

        init();
    }
    private void init() {
        myDB = new ChoseDataDatabase(AddData.this);

        objects_and_materials = (EditText) findViewById(R.id.objects_and_materials);
        intensity_water = (EditText) findViewById(R.id.intensity_water);
        linear_speed_of_fire = (EditText) findViewById(R.id.linear_speed_of_fire);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id_build");
    }

    @Override
    public void onClick(View v) {
        confirmDialog();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить объект?");
        builder.setMessage("Вы уверены, что хотите сохранить объект?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addDataToDB();
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

    public void addDataToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(objects_and_materials.getText().toString().trim());
        arrayList.add(intensity_water.getText().toString().trim());
        arrayList.add(linear_speed_of_fire.getText().toString().trim());
        arrayList.add(id_build);

        myDB.addData(arrayList);
    }
}
