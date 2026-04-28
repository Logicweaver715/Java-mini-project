package service;

import model.UserProfile;

public class ReportService {

    public void generateReport(UserProfile user) {

        System.out.println("\n===== FITNESS REPORT =====");

        System.out.println("Name   : " + user.name);
        System.out.println("Goal   : " + user.goal);
        System.out.println("Level  : " + user.level);
        System.out.println("Weight : " + user.weight);
        System.out.println("Steps  : " + user.steps);

        double bmi =
            user.weight /
            ((user.height / 100) * (user.height / 100));

        System.out.println("BMI    : " +
                String.format("%.2f", bmi));

        if (bmi > 30)
            System.out.println("Status : Obese");
        else if (bmi > 25)
            System.out.println("Status : Overweight");
        else
            System.out.println("Status : Healthy");
    }
}