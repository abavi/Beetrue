package com.example.beetrueapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {
    Context context;
    ArrayList<Note> list;

    public NotesAdapter(Context context, ArrayList<Note> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_note_layout, parent, false);
        return new notesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        holder.noteTitle.setText(list.get(position).getNoteTitle());
        holder.noteContent.setText(list.get(position).getNoteContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class notesViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteContent;
        public notesViewHolder(@NonNull View itemView){
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.noteContent);
        }
    }



}


