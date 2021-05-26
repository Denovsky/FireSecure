package com.example.firesecure.View;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class DocumentSchemaBuilding extends AppCompatActivity implements View.OnClickListener {

    public final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 1;
    public final int FILE_SELECT_CODE = 0;
    public final int IMAGE_CODE = 2;

    private ArrayList<String> arrayUri;

    private ChoseBuildingDatabase myDB;
    private String build_id;
    private String img;

    private Activity activity;
    private Context context;
    private TextView header;
    private ImageView schema_evacuation;
    private FloatingActionButton save_fub, add_fub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.document_schema_building);

        init();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = myDB.getData(build_id);

        if (cursor.moveToFirst()) {
            img = cursor.getString(cursor.getColumnIndexOrThrow(myDB.MAIN_PLAN));
            if (img != null) {
                add_fub.setVisibility(View.GONE);
                save_fub.setVisibility(View.GONE);
                schema_evacuation.setVisibility(View.VISIBLE);
                schema_evacuation.setImageURI(Uri.parse(img));
            } else {
                Toast.makeText(context, "some shit", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        context = this;
        activity = DocumentSchemaBuilding.this;
        myDB = new ChoseBuildingDatabase(DocumentSchemaBuilding.this);

        header = (TextView) findViewById(R.id.header);
        schema_evacuation = (ImageView) findViewById(R.id.schema_evacuation);

        add_fub = (FloatingActionButton) findViewById(R.id.add_fub);
        add_fub.setOnClickListener(this);

        save_fub = (FloatingActionButton) findViewById(R.id.save_fub);
        save_fub.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        build_id = extras.getString("id");
        getDataOnId(build_id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fub:
                int permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION_READ_CONTACTS);
                }
                break;
            case R.id.save_fub:
                myDB.addPacificInfo(arrayUri, build_id, "img");
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // разрешение получено, можно работать
                    showFileChooser();
                } else {
                    Toast.makeText(context, "Чтобы добавить JPEG файл, приложению необходимо дать доступ к памяти телефона!", Toast.LENGTH_SHORT);
                }
        }
        return;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // возвращает интент
        intent.setType("*/*"); // задает каталог в котором появляется пользователь
        intent.addCategory(Intent.CATEGORY_OPENABLE); // задается открытие менеджера (файлового ок да)

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Выберите изображение"), // для андроида 10 < при открытии будет надпись в header
                    FILE_SELECT_CODE);  // открывает менеджер
        } catch (android.content.ActivityNotFoundException ex) { // ошибка открытия менеджера
            Toast.makeText(context, "Пожалуйста загрузите файловый менеджер", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { // ссылка в системе на изображение
        if (requestCode == FILE_SELECT_CODE) {
            assert data != null; // если data не null, то функция работает
            Uri uri = data.getData(); // получает ссылку после выбора
            Log.d("MYURI", "uri " + uri);
            arrayUri = new ArrayList<>();
            arrayUri.add(uri.toString());
            Log.d("MYURI", "texy " + uri);

            try {
                add_fub.setVisibility(View.GONE);
                schema_evacuation.setVisibility(View.VISIBLE);
                schema_evacuation.setImageURI(uri);
            } catch (Exception e) {
                Toast.makeText(context, "Ошибка вывода изображения", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getDataOnId(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NAME_BUILDING));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(myDB.ADDRESS_BUILDING));
            header.setText("Общая схема на местности: " + name + ", " + address);
        }
    }
}
