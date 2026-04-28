package service;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import model.Trainer;

public class BookingService {

    private List<Trainer> trainers = new ArrayList<>();

    // Add trainer
    public void addTrainer(Trainer t) {
        trainers.add(t);
    }

    // Show all trainers
    public void showTrainers() {

        for (int i = 0; i < trainers.size(); i++) {

            System.out.println("\n[" + i + "]");

            trainers.get(i).display();
        }
    }

    
    // Get trainer by index
    public Trainer getTrainer(int index) {

        if (index >= 0 && index < trainers.size()) {
            return trainers.get(index);
        }

        return null;
    }

    // Book slot using multithreading
    public void bookSlot(Trainer trainer, String slot) {

        BookingThread t1 =
                new BookingThread(trainer, slot);

        BookingThread t2 =
                new BookingThread(trainer, slot);

        t1.setName("User1");
        t2.setName("User2");

        t1.start();
        t2.start();
    }
    // =====================================
    public void saveBooking(String username,
                            String trainerName,
                            String date,
                            String time) {

        try {

            FileWriter fw =
                new FileWriter(
                    "data/bookings.txt", true
                );

            fw.write(
                username + "," +
                trainerName + "," +
                date + "," +
                time + "\n"
            );

            fw.close();

            System.out.println("Booking Saved");

        } catch (Exception e) {

            System.out.println("Booking Save Error");
        }
    }
}
