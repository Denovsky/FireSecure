package com.example.firesecure.View_and_Presenter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.R;

public class ChoseFireres extends AppCompatActivity implements View.OnClickListener {

    private String id_build;
    private Button CO1, CO2, CO3, CO4, CO5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_fireres);

        init();
    }

    private void init(){
        CO1 = (Button) findViewById(R.id.CO1);
        CO2 = (Button) findViewById(R.id.CO2);
        CO3 = (Button) findViewById(R.id.CO3);
        CO4 = (Button) findViewById(R.id.CO4);
        CO5 = (Button) findViewById(R.id.CO5);

        CO1.setOnClickListener(this);
        CO2.setOnClickListener(this);
        CO3.setOnClickListener(this);
        CO4.setOnClickListener(this);
        CO5.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id_build");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.CO1:
                intent = new Intent(this, AddCO.class);
                intent.putExtra("id_CO", "1");
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.CO2:
                intent = new Intent(this, AddCO.class);
                intent.putExtra("id_CO", "2");
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.CO3:
                intent = new Intent(this, AddCO.class);
                intent.putExtra("id_CO", "3");
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.CO4:
                intent = new Intent(this, AddCO.class);
                intent.putExtra("id_CO", "4");
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.CO5:
                intent = new Intent(this, AddCO.class);
                intent.putExtra("id_CO", "5");
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
        }
    }
}
