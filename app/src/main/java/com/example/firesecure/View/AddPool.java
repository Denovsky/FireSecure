package com.example.firesecure.View;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseHydrantDatabase;
import com.example.firesecure.Model.ChosePoolDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddPool extends AppCompatActivity implements View.OnClickListener {

    private String id_divis;
    private ChosePoolDatabase myDB;

    private EditText num_pool, address_pool, size_pool;
    private ImageButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_pool);

        init();
    }
    private void init() {
        myDB = new ChosePoolDatabase(AddPool.this);

        num_pool = (EditText) findViewById(R.id.num_pool);
        address_pool = (EditText) findViewById(R.id.address_pool);
        size_pool = (EditText) findViewById(R.id.size_pool);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id_divis");
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
        builder.setTitle("Сохранить водоём?");
        builder.setMessage("Вы уверены, что хотите сохранить пожарный водоём?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addPoolToDB();
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

    public void addPoolToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(num_pool.getText().toString().trim());
        arrayList.add(address_pool.getText().toString().trim());
        arrayList.add(size_pool.getText().toString().trim());
        arrayList.add(id_divis);

        myDB.addPool(arrayList);
    }
}
