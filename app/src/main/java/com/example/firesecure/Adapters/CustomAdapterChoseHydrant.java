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
import com.example.firesecure.View_and_Presenter.InfoHydrant;

import java.util.ArrayList;

public class CustomAdapterChoseHydrant extends RecyclerView.Adapter<CustomAdapterChoseHydrant.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> id_hydrant = new ArrayList<>();
    private ArrayList<String> num_hydrant = new ArrayList<>();
    private ArrayList<String> address_hydrant = new ArrayList<>();
    private ArrayList<String> type_network = new ArrayList<>();
    private ArrayList<String> diameter_network = new ArrayList<>();
    private ArrayList<String> press_network = new ArrayList<>();
    private ArrayList<String> push_network = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    public CustomAdapterChoseHydrant(Activity activity, Context context,
                                     ArrayList<String> id_hydrant,
                                     ArrayList<String> num_hydrant,
                                     ArrayList<String> address_hydrant,
                                     ArrayList<String> type_network,
                                     ArrayList<String> diameter_network,
                                     ArrayList<String> press_network,
                                     ArrayList<String> push_network,
                                     ArrayList<String> id_division) {
        this.activity = activity;
        this.context = context;

        this.id_hydrant = id_hydrant;
        this.num_hydrant = num_hydrant;
        this.address_hydrant = address_hydrant;
        this.type_network = type_network;
        this.diameter_network = diameter_network;
        this.press_network = press_network;
        this.push_network = push_network;
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
        holder.SPCH_divis.setText("ПГ № " + num_hydrant.get(position) + ", " + address_hydrant.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoHydrant.class);
                intent.putExtra("id_hydrant", String.valueOf(id_hydrant.get(position)));
                intent.putExtra("num", String.valueOf(num_hydrant.get(position)));
                intent.putExtra("address", String.valueOf(address_hydrant.get(position)));
                intent.putExtra("type", String.valueOf(type_network.get(position)));
                intent.putExtra("diameter", String.valueOf(diameter_network.get(position)));
                intent.putExtra("press", String.valueOf(press_network.get(position)));
                intent.putExtra("push", String.valueOf(push_network.get(position)));
                intent.putExtra("id_divis", String.valueOf(id_division.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_hydrant.size();
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
