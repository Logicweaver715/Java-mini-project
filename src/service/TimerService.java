package service;

public class TimerService extends Thread {

    private int seconds;

    public TimerService(int seconds) {
        this.seconds = seconds;
    }

    public void run() {

        try {

            while (seconds >= 0) {

                System.out.println(
                    "Time Left: " + seconds
                );

                Thread.sleep(1000);

                seconds--;
            }

        } catch (Exception e) {

            System.out.println("Timer Error");
        }
    }
}