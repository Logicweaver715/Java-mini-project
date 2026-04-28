package model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {

    public String name;
    public String expertise;
    public int price;
    public List<String> slots;

    public Trainer(String name, String expertise, int price, List<String> slots) {
        this.name = name;
        this.expertise = expertise;
        this.price = price;
        this.slots = new ArrayList<>(slots);
    }

    // thread-safe booking
    public synchronized boolean bookSlot(String slot) {

        if (slots.contains(slot)) {
            slots.remove(slot);
            return true;
        }

        return false;
    }

    public void display() {

        System.out.println("\nTrainer: " + name);
        System.out.println("Expertise: " + expertise);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Slots: " + slots);
    }
}