package com.example.firesecure.View_and_Presenter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.Adapters.CustomAdapterChoseEmployee;
import com.example.firesecure.Model.ChoseEmployeeDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoseEmployee extends AppCompatActivity implements View.OnClickListener {

    private String id_divis;
    private boolean flag = false, flag_check = false;
    private ImageView empty_imageview;
    private TextView no_data;
    private ArrayList<String> isChecker = new ArrayList<>();

    private EditText enter_search;
    private FloatingActionButton search_fub, add_fub;
    private CheckBox fireman, guard, driver, gdzs;
    private RecyclerView recyclerView;
    private ChoseEmployeeDatabase myDB;

    private ArrayList<String> id_employee = new ArrayList<>();
    private ArrayList<String> employee_name = new ArrayList<>();
    private ArrayList<String> employee_rank = new ArrayList<>();
    private ArrayList<String> start_work_date = new ArrayList<>();
    private ArrayList<String> post = new ArrayList<>();
    private ArrayList<String> sentry_one = new ArrayList<>();
    private ArrayList<String> sentry_two = new ArrayList<>();
    private ArrayList<String> qualification = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    private ArrayList<String> id_employee_final = new ArrayList<>();
    private ArrayList<String> employee_name_final = new ArrayList<>();
    private ArrayList<String> employee_rank_final = new ArrayList<>();
    private ArrayList<String> start_work_date_final = new ArrayList<>();
    private ArrayList<String> post_final = new ArrayList<>();
    private ArrayList<String> sentry_one_final = new ArrayList<>();
    private ArrayList<String> sentry_two_final = new ArrayList<>();
    private ArrayList<String> qualification_final = new ArrayList<>();
    private ArrayList<String> id_division_final = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chose_employee);

        init();
    }

    private void init() {
        myDB = new ChoseEmployeeDatabase(ChoseEmployee.this);

        enter_search = (EditText) findViewById(R.id.enter_search);

        empty_imageview = (ImageView) findViewById(R.id.empty_imageview);
        no_data = (TextView) findViewById(R.id.no_data);

        search_fub = (FloatingActionButton) findViewById(R.id.search_fub);
        search_fub.setOnClickListener(this);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        fireman = (CheckBox) findViewById(R.id.fireman);
        fireman.setOnClickListener(this);

        guard = (CheckBox) findViewById(R.id.guard);
        guard.setOnClickListener(this);

        driver = (CheckBox) findViewById(R.id.driver);
        driver.setOnClickListener(this);

        gdzs = (CheckBox) findViewById(R.id.gdzs);
        gdzs.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        id_divis = extras.getString("id");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!flag) {
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            clearAllArray();

            storeDataInArrays(); // на выход получаю правильно заполненые final массивы
        }
        flag_check = false;
        flag = false;

        for (int i = 0; i < employee_name_final.size(); i++) {
            Log.d("main", id_employee_final.get(i) + " - id");
            Log.d("main", employee_name_final.get(i) + " - name");
        }

        CustomAdapterChoseEmployee customAdapterChoseEmployee =
                new CustomAdapterChoseEmployee(ChoseEmployee.this, this,
                        id_employee_final,
                        employee_name_final,
                        employee_rank_final,
                        start_work_date_final,
                        post_final,
                        sentry_one_final,
                        sentry_two_final,
                        qualification_final,
                        id_division_final);
        recyclerView.setAdapter(customAdapterChoseEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoseEmployee.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                Intent intent = new Intent(this, AddEmployee.class);
                intent.putExtra("id_divis", id_divis);
                startActivity(intent);
                break;
            case R.id.search_fub:
                searchResult(enter_search.getText().toString().trim());
                break;
            case R.id.fireman:
                if (fireman.isChecked()){
                    isChecker.add("Пожарный");
                } else {
                    isChecker.remove("Пожарный");
                }
                break;
            case R.id.guard:
                if (guard.isChecked()){
                    isChecker.add("Постовой");
                } else {
                    isChecker.remove("Постовой");
                }
                break;
            case R.id.driver:
                if (driver.isChecked()){
                    isChecker.add("Водитель");
                } else {
                    isChecker.remove("Водитель");
                }
                break;
            case R.id.gdzs:
                if (gdzs.isChecked()){
                    isChecker.add("Г.Д.З.С.");
                } else {
                    isChecker.remove("Г.Д.З.С.");
                }
                break;
        }
    }

    private void searchResult(String numFloor) {

        Log.d("ILYA2228", String.valueOf(employee_name_final));

        clearAllArray();

        storeDataInArrays(); // на выход получаю правильно заполненые final массивы

        CheckerOfChecker();

        Log.d("ILYA2228", String.valueOf(isChecker.size()));

        int index = 0;
        ArrayList<String> array = new ArrayList<>();
        if (employee_name_final.contains(numFloor)) {
            index = employee_name_final.indexOf(numFloor);
            flag = true;
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            array.add(id_employee_final.get(index));
            array.add(employee_name_final.get(index));
            array.add(employee_rank_final.get(index));
            array.add(start_work_date_final.get(index));
            array.add(post_final.get(index));
            array.add(sentry_one_final.get(index));
            array.add(sentry_two_final.get(index));
            array.add(qualification_final.get(index));
            array.add(id_division_final.get(index));

            id_employee_final.clear();
            employee_name_final.clear();
            employee_rank_final.clear();
            start_work_date_final.clear();
            post_final.clear();
            sentry_one_final.clear();
            sentry_two_final.clear();
            qualification_final.clear();
            id_division_final.clear();

            id_employee_final.add(array.get(0));
            employee_name_final.add(array.get(1));
            employee_rank_final.add(array.get(2));
            start_work_date_final.add(array.get(3));
            post_final.add(array.get(4));
            sentry_one_final.add(array.get(5));
            sentry_two_final.add(array.get(6));
            qualification_final.add(array.get(7));
            id_division_final.add(array.get(8));

            array.clear();
            Log.d("ILYA2228", "what");

            onResume();

        } else if (numFloor.equals("") && flag_check) {
            Log.d("ILYA2228", "frst var");

            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            onResume();
        } else if (numFloor.equals("") && !flag_check) {
            flag = true;
            Log.d("ILYA2228", "sec var");
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            onResume();
        } else {
            flag = true;
            clearAllArray();
            Log.d("ILYA2228", "thrd var");

            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            onResume();
        }
    }

    private void CheckerOfChecker(){

        Log.d("ILYA2228", String.valueOf(employee_name_final));

        if (isChecker.size() != 0){
            Log.d("ILYA2228", String.valueOf(employee_name_final));

            ArrayList<String> one = new ArrayList<>();
            ArrayList<String> two = new ArrayList<>();
            ArrayList<String> three = new ArrayList<>();
            ArrayList<String> four = new ArrayList<>();
            ArrayList<String> five = new ArrayList<>();
            ArrayList<String> six = new ArrayList<>();
            ArrayList<String> seven = new ArrayList<>();
            ArrayList<String> eight = new ArrayList<>();
            ArrayList<String> nine = new ArrayList<>();

            Log.d("ILYA2228", String.valueOf(isChecker));

            for (int i = 0; i < post_final.size(); i++){
                if (isChecker.contains(post_final.get(i))){
                    one.add(id_employee_final.get(i));
                    two.add(employee_name_final.get(i));
                    three.add(employee_rank_final.get(i));
                    four.add(start_work_date_final.get(i));
                    five.add(post_final.get(i));
                    six.add(sentry_one_final.get(i));
                    seven.add(sentry_two_final.get(i));
                    eight.add(qualification_final.get(i));
                    nine.add(id_division_final.get(i));
                }
            }
            id_employee_final.clear();
            employee_name_final.clear();
            employee_rank_final.clear();
            start_work_date_final.clear();
            post_final.clear();
            sentry_one_final.clear();
            sentry_two_final.clear();
            qualification_final.clear();
            id_division_final.clear();

            id_employee_final = one;
            employee_name_final = two;
            employee_rank_final = three;
            start_work_date_final = four;
            post_final = five;
            sentry_one_final = six;
            sentry_two_final = seven;
            qualification_final = eight;
            id_division_final = nine;

            Log.d("ILYA2228", String.valueOf(id_employee_final));
            Log.d("ILYA2228", String.valueOf(one));

        } else if (isChecker.size() == 0) {
            Log.d("ILYA2228", "nice var");
            flag_check = true;
        }
    }

    private void clearAllArray() {
        id_employee.clear();
        employee_name.clear();
        employee_rank.clear();
        start_work_date.clear();
        post.clear();
        sentry_one.clear();
        sentry_two.clear();
        qualification.clear();
        id_division.clear();

        id_employee_final.clear();
        employee_name_final.clear();
        employee_rank_final.clear();
        start_work_date_final.clear();
        post_final.clear();
        sentry_one_final.clear();
        sentry_two_final.clear();
        qualification_final.clear();
        id_division_final.clear();
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) { // если БД пустой
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else { // если БД не пустой
            while (cursor.moveToNext()) { // заполняю массивы всеми данными из курсора, который берется из БД
                id_employee.add(cursor.getString(0));
                employee_name.add(cursor.getString(1));
                employee_rank.add(cursor.getString(2));
                start_work_date.add(cursor.getString(3));
                post.add(cursor.getString(4));
                sentry_one.add(cursor.getString(5));
                sentry_two.add(cursor.getString(6));
                qualification.add(cursor.getString(7));
                id_division.add(cursor.getString(8));
            }
            checkDivision(); // делаю рабочими конечные массивы
            if (id_employee_final.size() == 0) { // если в пожарном подразделении нету сооружений
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            id_employee.clear();
            employee_name.clear();
            employee_rank.clear();
            start_work_date.clear();
            post.clear();
            sentry_one.clear();
            sentry_two.clear();
            qualification.clear();
            id_division.clear();
        }
    }

    private void checkDivision() {
        for (int i = 0; i < id_division.size(); i++) {
            if (id_divis.equals(id_division.get(i))) {
                id_employee_final.add(id_employee.get(i));
                employee_name_final.add(employee_name.get(i));
                employee_rank_final.add(employee_rank.get(i));
                start_work_date_final.add(start_work_date.get(i));
                post_final.add(post.get(i));
                sentry_one_final.add(sentry_one.get(i));
                sentry_two_final.add(sentry_two.get(i));
                qualification_final.add(qualification.get(i));
                id_division_final.add(id_division.get(i));
            }
        }
    }
}
