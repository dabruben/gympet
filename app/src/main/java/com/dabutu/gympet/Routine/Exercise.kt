package com.dabutu.gympet.Routine

import java.io.Serializable

data class Exercise(
    val name: String = "",
    val instructions: String = "",
    val bodyPart: String = ""
) : Serializable
