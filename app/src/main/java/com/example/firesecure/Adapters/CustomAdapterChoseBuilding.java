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
import com.example.firesecure.View_and_Presenter.InfoBuilding;

import java.util.ArrayList;

public class CustomAdapterChoseBuilding extends RecyclerView.Adapter<CustomAdapterChoseBuilding.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> id_building;
    private ArrayList<String> name_building;
    private ArrayList<String> depo_building;
    private ArrayList<String> address_building;
    private ArrayList<String> id_divis_array;

    public CustomAdapterChoseBuilding(Activity activity, Context context, ArrayList<String> id_building, ArrayList<String> name_building,
                                      ArrayList<String> depo_building, ArrayList<String> address_building, ArrayList<String> id_divis_array) {
        this.activity = activity;
        this.context = context;

        this.id_building = id_building;
        this.name_building = name_building;
        this.depo_building = depo_building;
        this.address_building = address_building;
        this.id_divis_array = id_divis_array;
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
        holder.SPCH_divis.setText(name_building.get(position) + ", " + address_building.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoBuilding.class);
                intent.putExtra("id_build", String.valueOf(id_building.get(position)));
                intent.putExtra("name", String.valueOf(name_building.get(position)));
                intent.putExtra("depo", String.valueOf(depo_building.get(position)));
                intent.putExtra("address", String.valueOf(address_building.get(position)));
                intent.putExtra("id_divis", String.valueOf(id_divis_array.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_building.size();
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
