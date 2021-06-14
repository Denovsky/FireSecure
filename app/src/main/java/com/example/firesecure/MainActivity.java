package com.example.firesecure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.firesecure.View.ChoseCalc;
import com.example.firesecure.View.ChoseDivision;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button entry_but, data_but, calc_but, setting_but;
    private Context context;

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor;

    public static final String APP_PREFERENCES = "mySettings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        init();
        if (sharedPreferences.getAll().size() == 0){
            editor.putBoolean("Flag", true);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.entry_but:
                intent = new Intent(context, ChoseDivision.class);
                startActivity(intent);
                break;
            case R.id.data_but:
                break;
            case R.id.calc_but:
                intent = new Intent(context, ChoseCalc.class);
                startActivity(intent);
                break;
            case R.id.setting_but:
                break;
        }
    }

    public void init() {
        context = this.getApplicationContext();

        entry_but = (Button) findViewById(R.id.entry_but);
        data_but = (Button) findViewById(R.id.data_but);
        calc_but = (Button) findViewById(R.id.calc_but);
        setting_but = (Button) findViewById(R.id.setting_but);

        entry_but.setOnClickListener(this);
        data_but.setOnClickListener(this);
        calc_but.setOnClickListener(this);
        setting_but.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
}