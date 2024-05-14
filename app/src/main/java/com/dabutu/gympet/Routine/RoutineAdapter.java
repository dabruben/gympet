package com.dabutu.gympet.Routine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dabutu.gympet.R;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {
    private ArrayList<Routine> routines;

    public RoutineAdapter(ArrayList<Routine> routines) {
        this.routines = routines;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_item, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine routine = routines.get(position);
        holder.routineName.setText(routine.getName());
        holder.numberOfExercises.setText(String.valueOf(routine.getNumberOfExercises()) + " exercises");
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {
        public TextView routineName;
        public TextView numberOfExercises;

        public RoutineViewHolder(View itemView) {
            super(itemView);
            routineName = itemView.findViewById(R.id.routineName);
            numberOfExercises = itemView.findViewById(R.id.numberOfExercises);
        }
    }
}
