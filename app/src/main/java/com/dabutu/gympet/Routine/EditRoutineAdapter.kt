package com.dabutu.gympet.Routine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class EditRoutineAdapter(
    private var exercises: MutableList<Exercise>
) : RecyclerView.Adapter<EditRoutineAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val instructionsTextView: TextView = itemView.findViewById(R.id.instructionsTextView)
        private val removeExerciseButton: Button = itemView.findViewById(R.id.removeExerciseButton)

        fun bind(exercise: Exercise) {
            nameTextView.text = exercise.name
            instructionsTextView.text = exercise.instructions

            removeExerciseButton.setOnClickListener {
                exercises.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}
