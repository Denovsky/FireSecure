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

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.R;

public class AddBuilding extends AppCompatActivity implements View.OnClickListener{

    private String id_divis;
    private EditText name_building, own_of_building, address_building;
    private ImageButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_building);

        init();
    }
    private void init() {
        name_building = (EditText) findViewById(R.id.name_building);
        own_of_building = (EditText) findViewById(R.id.own_of_building);
        address_building = (EditText) findViewById(R.id.address_building);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id");
    }

    @Override
    public void onClick(View v) {
        confirmDialog();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить подразделение?");
        builder.setMessage("Вы уверены, что хотите сохранить пожарное подразделение?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addMainInfoBuildingToDB();
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

    public void addMainInfoBuildingToDB(){
        ChoseBuildingDatabase myDB = new ChoseBuildingDatabase(AddBuilding.this);
        myDB.addMainBuildingInfo(name_building.getText().toString().trim(),
                own_of_building.getText().toString().trim(),
                address_building.getText().toString().trim(),
                id_divis);
    }
}
