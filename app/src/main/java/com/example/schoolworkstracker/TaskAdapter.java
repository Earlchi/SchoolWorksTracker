package com.example.schoolworkstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;

    public TaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvDate.setText("Date: " + task.getDate());
        holder.tvPriority.setText("Priority: " + task.getPriority());
        holder.tvIntensity.setText("Intensity: " + task.getIntensity());

        holder.cbDone.setOnCheckedChangeListener(null);
        holder.cbDone.setChecked(task.isDone());
        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked); // ✅ just mark done
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvPriority, tvIntensity;
        CheckBox cbDone;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvIntensity = itemView.findViewById(R.id.tvIntensity);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}
