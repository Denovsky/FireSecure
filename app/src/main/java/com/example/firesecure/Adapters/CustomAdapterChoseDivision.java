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
import com.example.firesecure.View.InfoDivision;

import java.util.ArrayList;

public class CustomAdapterChoseDivision extends RecyclerView.Adapter<CustomAdapterChoseDivision.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id_divis, num_divis, depo_divis, town_divis;

    public CustomAdapterChoseDivision(Activity activity, Context context, ArrayList id_divis, ArrayList num_divis,
                               ArrayList depo_divis, ArrayList town_divis) {
        this.activity = activity;
        this.context = context;
        this.id_divis = id_divis;
        this.num_divis = num_divis;
        this.depo_divis = depo_divis;
        this.town_divis = town_divis;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_for_chose_division, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.SPCH_divis.setText(String.valueOf(num_divis.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDivision.class);
                intent.putExtra("id", String.valueOf(id_divis.get(position)));
                intent.putExtra("num", String.valueOf(num_divis.get(position)));
                intent.putExtra("depo", String.valueOf(depo_divis.get(position)));
                intent.putExtra("town", String.valueOf(town_divis.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_divis.size();
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
