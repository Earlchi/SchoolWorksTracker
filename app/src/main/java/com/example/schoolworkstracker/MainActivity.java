package com.example.schoolworkstracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private EditText etTask, etDate;
    private Spinner spinnerPriority, spinnerIntensity;
    private Button btnAddTask, btnClearCompleted;
    private RecyclerView recyclerViewTasks;
    private FloatingActionButton fabRecycleBin;

    private TaskAdapter taskAdapter;
    public static ArrayList<Task> taskList = new ArrayList<>();
    public static Stack<Task> recycleBinStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.etTask);
        etDate = findViewById(R.id.etDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        spinnerIntensity = findViewById(R.id.spinnerIntensity);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnClearCompleted = findViewById(R.id.btnClearCompleted);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        fabRecycleBin = findViewById(R.id.fabRecycleBin);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> intensityAdapter = ArrayAdapter.createFromResource(this,
                R.array.intensity_array, android.R.layout.simple_spinner_item);
        intensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIntensity.setAdapter(intensityAdapter);

        etDate.setOnClickListener(v -> showDatePickerDialog());

        // ✅ link main list to RecycleBinHolder
        RecycleBinHolder.mainList = taskList;

        taskAdapter = new TaskAdapter(taskList);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(v -> addTask());
        btnClearCompleted.setOnClickListener(v -> clearCompletedTasks());

        fabRecycleBin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecycleBinActivity.class);
            startActivity(intent);
        });
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) ->
                        etDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    private void addTask() {
        String name = etTask.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String priority = spinnerPriority.getSelectedItem().toString();
        String intensity = spinnerIntensity.getSelectedItem().toString();

        if (name.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(name, date, priority, intensity);
        taskList.add(task);
        taskAdapter.notifyDataSetChanged();

        etTask.setText("");
        etDate.setText("");
    }

    // ✅ Only move DONE tasks to recycle bin
    private void clearCompletedTasks() {
        ArrayList<Task> toRemove = new ArrayList<>();
        for (Task task : taskList) {
            if (task.isDone()) {
                RecycleBinHolder.recycleBinList.add(task);
                toRemove.add(task);
            }
        }

        taskList.removeAll(toRemove);
        taskAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Completed tasks moved to Recycle Bin", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.notifyDataSetChanged(); // refresh on restore
    }
}
