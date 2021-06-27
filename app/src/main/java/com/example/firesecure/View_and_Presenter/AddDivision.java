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

import com.example.firesecure.Model.ChoseDivisionDatabase;
import com.example.firesecure.R;

public class AddDivision extends AppCompatActivity implements View.OnClickListener{

    private EditText SPCH_divis, SYFP_divis, town_divis;
    private ImageButton save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_division);

        init();
    }
    private void init() {
        SPCH_divis = (EditText) findViewById(R.id.SPCH_divis);
        SYFP_divis = (EditText) findViewById(R.id.SYFP_divis);
        town_divis = (EditText) findViewById(R.id.town_divis);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);
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
                addDivisionToDB();
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

    public void addDivisionToDB(){
        ChoseDivisionDatabase myDB = new ChoseDivisionDatabase(AddDivision.this);
        myDB.addDivision(SPCH_divis.getText().toString().trim(),
                SYFP_divis.getText().toString().trim(),
                town_divis.getText().toString().trim());
    }
}
