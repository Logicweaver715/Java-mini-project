package service;

import model.UserProfile;

public class DietService {

    public String plan(UserProfile u){

        if(u.goal.equals("Fat Loss"))
            return "High Protein + Low Carb";

        if(u.goal.equals("Muscle Gain"))
            return "High Protein + Calorie Surplus";

        if(u.sugar > 140)
            return "Low Sugar Diet";

        return "Balanced Diet";
    }

    public double waterNeed(UserProfile u){
        return u.weight * 35;
    }
}
