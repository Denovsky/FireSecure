package com.example.firesecure.View_and_Presenter;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Adapters.CreatingTable;
import com.example.firesecure.Model.ChosePushOfHydrantDatabase;
import com.example.firesecure.R;

import java.util.ArrayList;

public class AddPushHydrant extends AppCompatActivity implements View.OnClickListener {

    private Spinner type_network, diameter_network, press_network;
    private ImageButton save_btn;
    private TextView push_network;
    private ChosePushOfHydrantDatabase myDB;

    private int index, diametr, press, push = 0;

    private ArrayList<String> type_net = new ArrayList<>();
    private ArrayList<String> diameter_net = new ArrayList<>();
    private ArrayList<String> press_net = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_push_hydrant);

        init();
        fillArrays();
        spinnersClickListener();
    }

    private void init() {
        myDB = new ChosePushOfHydrantDatabase(AddPushHydrant.this);

        type_network = (Spinner) findViewById(R.id.type_network);
        diameter_network = (Spinner) findViewById(R.id.diameter_network);
        press_network = (Spinner) findViewById(R.id.press_network);
        push_network = (TextView) findViewById(R.id.push_network);

        save_btn = (ImageButton) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        index = 0;
        diametr = 100;
        press = 10;
    }

    private void fillArrays() {
        type_net.add("Тупиковая");
        type_net.add("Кольцевая");
        diameter_net.add("100");
        diameter_net.add("125");
        diameter_net.add("150");
        diameter_net.add("200");
        diameter_net.add("250");
        diameter_net.add("300");
        diameter_net.add("350");
        press_net.add("10");
        press_net.add("20");
        press_net.add("30");
        press_net.add("40");
        press_net.add("50");
        press_net.add("60");
        press_net.add("70");
        press_net.add("80");
    }

    private void spinnersClickListener() {
        ArrayAdapter<CharSequence> langAdapterOne = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, type_net.toArray(new String[type_net.size()]));
        langAdapterOne.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        type_network.setAdapter(langAdapterOne);
        type_network.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                index = position;
                CreatingTable creatingTable = new CreatingTable(index, diametr, press);
                push = creatingTable.getPush();
                push_network.setText(Integer.toString(push));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ArrayAdapter<CharSequence> langAdapterTwo = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, diameter_net.toArray(new String[diameter_net.size()]));
        langAdapterTwo.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        diameter_network.setAdapter(langAdapterTwo);
        diameter_network.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                diametr = Integer.parseInt(diameter_net.get(position));
                CreatingTable creatingTable = new CreatingTable(index, diametr, press);
                push = creatingTable.getPush();
                push_network.setText(Integer.toString(push));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ArrayAdapter<CharSequence> langAdapterThree = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, press_net.toArray(new String[press_net.size()]));
        langAdapterThree.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        press_network.setAdapter(langAdapterThree);
        press_network.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                press = Integer.parseInt(press_net.get(position));
                CreatingTable creatingTable = new CreatingTable(index, diametr, press);
                push = creatingTable.getPush();
                push_network.setText(Integer.toString(push));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
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

    public void addPushHydrantToDB() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(Integer.toString(index));
        arrayList.add(Integer.toString(diametr));
        arrayList.add(Integer.toString(press));
        arrayList.add(Integer.toString(push));

        myDB.addPush(arrayList);
    }
}
