package com.hfad.todoapp.Adapter;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todoapp.MainActivity;
import com.hfad.todoapp.Model.TodoModel;
import com.hfad.todoapp.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> tasksList;
    private MainActivity activity;

    public TodoAdapter(MainActivity activity){
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkTodo);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        TodoModel item = tasksList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(item.isStatus());
    }

    public int getItemCount(){
        return tasksList.size();
    }

    public void setTasksList(List<TodoModel> list){
        this.tasksList = list;
        notifyDataSetChanged();
    }
}
