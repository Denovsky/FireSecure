package com.example.firesecure.View;

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
import com.example.firesecure.Model.ChosePushOfHydrantDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddPushHydrant extends AppCompatActivity implements View.OnClickListener{

    private EditText type_network, diameter_network, press_network, push_network;
    private ImageButton save_btn;
    private ChosePushOfHydrantDatabase myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_push_hydrant);

        init();
    }
    private void init() {
        myDB = new ChosePushOfHydrantDatabase(AddPushHydrant.this);

        type_network = (EditText) findViewById(R.id.type_network);
        diameter_network = (EditText) findViewById(R.id.diameter_network);
        press_network = (EditText) findViewById(R.id.press_network);
        push_network = (EditText) findViewById(R.id.push_network);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        confirmDialog();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить справочные данные?");
        builder.setMessage("Вы уверены, что хотите сохранить справочные данные?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addPushHydrantToDB();
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

    public void addPushHydrantToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(type_network.getText().toString().trim());
        arrayList.add(diameter_network.getText().toString().trim());
        arrayList.add(press_network.getText().toString().trim());
        arrayList.add(push_network.getText().toString().trim());

        myDB.addPush(arrayList);
    }
}
