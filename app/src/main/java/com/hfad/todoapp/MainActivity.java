package com.hfad.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.todoapp.Adapter.TodoAdapter;
import com.hfad.todoapp.Model.TodoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;
    private FloatingActionButton fab;

    private TodoAdapter todoAdapter;
    private List<TodoModel> tasksList;
    private boolean isDarkModeOn;
    private  SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = new ArrayList<>();

        if (savedInstanceState != null){
            tasksList = savedInstanceState.getParcelableArrayList("tasksList");
        }

//      Retrieve Mode Saved
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

//      Initiate Recycler View
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//      Set adapter
        todoAdapter = new TodoAdapter(this);
        tasksRecyclerView.setAdapter(todoAdapter);

//      Add an dummy instance of TodoModel class in the list
        if (tasksList.isEmpty()){
            TodoModel dummyTask = new TodoModel(true, "My first task (dummy)");
            tasksList.add(dummyTask);
        }

//      New Task Pop Up Input Window event listener
        fab = findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View newTaskView = inflater.inflate(R.layout.task_input_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);

                alertDialogBuilder.setView(newTaskView);

                final EditText taskInput = (EditText) newTaskView
                        .findViewById(R.id.newTaskText);

                // add new task to the tasks list and it on the recyclerview
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        TodoModel newTask = new TodoModel(false, taskInput.getText().toString());
                                        tasksList.add(newTask);
                                        Collections.sort(tasksList, Comparator.comparing(TodoModel::getId));
                                        Collections.reverse(tasksList);

                                        todoAdapter.setTasksList(tasksList);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show the pop up
                alertDialog.show();
            }
        });

        Collections.sort(tasksList, Comparator.comparing(TodoModel::getId));
        Collections.reverse(tasksList);

        todoAdapter.setTasksList(tasksList);
    }

//  Dark Mode Option Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.darkModeAction:

                if (isDarkModeOn) {
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();
                }
                else {
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);
                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();
                }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle mySavedInstanceState){
        mySavedInstanceState.putParcelableArrayList("tasksList", (ArrayList<? extends Parcelable>) tasksList);
        super.onSaveInstanceState(mySavedInstanceState);
    }
}