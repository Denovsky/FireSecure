package com.example.firesecure.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseEmployeeDatabase;
import com.example.firesecure.Model.ChoseHydrantDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_LONG;

public class AddEmployee extends AppCompatActivity implements View.OnClickListener {

    private String id_divis, post_str, work_date, sentry_one_bool = "false", sentry_two_bool = "false";
    private ChoseEmployeeDatabase myDB;
    private String[] posts = {"Пожарный", "Постовой", "Водитель", "Г.Д.З.С."};
    private Context context;

    private EditText employee_name, employee_rank, qualification;
    private Button start_work_date;
    private Spinner post;
    private CheckBox sentry_one, sentry_two;
    private ImageButton save_btn;
    private Calendar dateAndTime = Calendar.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_employee);

        init();

        setInitialDateTime();

    }
    private void init() {
        context = AddEmployee.this;

        myDB = new ChoseEmployeeDatabase(AddEmployee.this);

        employee_name = (EditText) findViewById(R.id.employee_name);
        employee_rank = (EditText) findViewById(R.id.employee_rank);

        start_work_date = (Button) findViewById(R.id.start_work_date);
        start_work_date.setOnClickListener(this);

        post = (Spinner) findViewById(R.id.post);

        qualification = (EditText) findViewById(R.id.qualification);

        sentry_one = (CheckBox) findViewById(R.id.sentry_one);
        sentry_one.setOnClickListener(this);

        sentry_two = (CheckBox) findViewById(R.id.sentry_two);
        sentry_two.setOnClickListener(this);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id_divis");
        spinnerClickListener();
    }

    private void spinnerClickListener(){
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, posts);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        post.setAdapter(langAdapter);
        post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) { // "Пожарный", "Постовой", "Водитель", "Г.Д.З.С."
                    case 0: // Пожарный
                        post_str = "Пожарный";
                        break;
                    case 1: // Постовой
                        post_str = "Постовой";
                        break;
                    case 2: // Водитель
                        post_str = "Водитель";
                        break;
                    case 3: // Г.Д.З.С.
                        post_str = "Г.Д.З.С.";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                confirmDialog();
                break;
            case R.id.start_work_date:
                new DatePickerDialog(AddEmployee.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.sentry_one:
                if (sentry_one.isChecked()){
                    sentry_one_bool = "true";
                } else {
                    sentry_one_bool = "false";
                }
                break;
            case R.id.sentry_two:
                if (sentry_two.isChecked()){
                    sentry_two_bool = "true";
                } else {
                    sentry_one_bool = "false";
                }
                break;
        }
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {

        start_work_date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        work_date = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить работника?");
        builder.setMessage("Вы уверены, что хотите сохранить работника?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addEmployeeToDB();
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

    public void addEmployeeToDB(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(employee_name.getText().toString().trim());
        arrayList.add(employee_rank.getText().toString().trim());
        arrayList.add(work_date);
        arrayList.add(post_str);
        arrayList.add(sentry_one_bool);
        arrayList.add(sentry_two_bool);
        arrayList.add(qualification.getText().toString().trim());
        arrayList.add(id_divis);

        myDB.addEmployee(arrayList);
    }


}
