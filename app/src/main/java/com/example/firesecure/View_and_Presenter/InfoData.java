package com.example.firesecure.View_and_Presenter;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseDataDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoData extends AppCompatActivity {

    private ChoseDataDatabase myDB;
    private String id_facility;

    private FloatingActionButton delete_btn;
    private TextView header;
    private TextView objects_and_materials, intensity_water, linear_speed_of_fire;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_data);

        init();
        fillTextViews(id_facility);

        delete_btn = (FloatingActionButton) findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteData(id_facility);
                finish();
            }
        });
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_facility = extras.getString("id_facility");

        myDB = new ChoseDataDatabase(this);

        header = (TextView) findViewById(R.id.header);
        objects_and_materials = (TextView) findViewById(R.id.objects_and_materials);
        intensity_water = (TextView) findViewById(R.id.intensity_water);
        linear_speed_of_fire = (TextView) findViewById(R.id.linear_speed_of_fire);
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String object = cursor.getString(cursor.getColumnIndexOrThrow(myDB.OBJECT_AND_MATERIALS));
            String intensity = cursor.getString(cursor.getColumnIndexOrThrow(myDB.INTENSITY_WATER));
            String speed = cursor.getString(cursor.getColumnIndexOrThrow(myDB.LINEAR_SPEED_OF_FIRE));

            objects_and_materials.setText(object);
            intensity_water.setText(intensity);
            linear_speed_of_fire.setText(speed);

            header.setText(object);
        }
    }
}
