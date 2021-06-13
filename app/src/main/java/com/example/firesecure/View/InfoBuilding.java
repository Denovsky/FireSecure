package com.example.firesecure.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoBuilding extends AppCompatActivity implements View.OnClickListener {

    private TextView header;
    private ChoseBuildingDatabase myDB;
    private FloatingActionButton del_fub;
    private String id_build, id_divis; // принимает id division'a

    private Button main_info_building, OTX, main_schema, main_plan, floor_info, places_info, data_but;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_building);

        init();
    }

    private void init() {
        header = (TextView) findViewById(R.id.header);
        myDB = new ChoseBuildingDatabase(InfoBuilding.this);

        main_info_building = (Button) findViewById(R.id.main_info_building);
        main_info_building.setOnClickListener(this);

        OTX = (Button) findViewById(R.id.OTX);
        OTX.setOnClickListener(this);

        main_schema = (Button) findViewById(R.id.main_schema);
        main_schema.setOnClickListener(this);

        main_plan = (Button) findViewById(R.id.main_plan);
        main_plan.setOnClickListener(this);

        floor_info = (Button) findViewById(R.id.floor_info);
        floor_info.setOnClickListener(this);

        places_info = (Button) findViewById(R.id.places_info);
        places_info.setOnClickListener(this);

        data_but = (Button) findViewById(R.id.data_but);
        data_but.setOnClickListener(this);

        del_fub = (FloatingActionButton) findViewById(R.id.del_fub);
        del_fub.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id_build");
        id_divis = extras.getString("id_divis");
        getDataOnId(id_build);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.del_fub:
                confirmDialog();
                break;
            case R.id.main_info_building:
                intent = new Intent(this, PacificInfoBuilding.class);
                intent.putExtra("id", id_build);
                startActivity(intent);
                break;
            case R.id.OTX:
                intent = new Intent(this, OTXInfoBuilding.class);
                intent.putExtra("id", id_build);
                startActivity(intent);
                break;
            case R.id.main_schema:
                intent = new Intent(this, DocumentSchemaBuilding.class);
                intent.putExtra("id", id_build);
                startActivity(intent);
                break;
            case R.id.main_plan:
                intent = new Intent(this, DocumentWayBuilding.class);
                intent.putExtra("id", id_build);
                startActivity(intent);
                break;
            case R.id.floor_info:
                intent = new Intent(this, ChoseFloor.class);
                intent.putExtra("id", id_build);
                intent.putExtra("id_divis", id_divis);
                startActivity(intent);
                break;
            case R.id.places_info:
                intent = new Intent(this, ChoseRoom.class);
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.data_but:
                intent = new Intent(this, ReferenceData.class);
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить сооружение?");
        builder.setMessage("Вы уверены, что хотите удалить сооружение?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteOneBuilding(id_build);
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
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NAME_BUILDING));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(myDB.ADDRESS_BUILDING));
            header.setText(name + ", " + address);
        }
    }
}
