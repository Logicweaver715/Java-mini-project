package service;

import model.UserProfile;

public class RecommendationService {

    public String recommend(UserProfile u){

        double bmi = u.weight / ((u.height/100) * (u.height/100));

        if(u.bp > 140)
            return "Yoga, Walking";

        if(u.sugar > 140)
            return "Walking after meals";

        if(bmi > 30)
            return "Cardio + Diet Control";

        if(u.goal.equalsIgnoreCase("Fat Loss"))
            return "Walking, HIIT, Cycling";

        if(u.goal.equalsIgnoreCase("Muscle Gain"))
            return "Pushups, Pullups, Squats";

        return "General Fitness Exercises";
    }
}