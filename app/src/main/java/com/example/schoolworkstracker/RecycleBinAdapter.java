package com.example.schoolworkstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;

public class RecycleBinAdapter extends RecyclerView.Adapter<RecycleBinAdapter.ViewHolder> {

    private ArrayList<Task> binList;
    private OnTaskRestoreListener listener;
    private ArrayList<Task> selectedTasks = new ArrayList<>();

    public interface OnTaskRestoreListener {
        void onRestore(Task task, int position);
    }

    public RecycleBinAdapter(ArrayList<Task> binList, OnTaskRestoreListener listener) {
        this.binList = binList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = binList.get(position);

        holder.tvTitle.setText(task.getTitle());
        holder.tvDate.setText("Date: " + task.getDate());
        holder.tvPriority.setText("Priority: " + task.getPriority());
        holder.tvIntensity.setText("Intensity: " + task.getIntensity());

        // Use checkbox for selecting tasks to restore
        holder.cbDone.setVisibility(View.VISIBLE);
        holder.cbDone.setText("Select to restore");
        holder.cbDone.setOnCheckedChangeListener(null);
        holder.cbDone.setChecked(selectedTasks.contains(task));

        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) selectedTasks.add(task);
            else selectedTasks.remove(task);
        });
    }

    @Override
    public int getItemCount() {
        return binList.size();
    }

    // ✅ Restores all selected tasks
    public void restoreCheckedTasks() {
        Iterator<Task> iterator = selectedTasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            RecycleBinHolder.restoreTask(task); // restores into main list
            RecycleBinHolder.recycleBinList.remove(task); // remove from bin
            binList.remove(task);
            iterator.remove();
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvPriority, tvIntensity;
        CheckBox cbDone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvIntensity = itemView.findViewById(R.id.tvIntensity);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}
