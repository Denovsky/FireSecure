package com.example.firesecure.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.R;
import com.example.firesecure.View.InfoBuilding;
import com.example.firesecure.View.InfoFloor;
import com.example.firesecure.View.InfoRoom;

import java.util.ArrayList;

public class CustomAdapterChoseRoom extends RecyclerView.Adapter<CustomAdapterChoseRoom.MyViewHolder> {

    private Activity activity;
    private Context context;

    private ArrayList<String> id_room = new ArrayList<>();
    private ArrayList<String> num_room = new ArrayList<>();
    private ArrayList<String> name_room = new ArrayList<>();
    private ArrayList<String> status_room = new ArrayList<>();
    private ArrayList<String> length_sleeve = new ArrayList<>();
    private ArrayList<String> state_num_people_in = new ArrayList<>();
    private ArrayList<String> approx_num_people_in = new ArrayList<>();
    private ArrayList<String> room_with_ahs = new ArrayList<>();
    private ArrayList<String> room_with_sanitary = new ArrayList<>();
    private ArrayList<String> rv_dm_vv_rtt = new ArrayList<>();
    private ArrayList<String> num_entry = new ArrayList<>();
    private ArrayList<String> area_size_room = new ArrayList<>();
    private ArrayList<String> id_floor_array = new ArrayList<>();

    public CustomAdapterChoseRoom(Activity activity, Context context,
                                  ArrayList<String> id_room,
                                  ArrayList<String> num_room,
                                  ArrayList<String> name_room,
                                  ArrayList<String> status_room,
                                  ArrayList<String> length_sleeve,
                                  ArrayList<String> state_num_people_in,
                                  ArrayList<String> approx_num_people_in,
                                  ArrayList<String> room_with_ahs,
                                  ArrayList<String> room_with_sanitary,
                                  ArrayList<String> rv_dm_vv_rtt,
                                  ArrayList<String> num_entry,
                                  ArrayList<String> area_size_room,
                                  ArrayList<String> id_floor_array) {
        this.activity = activity;
        this.context = context;

        this.id_room = id_room;
        this.num_room = num_room;
        this.name_room = name_room;
        this.status_room = status_room;
        this.length_sleeve = length_sleeve;
        this.state_num_people_in = state_num_people_in;
        this.approx_num_people_in = approx_num_people_in;
        this.room_with_ahs = room_with_ahs;
        this.room_with_sanitary = room_with_sanitary;
        this.rv_dm_vv_rtt = rv_dm_vv_rtt;
        this.num_entry = num_entry;
        this.area_size_room = area_size_room;
        this.id_floor_array = id_floor_array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.SPCH_divis.setText("Помещение №" + num_room.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoRoom.class);
                intent.putExtra("id_room", String.valueOf(id_room.get(position)));
                intent.putExtra("num_room", String.valueOf(num_room.get(position)));
                intent.putExtra("name_room", String.valueOf(name_room.get(position)));
                intent.putExtra("status_room", String.valueOf(status_room.get(position)));
                intent.putExtra("length_sleeve", String.valueOf(length_sleeve.get(position)));
                intent.putExtra("state_num_people_in", String.valueOf(state_num_people_in.get(position)));
                intent.putExtra("approx_num_people_in", String.valueOf(approx_num_people_in.get(position)));
                intent.putExtra("room_with_ahs", String.valueOf(room_with_ahs.get(position)));
                intent.putExtra("room_with_sanitary", String.valueOf(room_with_sanitary.get(position)));
                intent.putExtra("rv_dm_vv_rtt", String.valueOf(rv_dm_vv_rtt.get(position)));
                intent.putExtra("num_entry", String.valueOf(num_entry.get(position)));
                intent.putExtra("area_size_room", String.valueOf(area_size_room.get(position)));
                intent.putExtra("id_floor", String.valueOf(id_floor_array.get(position)));

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_room.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView SPCH_divis;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SPCH_divis = itemView.findViewById(R.id.SPCH_divis);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }
}
