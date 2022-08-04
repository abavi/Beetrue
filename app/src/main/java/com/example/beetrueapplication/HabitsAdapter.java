package com.example.beetrueapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.habitsViewHolder> {
    private Context context;
    public ArrayList<HabitModel> list;

    public HabitsAdapter(Context context, ArrayList<HabitModel> list){
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public habitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_habit_layout, parent, false);
        return new habitsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull habitsViewHolder holder, int position) {
        holder.habitContent.setText(list.get(position).getHabitContent());
        holder.habitDate.setText("From: " + list.get(position).getHabitDate());
        holder.totalDays.setText(calculateDays(list.get(position).getHabitDate()) + " Days"); // Total days displayed in card form
    }

    // Calculate total days of habit
    public String calculateDays(String habitDate) {

        Date convertedHabitDate = null;
        try {
            convertedHabitDate = new SimpleDateFormat("dd/MM/yyyy").parse(habitDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(TimeUnit.DAYS.convert(new Date().getTime() - convertedHabitDate.getTime(), TimeUnit.MILLISECONDS));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class habitsViewHolder extends RecyclerView.ViewHolder {
        TextView habitContent, habitDate, totalDays;
        public habitsViewHolder(@NonNull View itemView){
            super(itemView);
            habitContent = itemView.findViewById(R.id.habitContent);
            habitDate = itemView.findViewById(R.id.fromDate);
            totalDays = itemView.findViewById(R.id.totalDays);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // On click functionality for each element in the recyclerView
                    HabitModel habit = list.get(getBindingAdapterPosition());
                    Intent i = new Intent(context, EditHabitActivity.class);
                    //Toast.makeText(context, habit.habitID, Toast.LENGTH_SHORT).show();
                    i.putExtra("content", habit.habitContent);
                    i.putExtra("date", habit.habitDate);
                    i.putExtra("habitID", habit.habitID);
                    context.startActivity(i);
                }
            });

    }
}
}

