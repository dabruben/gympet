package com.dabutu.gympet.Routine

import java.io.Serializable

data class ExerciseWithSets(
    val name: String = "",
    val instructions: String = "",
    val bodyPart: String = "",
    var sets: Int = 4 // Default to 4 sets
) : Serializable
