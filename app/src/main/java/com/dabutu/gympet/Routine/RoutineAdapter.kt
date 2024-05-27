package com.dabutu.gympet.Routine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class RoutineAdapter(private val routines: List<Routine>) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_item, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.nameTextView.text = routine.name
        holder.exerciseCountTextView.text = "Exercises: ${routine.exerciseCount}"
    }

    override fun getItemCount() = routines.size

    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.routineName)
        val exerciseCountTextView: TextView = itemView.findViewById(R.id.numberOfExercises)
    }
}
