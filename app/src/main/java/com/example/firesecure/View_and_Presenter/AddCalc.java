package com.example.firesecure.View_and_Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.R;

import java.util.Timer;
import java.util.TimerTask;

public class AddCalc extends AppCompatActivity {

    // главное инфо
    private Activity activity;
    private Context context;
    private Double spend_main_text,
            num_sleeve_dump_d,
            num_sleeve_secure_d;

    private EditText num_posts_safety,
            num_magistry_line,
            entry_height_magistral_line,
            spend_dumping,
            entry_sum_spend_water;

    private TextView num_fire_tech,
            num_personal,
            limit_distance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.additional_calc);

        init();
        fillInfo();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        spend_main_text = Double.valueOf(extras.getString("spend_main_text"));
        num_sleeve_dump_d = Double.valueOf(extras.getString("num_sleeve_dump_d"));
        num_sleeve_secure_d = Double.valueOf(extras.getString("num_sleeve_secure_d"));

        activity = AddCalc.this;
        context = this;

        num_fire_tech = (TextView) findViewById(R.id.num_fire_tech);
        num_posts_safety = (EditText) findViewById(R.id.num_posts_safety);
        num_magistry_line = (EditText) findViewById(R.id.num_magistry_line);
        num_personal = (TextView) findViewById(R.id.num_personal);
        entry_height_magistral_line = (EditText) findViewById(R.id.entry_height_magistral_line);
        spend_dumping = (EditText) findViewById(R.id.spend_dumping);
        entry_sum_spend_water = (EditText) findViewById(R.id.entry_sum_spend_water);
        limit_distance = (TextView) findViewById(R.id.limit_distance);
    }

    private void fillInfo() {
        Double num_fire_tech_d = (spend_main_text / 32);
        num_fire_tech.setText(String.valueOf(num_fire_tech_d));

        Timer timer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask(activity,
                spend_main_text,
                num_sleeve_dump_d,
                num_sleeve_secure_d);
        timer.schedule(myTimerTask, 2000, 2000);
    }

}

class MyTimerTask extends TimerTask {

    public Activity activity;
    private EditText num_posts_safety,
            num_magistry_line,
            entry_height_magistral_line,
            spend_dumping,
            entry_sum_spend_water;

    private TextView num_fire_tech,
            num_personal,
            limit_distance;

    private Double spend_main_text,
            num_sleeve_dump_d,
            num_sleeve_secure_d;

    public MyTimerTask(Activity activity,
                       Double spend_main_text,
                       Double num_sleeve_dump_d,
                       Double num_sleeve_secure_d) {
        this.activity = activity;
        this.spend_main_text = spend_main_text;
        this.num_sleeve_dump_d = num_sleeve_dump_d;
        this.num_sleeve_secure_d = num_sleeve_secure_d;
        num_fire_tech = (TextView) activity.findViewById(R.id.num_fire_tech);
        num_posts_safety = (EditText) activity.findViewById(R.id.num_posts_safety);
        num_magistry_line = (EditText) activity.findViewById(R.id.num_magistry_line);
        num_personal = (TextView) activity.findViewById(R.id.num_personal);
        entry_height_magistral_line = (EditText) activity.findViewById(R.id.entry_height_magistral_line);
        spend_dumping = (EditText) activity.findViewById(R.id.spend_dumping);
        entry_sum_spend_water = (EditText) activity.findViewById(R.id.entry_sum_spend_water);
        limit_distance = (TextView) activity.findViewById(R.id.limit_distance);
    }

    @Override
    public void run() {
        Log.d("TAGER", "уебу -" + num_posts_safety.getText().toString() + "- чтото");
        Log.d("TAGER", "уебу -" + num_magistry_line.getText().toString() + "- чтото2");
        if (!num_posts_safety.getText().toString().equals("") && !num_magistry_line.getText().toString().equals("") && num_personal.getText().toString().equals("")) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    num_personal.setText(String.valueOf(num_sleeve_dump_d * 2 + num_sleeve_secure_d + Double.valueOf(num_posts_safety.getText().toString().trim()) + Double.valueOf(num_magistry_line.getText().toString().trim())));
                }
            });
        }
        if (!entry_height_magistral_line.getText().toString().equals("") && !spend_dumping.getText().toString().equals("") && !entry_sum_spend_water.getText().toString().equals("") && limit_distance.getText().toString().equals("")) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    limit_distance.setText(String.valueOf(((90 - (70 + Double.valueOf(entry_height_magistral_line.getText().toString()) + Double.valueOf(spend_dumping.getText().toString()))) / (0.015 * (Double.valueOf(entry_sum_spend_water.getText().toString()) * Double.valueOf(entry_sum_spend_water.getText().toString())))) * (20 / 1.2)));
                }
            });
        }
    }
}
