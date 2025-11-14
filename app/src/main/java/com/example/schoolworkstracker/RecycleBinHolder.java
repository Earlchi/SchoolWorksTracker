package com.example.schoolworkstracker;

import java.util.ArrayList;

public class RecycleBinHolder {
    public static ArrayList<Task> recycleBinList = new ArrayList<>();
    public static ArrayList<Task> mainList;

    public static void restoreTask(Task task) {
        if (mainList != null) {
            mainList.add(task);
        }
    }
}