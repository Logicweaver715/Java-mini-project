package service;

import model.Trainer;

public class BookingThread extends Thread {

    private Trainer trainer;
    private String slot;

    public BookingThread(Trainer trainer, String slot) {
        this.trainer = trainer;
        this.slot = slot;
    }

    public void run() {

        if (trainer.bookSlot(slot)) {

            System.out.println(
                Thread.currentThread().getName() +
                " SUCCESS: Booked " + slot +
                " with " + trainer.name
            );

        } else {

            System.out.println(
                Thread.currentThread().getName() +
                " FAILED: Slot already taken"
            );
        }
    }
}