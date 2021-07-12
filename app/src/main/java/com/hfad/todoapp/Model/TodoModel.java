package com.hfad.todoapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoModel implements Parcelable {
    private int id;
    private static int TASKS_NUMBER = 1;
    private boolean status;
    private String task;

    public TodoModel(Boolean status, String task){
        this.id = TASKS_NUMBER;
        this.status = status;
        this.task = task;

        TASKS_NUMBER++;
    }

    protected TodoModel(Parcel in) {
        id = in.readInt();
        status = in.readByte() != 0;
        task = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(task);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TodoModel> CREATOR = new Creator<TodoModel>() {
        @Override
        public TodoModel createFromParcel(Parcel in) {
            return new TodoModel(in);
        }

        @Override
        public TodoModel[] newArray(int size) {
            return new TodoModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getTasksNumber() {
        return TASKS_NUMBER;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
