package com.example.firesecure.View_and_Presenter;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseEmployeeDatabase;
import com.example.firesecure.R;

public class InfoEmployee extends AppCompatActivity {

    private ChoseEmployeeDatabase myDB;
    private String id_employee;

    private TextView header;
    private TextView employee_name, employee_rank, start_work_date, post, sentry_one, sentry_two, qualification;
    private LinearLayout sentry_one_lay, sentry_two_lay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_employee);

        init();
        fillTextViews(id_employee);
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        id_employee = extras.getString("id_employee");

        myDB = new ChoseEmployeeDatabase(this);

        header = (TextView) findViewById(R.id.header);

        employee_name = (TextView) findViewById(R.id.employee_name);
        employee_rank = (TextView) findViewById(R.id.employee_rank);
        start_work_date = (TextView) findViewById(R.id.start_work_date);
        post = (TextView) findViewById(R.id.post);
        sentry_one = (TextView) findViewById(R.id.sentry_one);
        sentry_two = (TextView) findViewById(R.id.sentry_two);
        qualification = (TextView) findViewById(R.id.qualification);

        sentry_one_lay = (LinearLayout) findViewById(R.id.sentry_one_lay);
        sentry_two_lay = (LinearLayout) findViewById(R.id.sentry_two_lay);
    }

    private void fillTextViews(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String name_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.EMPLOYEE_NAME));
            String rank_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.EMPLOYEE_RANK));
            String start_work_date_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.START_WORK_DATE));
            String post_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.POST));
            String sentry_one_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.SENTRY_ONE));
            String sentry_two_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.SENTRY_TWO));
            String qualification_str = cursor.getString(cursor.getColumnIndexOrThrow(myDB.QUALIFICATION));

            Toast.makeText(this, sentry_one_str, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, sentry_two_str, Toast.LENGTH_SHORT).show();

            employee_name.setText(name_str);
            employee_rank.setText(rank_str);
            start_work_date.setText(start_work_date_str);
            post.setText(post_str);
            qualification.setText(qualification_str);

            if (sentry_one_str.equals("true")) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                sentry_one_lay.setLayoutParams(lp);
            }
            if (sentry_two_str.equals("true")) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                sentry_two_lay.setLayoutParams(lp);
            }

            header.setText(name_str + " - " + post_str);
        }
    }
}
