package com.example.firesecure.View;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.Model.ChoseRoomDatabase;
import com.example.firesecure.R;

public class DocumentPlanRoomInfo extends AppCompatActivity {

    public final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 1;
    public final int FILE_SELECT_CODE = 0;
    public final int IMAGE_CODE = 2;

    private ChoseRoomDatabase myDB;
    private String strokeUri;
    private String id_floor;

    private Activity activity;
    private Context context;
    private TextView header;
    private ImageView schema_evacuation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.document_plan_room_info);

        init();
        AddImageView(id_floor);
    }

    private void init() {
        context = this;
        activity = DocumentPlanRoomInfo.this;
        Bundle extras = getIntent().getExtras();
        id_floor = extras.getString("id_room");
        myDB = new ChoseRoomDatabase(this);

        header = (TextView) findViewById(R.id.header);
        schema_evacuation = (ImageView) findViewById(R.id.schema_evacuation);
    }

    private void AddImageView(String id) {
        try {
            Cursor cursor = myDB.getData(id);
            if (cursor.moveToFirst()) {
                Uri planUri = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PLAN_ROOM)));
                String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_ROOM));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    ContentResolver resolver = activity.getContentResolver();
                    resolver.takePersistableUriPermission(planUri, takeFlags);
                    schema_evacuation.setVisibility(View.VISIBLE);
                    schema_evacuation.setImageURI(planUri);
                    header.setText("Схема помещения №" + num);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Добавьте изображение", Toast.LENGTH_LONG).show();
        }
    }
}
