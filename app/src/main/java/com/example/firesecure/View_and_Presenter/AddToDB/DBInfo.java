package com.example.firesecure.View_and_Presenter.AddToDB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firesecure.Model.Server;
import com.example.firesecure.R;

import java.util.Objects;

public class DBInfo extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Activity activity;
    public TextView command,
                status_connect;
    public Button turn_on_socket;
    private Server server;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // делаем полноэкранное
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_database);

        init();
    }

    private void init() {
        command = (TextView) findViewById(R.id.command);
        status_connect = (TextView) findViewById(R.id.status_connect);
        turn_on_socket = (Button) findViewById(R.id.turn_on_socket);
        turn_on_socket.setOnClickListener(this);

        context = this;
        activity = DBInfo.this;

        server = new Server(activity, context);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_on_socket:
                if (!server.getFlag()) {
                    server.openServer();
                } else if (server.getFlag()){
                    server.closeServer();
                }
                break;
        }
    }
}
