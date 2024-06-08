package com.dabutu.gympet.Routine

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R
import com.google.firebase.firestore.FirebaseFirestore

class RoutineAdapter(
    private val routines: MutableList<Routine>,
    private val context: Context,
    private val startRoutineCallback: (Routine) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_item, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.bind(routine)
    }

    override fun getItemCount(): Int = routines.size

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val routineNameTextView: TextView = itemView.findViewById(R.id.routineNameTextView)
        private val numberOfExercisesTextView: TextView = itemView.findViewById(R.id.numberOfExercisesTextView)
        private val deleteRoutineButton: Button = itemView.findViewById(R.id.deleteRoutineButton)
        private val startRoutineButton: Button = itemView.findViewById(R.id.startRoutineButton)

        init {
            itemView.setOnClickListener(this)
            deleteRoutineButton.setOnClickListener {
                val routine = routines[adapterPosition]
                deleteRoutine(routine)
            }
            startRoutineButton.setOnClickListener {
                val routine = routines[adapterPosition]
                startRoutineCallback(routine)
            }
        }

        fun bind(routine: Routine) {
            routineNameTextView.text = routine.title
            numberOfExercisesTextView.text = routine.exerciseCount.toString()
        }

        override fun onClick(v: View?) {
            val routine = routines[adapterPosition]
            val intent = Intent(context, EditRoutineActivity::class.java).apply {
                putExtra("routineName", routine.title)
            }
            context.startActivity(intent)
        }

        private fun deleteRoutine(routine: Routine) {
            db.collection("routines").whereEqualTo("title", routine.title)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        db.collection("routines").document(document.id).delete()
                    }
                    routines.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, routines.size)
                }
                .addOnFailureListener { e ->
                    // Handle the error
                }
        }
    }
}
