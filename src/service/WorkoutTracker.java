package service;

import java.io.FileWriter;
import java.util.ArrayList;
import model.WorkoutSession;

public class WorkoutTracker {

    ArrayList<WorkoutSession> history =
            new ArrayList<>();

    public void addSession(String ex,
                           int min,
                           double cal) {

        history.add(
            new WorkoutSession(ex, min, cal)
        );

        saveSession(ex, min, cal);
    }

    // =====================================
    // SAVE TO tracker.txt
    // =====================================
    private void saveSession(String ex,
                             int min,
                             double cal) {

        try {

            FileWriter fw =
                new FileWriter(
                    "data/tracker.txt", true
                );

            fw.write(
                ex + "," +
                min + "," +
                cal + "\n"
            );

            fw.close();

        } catch (Exception e) {

            System.out.println(
                "Tracker Save Error"
            );
        }
    }

    public int totalMinutes() {

        int total = 0;

        for (WorkoutSession w : history) {
            total += w.minutes;
        }

        return total;
    }

    public double totalCalories() {

        double total = 0;

        for (WorkoutSession w : history) {
            total += w.calories;
        }

        return total;
    }

    public int totalSessions() {
        return history.size();
    }
}