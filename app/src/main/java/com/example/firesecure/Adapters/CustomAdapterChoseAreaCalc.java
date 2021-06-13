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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firesecure.R;
import com.example.firesecure.View.CalcArea;
import com.example.firesecure.View.InfoRoom;

import java.util.ArrayList;

public class CustomAdapterChoseAreaCalc extends RecyclerView.Adapter<CustomAdapterChoseAreaCalc.MyViewHolder> {

    private Activity activity;
    private Context context;

    private ArrayList<String> num_room_array = new ArrayList<>();
    private ArrayList<String> name_room_array = new ArrayList<>();
    private ArrayList<String> area_size_room_array = new ArrayList<>();

    private String id_build;
    private CalcArea calcArea = new CalcArea();

    private Double area_size_double = 0.0;
    private Double coefficient_main = 0.0;
    private Double coefficient_double;
    private boolean flag = false;

    private TextView area_size;

    public CustomAdapterChoseAreaCalc(Activity activity, Context context,
                                      ArrayList<String> num_room_array,
                                      ArrayList<String> name_room_array,
                                      ArrayList<String> area_size_room_array,
                                      String id_build) {
        this.activity = activity;
        this.context = context;

        this.num_room_array = num_room_array;
        this.name_room_array = name_room_array;
        this.area_size_room_array = area_size_room_array;
        this.id_build = id_build;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_area_calc, parent, false);
        area_size = activity.findViewById(R.id.area_size);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        CheckBox checkbox_in = holder.checkbox;
        TextView name_room_in = holder.name_room;
        EditText coefficient_in = holder.coefficient;

        name_room_in.setText(name_room_array.get(position) + " " + num_room_array.get(position));
        //Recyclerview onClickListener
        checkbox_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_in.isChecked()) {
                    try {
                        coefficient_double = Double.parseDouble(coefficient_in.getText().toString().trim());
                        Double some = Double.parseDouble(area_size_room_array.get(position));
                        coefficient_main = some * coefficient_double;
                        area_size_double = area_size_double + coefficient_main;
                        calcArea.setMain_area_size(area_size_double);
                        area_size.setText(String.valueOf(area_size_double));
                        flag = true;
                    } catch (Exception e) {
                        Toast.makeText(context, "Ошибка в заполние Б.Д. или коэффицинта", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (flag) {
                        coefficient_double = Double.parseDouble(coefficient_in.getText().toString().trim());
                        Double some = Double.parseDouble(area_size_room_array.get(position));
                        coefficient_main = some * coefficient_double;
                        area_size_double = area_size_double - coefficient_main;
                        calcArea.setMain_area_size(area_size_double);
                        area_size.setText(String.valueOf(area_size_double));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return num_room_array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_room;
        LinearLayout mainLayout;
        CheckBox checkbox;
        EditText coefficient;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_room = itemView.findViewById(R.id.name_room);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            checkbox = itemView.findViewById(R.id.checkbox);
            coefficient = itemView.findViewById(R.id.coefficient);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }
}
