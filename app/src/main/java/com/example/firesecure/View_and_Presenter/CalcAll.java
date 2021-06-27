package com.example.firesecure.View_and_Presenter;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.R;

public class CalcAll extends AppCompatActivity {
    // главное инфо
    private Double area_size, intensity;
    private TextView
            need_spend_dumping,
            need_spend_secure,
            need_spend_main,
            num_sleeve_dump,
            num_sleeve_secure,
            spend_dumping,
            spend_secure,
            spend_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calc_all);

        init();
        fillInfo();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        area_size = Double.valueOf(extras.getString("area_size"));
        intensity = Double.valueOf(extras.getString("intensity"));

        need_spend_dumping = (TextView) findViewById(R.id.need_spend_dumping);
        need_spend_secure = (TextView) findViewById(R.id.need_spend_secure);
        need_spend_main = (TextView) findViewById(R.id.need_spend_main);
        num_sleeve_dump = (TextView) findViewById(R.id.num_sleeve_dump);
        num_sleeve_secure = (TextView) findViewById(R.id.num_sleeve_secure);
        spend_dumping = (TextView) findViewById(R.id.spend_dumping);
        spend_secure = (TextView) findViewById(R.id.spend_secure);
        spend_main = (TextView) findViewById(R.id.spend_main);
    }

    private void fillInfo() {
        Double need_spend_dumping_d = area_size * intensity;
        need_spend_dumping.setText(String.valueOf(need_spend_dumping_d));

        Double need_spend_secure_d = area_size * intensity * 0.25;
        need_spend_secure.setText(String.valueOf(need_spend_secure_d));

        Double need_spend_main_d = need_spend_dumping_d + need_spend_secure_d;
        need_spend_main.setText(String.valueOf(need_spend_main_d));

        Double num_sleeve_dump_d = Math.ceil(need_spend_dumping_d / 7.4);
        num_sleeve_dump.setText(String.valueOf(num_sleeve_dump_d));

        Double num_sleeve_secure_d = Math.ceil(need_spend_secure_d / 3.7);
        num_sleeve_secure.setText(String.valueOf(num_sleeve_secure_d));

        Double spend_dumping_d = num_sleeve_dump_d * 7.4;
        spend_dumping.setText(String.valueOf(spend_dumping_d));

        Double spend_secure_d = num_sleeve_secure_d * 3.7;
        spend_secure.setText(String.valueOf(spend_secure_d));

        Double spend_main_d = spend_dumping_d + spend_secure_d;
        spend_main.setText(String.valueOf(spend_main_d));
    }
}
