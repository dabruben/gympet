package com.dabutu.gympet.Routine

import java.io.Serializable

data class Routine(
    val name: String,
    val exerciseCount: Int
) : Serializable {
    var exercises: List<Exercise> = listOf()
}
