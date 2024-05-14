package com.dabutu.gympet.Routine;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.dabutu.gympet.R;

import java.util.ArrayList;

public class RoutineActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoutineAdapter adapter;
    private ArrayList<Routine> routineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        routineList = new ArrayList<>();
        adapter = new RoutineAdapter(routineList);
        recyclerView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add a new routine (dummy data for now)
                routineList.add(new Routine("New Routine", 0));
                adapter.notifyDataSetChanged();
            }
        });

        Button exercisesButton = findViewById(R.id.exercisesButton);
        exercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Future functionality
            }
        });
    }
}
