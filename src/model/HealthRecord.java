package model;

public class HealthRecord {
     public String date;
    public double weight;
    public int bp;
    public int sugar;
    public int sleep;
    public int steps;

    public HealthRecord(String date,double weight,int bp,int sugar,int sleep,int steps){
        this.date = date;
        this.weight = weight;
        this.bp = bp;
        this.sugar = sugar;
        this.sleep = sleep;
        this.steps = steps;
    }
}
