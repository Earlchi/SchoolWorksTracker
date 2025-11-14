package com.example.schoolworkstracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecycleBinActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnRestore;
    private RecycleBinAdapter adapter;
    private ArrayList<Task> binList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_bin);

        recyclerView = findViewById(R.id.recyclerViewRecycleBin);
        btnRestore = findViewById(R.id.btnRestore);

        // ✅ show back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binList = RecycleBinHolder.recycleBinList;
        adapter = new RecycleBinAdapter(binList, (task, position) -> {});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnRestore.setOnClickListener(v -> {
            adapter.restoreCheckedTasks();
            Toast.makeText(this, "Selected tasks restored!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
