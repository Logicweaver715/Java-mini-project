package model;

public class WorkoutSession {
     public String exercise;
    public int minutes;
    public double calories;

    public WorkoutSession(String exercise,int minutes,double calories){
        this.exercise = exercise;
        this.minutes = minutes;
        this.calories = calories;
    }
}
