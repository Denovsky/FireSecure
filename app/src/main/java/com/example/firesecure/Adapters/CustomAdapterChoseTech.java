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
import com.example.firesecure.View.InfoEmployee;
import com.example.firesecure.View.InfoTech;

import java.util.ArrayList;

public class CustomAdapterChoseTech extends RecyclerView.Adapter<CustomAdapterChoseTech.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> id_tech;
    private ArrayList<String> mark_tech;
    private ArrayList<String> type_tech;
    private ArrayList<String> id_division;

    public CustomAdapterChoseTech(Activity activity, Context context,
                                          ArrayList<String> id_tech,
                                          ArrayList<String> mark_tech,
                                          ArrayList<String> type_tech,
                                          ArrayList<String> id_division) {
        this.activity = activity;
        this.context = context;

        this.id_tech = id_tech;
        this.mark_tech = mark_tech;
        this.type_tech = type_tech;
        this.id_division = id_division;
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
        holder.SPCH_divis.setText(type_tech.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoTech.class);
                intent.putExtra("id_tech", String.valueOf(id_tech.get(position)));
                intent.putExtra("mark", String.valueOf(mark_tech.get(position)));
                intent.putExtra("type", String.valueOf(type_tech.get(position)));
                intent.putExtra("id_divis", String.valueOf(id_division.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_tech.size();
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
