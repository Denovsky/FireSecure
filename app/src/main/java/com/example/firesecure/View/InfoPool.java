package com.example.firesecure.View;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseHydrantDatabase;
import com.example.firesecure.Model.ChosePoolDatabase;
import com.example.firesecure.R;

public class InfoPool extends AppCompatActivity {

    private ChosePoolDatabase myDB;
    private String id_pool;

    private TextView header;
    private TextView num_pool, address_pool, size_pool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_pool);

        init();
        fillTextViews(id_pool);
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_pool = extras.getString("id_pool");

        myDB = new ChosePoolDatabase(this);

        header = (TextView) findViewById(R.id.header);

        num_pool = (TextView) findViewById(R.id.num_pool);
        address_pool = (TextView) findViewById(R.id.address_pool);
        size_pool = (TextView) findViewById(R.id.size_pool);
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_POOL));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(myDB.ADDRESS_POOL));
            String size = cursor.getString(cursor.getColumnIndexOrThrow(myDB.SIZE_POOL));

            num_pool.setText(num);
            address_pool.setText(address);
            size_pool.setText(size);

            header.setText("Пожарный водоём №" + num + ", " + address);
        }
    }
}
