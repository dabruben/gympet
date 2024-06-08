package com.dabutu.gympet.Routine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class TrainingAdapter(private val exercises: List<ExerciseWithSets>) :
    RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_training, parent, false)
        return TrainingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    inner class TrainingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseNameTextView: TextView = itemView.findViewById(R.id.exerciseNameTextView)
        private val setsEditText: EditText = itemView.findViewById(R.id.setsEditText)
        private val updateSetsButton: Button = itemView.findViewById(R.id.updateSetsButton)
        private val setsContainer: ViewGroup = itemView.findViewById(R.id.setsContainer)

        fun bind(exercise: ExerciseWithSets) {
            exerciseNameTextView.text = exercise.name
            setsEditText.setText(exercise.sets.toString()) // Set default number of sets
            setsContainer.removeAllViews()
            addSetsToContainer(exercise.sets)

            updateSetsButton.setOnClickListener {
                val newSets = setsEditText.text.toString().toIntOrNull() ?: exercise.sets
                exercise.sets = newSets
                setsContainer.removeAllViews()
                addSetsToContainer(newSets)
            }
        }

        private fun addSetsToContainer(sets: Int) {
            for (i in 1..sets) {
                val setView = LayoutInflater.from(itemView.context).inflate(R.layout.item_set_training, setsContainer, false)
                setView.tag = "Set${adapterPosition}$i"
                val setNumberTextView: TextView = setView.findViewById(R.id.setNumberTextView)
                val repsEditText: EditText = setView.findViewById(R.id.repsEditText)
                val checkBox: CheckBox = setView.findViewById(R.id.completedCheckBox)

                setNumberTextView.text = "Set $i"
                repsEditText.setText("12") // Default to 12 reps

                setsContainer.addView(setView)
            }
        }
    }
}
