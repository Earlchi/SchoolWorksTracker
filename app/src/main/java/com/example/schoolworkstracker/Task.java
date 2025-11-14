package com.example.schoolworkstracker;

public class Task {
    private String title;
    private String date;
    private String priority;
    private String intensity;
    private boolean isDone; // ✅ New field

    public Task(String title, String date, String priority, String intensity) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.intensity = intensity;
        this.isDone = false;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getPriority() { return priority; }
    public String getIntensity() { return intensity; }
    public boolean isDone() { return isDone; }

    public void setDone(boolean done) {
        this.isDone = done;
    }
}
