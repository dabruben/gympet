package com.dabutu.gympet.Routine

import java.io.Serializable

data class Exercise(
    val name: String,
    val description: String,
    var series: List<Int>,
    var reps: MutableList<Int>
) : Serializable
