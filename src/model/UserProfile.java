package model;

public class UserProfile {

    public String name;
    public int age;
    public String gender;
    public double weight;
    public double height;
    public int bp;
    public int sugar;
    public int sleepHours;
    public int steps;
    public String goal;
    public String level;
    public String birthDefects;

    public UserProfile(String name,int age,String gender,double weight,double height,
                       int bp,int sugar,int sleepHours,int steps,
                       String goal,String level,String birthDefects){

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bp = bp;
        this.sugar = sugar;
        this.sleepHours = sleepHours;
        this.steps = steps;
        this.goal = goal;
        this.level = level;
        this.birthDefects = birthDefects;
    }
}
