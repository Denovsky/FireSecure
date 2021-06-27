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
import com.example.firesecure.View_and_Presenter.InfoData;

import java.util.ArrayList;

public class CustomAdapterChoseData extends RecyclerView.Adapter<CustomAdapterChoseData.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> id_facility;
    private ArrayList<String> objects_and_materials;
    private ArrayList<String> intensity_water;
    private ArrayList<String> linear_speed_of_fire;
    private ArrayList<String> id_build;

    public CustomAdapterChoseData(Activity activity, Context context,
                                  ArrayList<String> id_facility,
                                  ArrayList<String> objects_and_materials,
                                  ArrayList<String> intensity_water,
                                  ArrayList<String> linear_speed_of_fire,
                                  ArrayList<String> id_build) {
        this.activity = activity;
        this.context = context;

        this.id_facility = id_facility;
        this.objects_and_materials = objects_and_materials;
        this.intensity_water = intensity_water;
        this.linear_speed_of_fire = linear_speed_of_fire;
        this.id_build = id_build;
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
        holder.SPCH_divis.setText(objects_and_materials.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoData.class);
                intent.putExtra("id_facility", String.valueOf(id_facility.get(position)));
                intent.putExtra("objects_and_materials", String.valueOf(objects_and_materials.get(position)));
                intent.putExtra("intensity_water", String.valueOf(intensity_water.get(position)));
                intent.putExtra("linear_speed_of_fire", String.valueOf(linear_speed_of_fire.get(position)));
                intent.putExtra("id_build", String.valueOf(id_build.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_facility.size();
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
