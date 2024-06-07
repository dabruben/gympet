package com.dabutu.gympet.Routine

import java.io.Serializable

data class Routine(
    val title: String = "",
    val exerciseCount: Int = 0,
    var exercises: List<Exercise> = listOf()
) : Serializable {
    constructor() : this("", 0, listOf())
}
