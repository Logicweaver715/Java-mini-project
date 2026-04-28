package service;

public class GoalService {

    public double progress(double current,double target){

        return (current / target) * 100;
    }

    public int estimateDays(double remain){

        return (int)(remain * 7);
    }
}
