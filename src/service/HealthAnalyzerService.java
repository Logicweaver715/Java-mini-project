package service;

import model.UserProfile;

public class HealthAnalyzerService {

    public double getBMI(UserProfile u){
        return u.weight / ((u.height/100)*(u.height/100));
    }

    public String sugarRisk(UserProfile u){

        if(u.sugar > 140)
            return "High Diabetes Risk";

        return "Normal";
    }

    public String bpRisk(UserProfile u){

        if(u.bp > 140)
            return "High BP Risk";

        return "Normal";
    }

    public int healthScore(UserProfile u){

        int score = 100;

        if(u.bp > 140) score -= 20;
        if(u.sugar > 140) score -= 20;
        if(getBMI(u) > 30) score -= 20;
        if(u.sleepHours < 6) score -= 10;

        return score;
    }
}