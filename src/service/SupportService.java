package service;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import model.SupportTicket;

public class SupportService {

    private Queue<SupportTicket> tickets =
            new LinkedList<>();

    private HashMap<String, String> solutions =
            new HashMap<>();

    public SupportService() {

        // 12 predefined issues + solutions

        solutions.put("Login Issue",
                "Check username/password or reset password.");

        solutions.put("Slow App",
                "Restart app or clear background processes.");

        solutions.put("Workout Not Saving",
                "Check internet or restart app.");

        solutions.put("Incorrect BMI",
                "Verify height and weight inputs.");

        solutions.put("Crash on Start",
                "Reinstall app or update Java version.");

        solutions.put("Camera Not Working",
                "Check device permissions.");

        solutions.put("Booking Failed",
                "Slot may be taken. Try another time.");

        solutions.put("Data Not Loading",
                "Check file/data connection.");

        solutions.put("Timer Not Working",
                "Restart workout module.");

        solutions.put("UI Not Responsive",
                "Restart app.");

        solutions.put("Wrong Calories",
                "Verify exercise input.");

        solutions.put("Profile Not Saving",
                "Check all required fields.");
    }

    // =====================================
    // ADD TICKET + SAVE TO support.txt
    // =====================================
    public void addTicket(SupportTicket ticket) {

        // auto assign solution
        if (solutions.containsKey(ticket.issue)) {
            ticket.solution =
                    solutions.get(ticket.issue);
        } else {
            ticket.solution =
                    "Support will contact you soon.";
        }

        tickets.add(ticket);

        saveTicket(ticket);
    }

    // =====================================
    // SAVE FILE
    // =====================================
    private void saveTicket(SupportTicket ticket) {

        try {

            FileWriter fw =
                new FileWriter(
                    "data/support.txt", true
                );

            fw.write(
                ticket.username + "," +
                ticket.issue + "," +
                ticket.solution + "," +
                ticket.status + "\n"
            );

            fw.close();

        } catch (Exception e) {

            System.out.println(
                "Support Save Error"
            );
        }
    }

    // =====================================
    // RESOLVE NEXT TICKET
    // =====================================
    public void resolveNextTicket() {

        if (!tickets.isEmpty()) {

            SupportTicket t = tickets.poll();

            t.status = "Resolved";

            System.out.println(
                "\n===== SUPPORT RESOLVED ====="
            );

            System.out.println(
                "User     : " + t.username
            );

            System.out.println(
                "Issue    : " + t.issue
            );

            System.out.println(
                "Solution : " + t.solution
            );

            System.out.println(
                "Status   : " + t.status
            );

        } else {

            System.out.println(
                "No pending tickets"
            );
        }
    }

    // =====================================
    // PENDING COUNT
    // =====================================
    public int pendingTickets() {
        return tickets.size();
    }
}