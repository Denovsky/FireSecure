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

import java.util.ArrayList;

public class CustomAdapterChoseFloor extends RecyclerView.Adapter<CustomAdapterChoseFloor.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> id_floor, num_floor, status_floor, entry_num, length_lever, area_size_floor, id_building;

    public CustomAdapterChoseFloor(Activity activity, Context context,
                                   ArrayList<String> id_floor,
                                   ArrayList<String> num_floor,
                                   ArrayList<String> status_floor,
                                   ArrayList<String> entry_num,
                                   ArrayList<String> length_lever,
                                   ArrayList<String> area_size_floor,
                                   ArrayList<String> id_building) {
        this.activity = activity;
        this.context = context;

        this.id_floor = id_floor;
        this.num_floor = num_floor;
        this.status_floor = status_floor;
        this.entry_num = entry_num;
        this.length_lever = length_lever;
        this.area_size_floor = area_size_floor;
        this.id_building = id_building;
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
        holder.SPCH_divis.setText("Этаж №" + num_floor.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoFloor.class);
                intent.putExtra("id_floor", String.valueOf(id_floor.get(position)));
                intent.putExtra("num", String.valueOf(num_floor.get(position)));
                intent.putExtra("status", String.valueOf(status_floor.get(position)));
                intent.putExtra("entry", String.valueOf(entry_num.get(position)));
                intent.putExtra("length_lever", String.valueOf(length_lever.get(position)));
                intent.putExtra("area_size", String.valueOf(area_size_floor.get(position)));
                intent.putExtra("id_building", String.valueOf(id_building.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_floor.size();
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
