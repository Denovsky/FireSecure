package com.example.firesecure.View;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.firesecure.Model.ChoseBuildingDatabase;
import com.example.firesecure.Model.ChoseFloorDatabase;
import com.example.firesecure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DocumentPlanFloorInfo extends AppCompatActivity {

    public final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 1;
    public final int FILE_SELECT_CODE = 0;
    public final int IMAGE_CODE = 2;

    private ChoseFloorDatabase myDB;
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
        setContentView(R.layout.document_plan_floor_info);

        init();
        AddImageView(id_floor);
    }

    private void init() {
        context = this;
        activity = DocumentPlanFloorInfo.this;
        Bundle extras = getIntent().getExtras();
        id_floor = extras.getString("id_floor");
        myDB = new ChoseFloorDatabase(this);

        header = (TextView) findViewById(R.id.header);
        schema_evacuation = (ImageView) findViewById(R.id.schema_evacuation);
    }

    private void AddImageView(String id) {
        Cursor cursor = myDB.getData(id);
        if (cursor.moveToFirst()) {
            Uri planUri = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(myDB.PLAN_FLOOR)));
            String num = cursor.getString(cursor.getColumnIndexOrThrow(myDB.NUMBER_FLOOR));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                ContentResolver resolver = activity.getContentResolver();
                resolver.takePersistableUriPermission(planUri, takeFlags);
                schema_evacuation.setVisibility(View.VISIBLE);
                schema_evacuation.setImageURI(planUri);
                header.setText("Схема этажа №" + num);
            }
        }
    }
}
