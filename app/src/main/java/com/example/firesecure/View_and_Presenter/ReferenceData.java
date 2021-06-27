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

public class ReferenceData extends AppCompatActivity implements View.OnClickListener {

    private String id_build;
    private Button intensity, fireres, waterloss;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.reference_data);

        init();
    }

    private void init(){
        intensity = (Button) findViewById(R.id.intensity);
        fireres = (Button) findViewById(R.id.fireres);
        waterloss = (Button) findViewById(R.id.waterloss);

        intensity.setOnClickListener(this);
        fireres.setOnClickListener(this);
        waterloss.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_build = extras.getString("id_build");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.intensity:
                intent = new Intent(this, ChoseData.class);
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.fireres:
                intent = new Intent(this, ChoseFireres.class);
                intent.putExtra("id_build", id_build);
                startActivity(intent);
                break;
            case R.id.waterloss:
                intent = new Intent(this, InfoPushHydrant.class);
                startActivity(intent);
                break;
        }
    }
}
