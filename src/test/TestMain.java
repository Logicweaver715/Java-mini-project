package test;

import model.*;
import service.*;

public class TestMain {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" HEALTH & FITNESS SYSTEM TEST START ");
        System.out.println("====================================");

        try {

            // =====================================
            // USER PROFILE TEST
            // =====================================
            UserProfile user = new UserProfile(
                    "Arvinth",
                    19,
                    "Male",
                    78.0,
                    172.0,
                    135,
                    110,
                    7,
                    5500,
                    "Fat Loss",
                    "Beginner",
                    "None"
            );

            System.out.println("\nUSER PROFILE CREATED");
            System.out.println("Name   : " + user.name);
            System.out.println("Goal   : " + user.goal);
            System.out.println("Level  : " + user.level);

            // =====================================
            // HEALTH ANALYZER TEST
            // =====================================
            HealthAnalyzerService health =
                    new HealthAnalyzerService();

            System.out.println("\n===== HEALTH REPORT =====");
            System.out.println("BMI         : " + health.getBMI(user));
            System.out.println("BP Risk     : " + health.bpRisk(user));
            System.out.println("Sugar Risk  : " + health.sugarRisk(user));
            System.out.println("HealthScore : " + health.healthScore(user));

            // =====================================
            // DIET SERVICE TEST
            // =====================================
            DietService diet = new DietService();

            System.out.println("\n===== DIET PLAN =====");
            System.out.println("Plan        : " + diet.plan(user));
            System.out.println("Water Need  : " + diet.waterNeed(user) + " ml");

            // =====================================
            // GOAL SERVICE TEST
            // =====================================
            GoalService goal = new GoalService();

            System.out.println("\n===== GOAL TRACKER =====");
            System.out.println("Progress    : " +
                    goal.progress(65, 100) + "%");
            System.out.println("Days Left   : " +
                    goal.estimateDays(5));

            // =====================================
            // RECOMMENDATION TEST
            // =====================================
            RecommendationService rec =
                    new RecommendationService();

            System.out.println("\n===== RECOMMENDATIONS =====");
            System.out.println(rec.recommend(user));

            // =====================================
            // EXERCISE MODEL TEST
            // =====================================
            Exercise ex = new Exercise(
                    "Pushups",
                    "Chest",
                    "Beginner",
                    8.5
            );

            System.out.println("\n===== EXERCISE DATA =====");
            System.out.println("Name        : " + ex.name);
            System.out.println("Body Part   : " + ex.bodyPart);
            System.out.println("Level       : " + ex.level);
            System.out.println("Calories/min: " + ex.caloriesPerMin);

            // =====================================
            // HEALTH RECORD TEST
            // =====================================
            HealthRecord record = new HealthRecord(
                    "23-04-2026",
                    78,
                    135,
                    110,
                    7,
                    5500
            );

            System.out.println("\n===== DAILY RECORD =====");
            System.out.println("Date   : " + record.date);
            System.out.println("Weight : " + record.weight);
            System.out.println("Steps  : " + record.steps);

            // =====================================
            // WORKOUT TRACKER TEST
            // =====================================
            WorkoutTracker tracker =
                    new WorkoutTracker();

            tracker.addSession("Pushups", 15, 120);
            tracker.addSession("Cycling", 30, 300);
            tracker.addSession("Squats", 10, 90);

            System.out.println("\n===== WORKOUT TRACKER =====");
            System.out.println("Sessions : " +
                    tracker.totalSessions());
            System.out.println("Minutes  : " +
                    tracker.totalMinutes());
            System.out.println("Calories : " +
                    tracker.totalCalories());

            // =====================================
            // AUTH SERVICE TEST
            // =====================================
            AuthService auth =
                    new AuthService();

            auth.signup(
                    "arvinth",
                    "1234",
                    "arvinth@gmail.com"
            );

            System.out.println("\n===== LOGIN TEST =====");
            System.out.println(
                    auth.login("arvinth", "1234")
            );

            // =====================================
            // BOOKING SYSTEM TEST
            // =====================================
            BookingService bookingService =
                    new BookingService();

            bookingService.addTrainer(
                    new Trainer(
                            "Ravi Sharma",
                            "Weight Training",
                            1000,
                            java.util.Arrays.asList(
                                    "9AM", "11AM", "4PM"
                            )
                    )
            );

            bookingService.addTrainer(
                    new Trainer(
                            "Ananya Iyer",
                            "Yoga",
                            800,
                            java.util.Arrays.asList(
                                    "7AM", "10AM", "6PM"
                            )
                    )
            );

            System.out.println("\n===== TRAINERS =====");
            bookingService.showTrainers();

            Trainer selected =
                    bookingService.getTrainer(0);

            System.out.println("\n===== BOOKING TEST =====");
            bookingService.bookSlot(selected, "9AM");

            Thread.sleep(1500);

            // =====================================
            // TIMER TEST
            // =====================================
            System.out.println("\n===== TIMER TEST =====");

            TimerService timer =
                    new TimerService(5);

            timer.start();
            timer.join();
            // =====================================
            // SUPPORT SYSTEM TEST
            // =====================================
            SupportService support = new SupportService();

            support.addTicket(new SupportTicket(
            "arvinth", "Login Issue"
            ));

            support.addTicket(new SupportTicket(
            "arvinth", "Camera Not Working"
            ));

            System.out.println("\nPending Tickets: " +
            support.pendingTickets());

            support.resolveNextTicket();
            support.resolveNextTicket();
            // =====================================
            // FINAL
            // =====================================
            System.out.println("\n====================================");
            System.out.println(" ALL MODULES WORKING SUCCESSFULLY ");
            System.out.println("====================================");

        } catch (Exception e) {

            System.out.println("\nERROR FOUND:");
            e.printStackTrace();
        }
    }
}