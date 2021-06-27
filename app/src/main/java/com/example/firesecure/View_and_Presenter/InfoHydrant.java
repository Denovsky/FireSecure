package com.example.firesecure.View_and_Presenter;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseHydrantDatabase;
import com.example.firesecure.R;

public class InfoHydrant extends AppCompatActivity {

    private ChoseHydrantDatabase myDB;
    private String id_hydrant;

    private TextView header;
    private TextView num_hydrant, address_hydrant, type_network, diameter_network, press_network, push_network;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_hydrant);

        init();
        fillTextViews(id_hydrant);
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_hydrant = extras.getString("id_hydrant");

        myDB = new ChoseHydrantDatabase(this);

        header = (TextView) findViewById(R.id.header);

        num_hydrant = (TextView) findViewById(R.id.num_hydrant);
        address_hydrant = (TextView) findViewById(R.id.address_hydrant);
        type_network = (TextView) findViewById(R.id.type_network);
        diameter_network = (TextView) findViewById(R.id.diameter_network);
        press_network = (TextView) findViewById(R.id.press_network);
        push_network = (TextView) findViewById(R.id.push_network);
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_HYDRANT));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(myDB.ADDRESS_HYDRANT));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(myDB.TYPE_NETWORK));
            String diameter = cursor.getString(cursor.getColumnIndexOrThrow(myDB.DIAMETER_NETWORK));
            String press = cursor.getString(cursor.getColumnIndexOrThrow(myDB.PRESS_NETWORK));
            String push = cursor.getString(cursor.getColumnIndexOrThrow(myDB.PUSH_NETWORK));

            num_hydrant.setText(num);
            address_hydrant.setText(address);
            type_network.setText(type);
            diameter_network.setText(diameter);
            press_network.setText(press);
            push_network.setText(push);

            header.setText("Пожарный гидрант №" + num + ", " + address);
        }
    }
}
