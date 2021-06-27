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
import com.example.firesecure.View_and_Presenter.InfoPool;

import java.util.ArrayList;

public class CustomAdapterChosePool extends RecyclerView.Adapter<CustomAdapterChosePool.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> id_pool = new ArrayList<>();
    private ArrayList<String> num_pool = new ArrayList<>();
    private ArrayList<String> address_pool = new ArrayList<>();
    private ArrayList<String> size_pool = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    public CustomAdapterChosePool(Activity activity, Context context,
                                  ArrayList<String> id_pool,
                                  ArrayList<String> num_pool,
                                  ArrayList<String> address_pool,
                                  ArrayList<String> size_pool,
                                  ArrayList<String> id_division) {
        this.activity = activity;
        this.context = context;

        this.id_pool = id_pool;
        this.num_pool = num_pool;
        this.address_pool = address_pool;
        this.size_pool = size_pool;
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
        holder.SPCH_divis.setText("П.В. № " + num_pool.get(position) + ", " + address_pool.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoPool.class);
                intent.putExtra("id_pool", String.valueOf(id_pool.get(position)));
                intent.putExtra("num", String.valueOf(num_pool.get(position)));
                intent.putExtra("address", String.valueOf(address_pool.get(position)));
                intent.putExtra("size", String.valueOf(size_pool.get(position)));
                intent.putExtra("id_divis", String.valueOf(id_division.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_pool.size();
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
