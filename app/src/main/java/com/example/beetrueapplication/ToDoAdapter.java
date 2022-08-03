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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.tasksViewHolder> {
    private Context context;
    public ArrayList<TaskModel> list;

    public ToDoAdapter(Context context, ArrayList<TaskModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public tasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_task_layout, parent, false);
        return new tasksViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull tasksViewHolder holder, int position) {
        holder.taskContent.setText(list.get(position).getTaskContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class tasksViewHolder extends RecyclerView.ViewHolder {
        TextView taskContent;
        public tasksViewHolder(@NonNull View itemView){
            super(itemView);
            taskContent = itemView.findViewById(R.id.noteContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskModel task = list.get(getBindingAdapterPosition());
                    Intent i = new Intent(context, EditTaskActivity.class);
                    //Toast.makeText(context, task.taskID, Toast.LENGTH_SHORT).show();
                    i.putExtra("content", task.taskContent);
                    i.putExtra("taskID", task.taskID);
                    context.startActivity(i);
                }
            });
        }
    }


}
