package com.dabutu.gympet.Routine

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R
import com.dabutu.gympet.databinding.ExerciseItemBinding

class ExerciseAdapter(private var exercises: MutableList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    private var originalList: List<Exercise> = exercises.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount() = exercises.size

    fun filter(query: String) {
        exercises = if (query.isEmpty()) {
            originalList.toMutableList()
        } else {
            originalList.filter { it.name.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(private val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.nameEditText.setText(exercise.name)
            binding.nameEditText.isEnabled = false  // Make EditText non-editable

            // To avoid triggering TextWatcher while setting text
            binding.seriesEditText.setText(exercise.series.size.toString())

            binding.seriesEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.toString()?.toIntOrNull()?.let { seriesCount ->
                        if (seriesCount != exercise.series.size) {
                            exercise.series = List(seriesCount) { it + 1 }
                            exercise.reps = MutableList(seriesCount) { if (it < exercise.reps.size) exercise.reps[it] else 12 }
                            notifyDataSetChanged()
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            binding.repsContainer.removeAllViews()
            exercise.reps.forEachIndexed { index, rep ->
                val repView = LayoutInflater.from(binding.root.context).inflate(R.layout.rep_item, binding.repsContainer, false) as EditText
                repView.setText(rep.toString())
                repView.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        s?.toString()?.toIntOrNull()?.let { repsCount ->
                            if (index < exercise.reps.size && repsCount != exercise.reps[index]) {
                                exercise.reps[index] = repsCount
                            }
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
                binding.repsContainer.addView(repView)
            }
        }
    }
}
