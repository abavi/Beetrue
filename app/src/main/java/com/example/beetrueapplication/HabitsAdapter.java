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

import java.util.ArrayList;

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
        holder.habitDate.setText(list.get(position).getHabitDate());
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HabitModel habit = list.get(getBindingAdapterPosition());
                    Intent i = new Intent(context, EditHabitActivity.class);
                    Toast.makeText(context, habit.habitID, Toast.LENGTH_SHORT).show();
                    i.putExtra("content", habit.habitContent);
                    i.putExtra("date", habit.habitDate);
                    i.putExtra("habitID", habit.habitID);
                    context.startActivity(i);
                }
            });

    }
}
}

