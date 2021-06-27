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

import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.Model.ChoseRoomDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddRoom extends AppCompatActivity implements View.OnClickListener {

    private ChoseRoomDatabase myDB;
    private String id_floor, id_build;
    private String strokeUriPath;

    private TextView header;
    private EditText num_room,
            name_room,
            status_room,
            length_sleeve,
            state_num_people_in,
            approx_num_people_in,
            room_with_ahs,
            room_with_sanitary,
            rv_dm_vv_rtt,
            num_entry,
            area_size_room;
    private Button add_plan;
    private ImageButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_room);

        init();
    }

    private void init() {
        myDB = new ChoseRoomDatabase(this);

        header = (TextView) findViewById(R.id.header);

        num_room = (EditText) findViewById(R.id.num_room);
        name_room = (EditText) findViewById(R.id.name_room);
        status_room = (EditText) findViewById(R.id.status_room);
        length_sleeve = (EditText) findViewById(R.id.length_sleeve);
        state_num_people_in = (EditText) findViewById(R.id.state_num_people_in);
        approx_num_people_in = (EditText) findViewById(R.id.approx_num_people_in);
        room_with_ahs = (EditText) findViewById(R.id.room_with_ahs);
        room_with_sanitary = (EditText) findViewById(R.id.room_with_sanitary);
        rv_dm_vv_rtt = (EditText) findViewById(R.id.rv_dm_vv_rtt);
        num_entry = (EditText) findViewById(R.id.num_entry);
        area_size_room = (EditText) findViewById(R.id.area_size_room);

        add_plan = (Button) findViewById(R.id.add_plan);
        add_plan.setOnClickListener(this);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_floor = extras.getString("id_floor");
        id_build = extras.getString("id_build");

        FillHeader(id_floor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_plan:
                Intent intent = new Intent(this, DocumentPlanRoom.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.save_btn:
                confirmDialog();
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
                addRoomToDB();
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

    public void addRoomToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(num_room.getText().toString().trim());
        arrayList.add(name_room.getText().toString().trim());
        arrayList.add(status_room.getText().toString().trim());
        arrayList.add(length_sleeve.getText().toString().trim());
        arrayList.add(state_num_people_in.getText().toString().trim());
        arrayList.add(approx_num_people_in.getText().toString().trim());
        arrayList.add(room_with_ahs.getText().toString().trim());
        arrayList.add(room_with_sanitary.getText().toString().trim());
        arrayList.add(rv_dm_vv_rtt.getText().toString().trim());
        arrayList.add(num_entry.getText().toString().trim());
        arrayList.add(area_size_room.getText().toString().trim());

        arrayList.add(id_floor);
        arrayList.add(id_build);
        arrayList.add(strokeUriPath);

        myDB.addRoom(arrayList);
    }

    private void FillHeader(String id_floor) {
        ChoseFloorDatabase myFloor = new ChoseFloorDatabase(this);
        Cursor cursor = myFloor.getData(id_floor);
        if (cursor.moveToFirst()) {
            String num = cursor.getString(cursor.getColumnIndexOrThrow(myFloor.NUMBER_FLOOR));
            header.setText("Добавить помещение на этаж №" + num);
        }
    }
}
