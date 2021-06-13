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
import com.example.firesecure.View.InfoHydrant;

import java.util.ArrayList;

public class CustomAdapterChoseEmployee extends RecyclerView.Adapter<CustomAdapterChoseEmployee.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> id_employee = new ArrayList<>();
    private ArrayList<String> employee_name = new ArrayList<>();
    private ArrayList<String> employee_rank = new ArrayList<>();
    private ArrayList<String> start_work_date = new ArrayList<>();
    private ArrayList<String> post = new ArrayList<>();
    private ArrayList<String> sentry_one = new ArrayList<>();
    private ArrayList<String> sentry_two = new ArrayList<>();
    private ArrayList<String> qualification = new ArrayList<>();
    private ArrayList<String> id_division = new ArrayList<>();

    public CustomAdapterChoseEmployee(Activity activity, Context context,
                                      ArrayList<String> id_employee,
                                      ArrayList<String> employee_name,
                                      ArrayList<String> employee_rank,
                                      ArrayList<String> start_work_date,
                                      ArrayList<String> post,
                                      ArrayList<String> sentry_one,
                                      ArrayList<String> sentry_two,
                                      ArrayList<String> qualification,
                                      ArrayList<String> id_division) {
        this.activity = activity;
        this.context = context;

        this.id_employee = id_employee;
        this.employee_name = employee_name;
        this.employee_rank = employee_rank;
        this.start_work_date = start_work_date;
        this.post = post;
        this.sentry_one = sentry_one;
        this.sentry_two = sentry_two;
        this.qualification = qualification;
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
        holder.SPCH_divis.setText(employee_name.get(position) + " - " + post.get(position));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoEmployee.class);
                intent.putExtra("id_employee", String.valueOf(id_employee.get(position)));
                intent.putExtra("name", String.valueOf(employee_name.get(position)));
                intent.putExtra("rank", String.valueOf(employee_rank.get(position)));
                intent.putExtra("start_work_date", String.valueOf(start_work_date.get(position)));
                intent.putExtra("post", String.valueOf(post.get(position)));
                intent.putExtra("sentry_one", String.valueOf(sentry_one.get(position)));
                intent.putExtra("sentry_two", String.valueOf(sentry_two.get(position)));
                intent.putExtra("qualification", String.valueOf(qualification.get(position)));
                intent.putExtra("id_divis", String.valueOf(id_division.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_employee.size();
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
