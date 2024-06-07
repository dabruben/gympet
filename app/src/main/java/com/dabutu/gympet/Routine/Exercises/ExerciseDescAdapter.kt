package com.dabutu.gympet.Routine.Exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class ExerciseDescAdapter(
    private var exercises: List<ExerciseDesc>,
    private val onDeleteExercise: (ExerciseDesc) -> Unit
) : RecyclerView.Adapter<ExerciseDescAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_description, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    fun updateExercises(newExercises: List<ExerciseDesc>) {
        exercises = newExercises
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseNameTextView: TextView = itemView.findViewById(R.id.exerciseNameTextView)
        private val exerciseDescriptionTextView: TextView = itemView.findViewById(R.id.exerciseDescriptionTextView)
        private val deleteExerciseButton: Button = itemView.findViewById(R.id.deleteExerciseButton)

        fun bind(exercise: ExerciseDesc) {
            exerciseNameTextView.text = exercise.name
            exerciseDescriptionTextView.text = exercise.instructions

            deleteExerciseButton.setOnClickListener {
                onDeleteExercise(exercise)
            }
        }
    }
}
