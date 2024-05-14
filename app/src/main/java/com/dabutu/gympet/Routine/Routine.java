package com.dabutu.gympet.Routine;

public class Routine {
    private String name;
    private int numberOfExercises;

    public Routine(String name, int numberOfExercises) {
        this.name = name;
        this.numberOfExercises = numberOfExercises;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfExercises() {
        return numberOfExercises;
    }
}
