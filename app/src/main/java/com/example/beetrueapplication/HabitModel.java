package com.example.beetrueapplication;

public class HabitModel {
    String habitContent, habitDate, habitID;

    public HabitModel(String habitContent, String habitDate, String habitID) {
        this.habitContent = habitContent;
        this.habitDate = habitDate;
        this.habitID = habitID;
    }

    public HabitModel() {
    }

    public String getHabitContent() {
        return habitContent;
    }

    public void setHabitContent(String habitContent) {
        this.habitContent = habitContent;
    }

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    public String getHabitDate() {
        return habitDate;
    }

    public void setHabitDate(String habitDate) {
        this.habitDate = habitDate;
    }
}
