package com.example.beetrueapplication;

public class TaskModel {

    // Model class for the Task object

    String taskContent, taskID;

    public TaskModel() {
    }

    public TaskModel(String taskContent, String taskID) {
        this.taskContent = taskContent;
        this.taskID = taskID;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
}
