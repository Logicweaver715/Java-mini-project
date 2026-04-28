package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.PostureResult;
import service.PostureAnalyzer;

/**
 * Premium ExerciseUI.java
 * Updated with START / PAUSE / RESET / STOP
 */
public class ExerciseUI extends JPanel {

    private final MainFrame mainFrame;

    private Timer timer;
    private int seconds = 0;
    private boolean running = false;

    private JLabel timerLabel;
    private JLabel selectedLabel;
    private JLabel postureLabel;
    private JLabel scoreLabel;

    private JProgressBar postureBar;
    private JProgressBar scoreBar;

    private JButton startPauseBtn;
    private JButton resetBtn;
    private JButton stopBtn;   // NEW

    private String selectedExercise = "Push-Ups";

    private static final Object[][] EXERCISES = {
            {"Push-Ups", "💪"},
            {"Squats", "🦵"},
            {"Yoga Flow", "🧘"},
            {"Cycling", "🚴"},
            {"Walking", "🚶"},
            {"Plank", "🏋"}
    };

    public ExerciseUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);

        buildUI();
    }

    private void buildUI() {

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Theme.BG_SIDEBAR);
        top.setBorder(new EmptyBorder(18, 28, 18, 28));

        JLabel title = Theme.createLabel(
                "Exercise Center",
                Theme.FONT_TITLE,
                Theme.TEXT_PRIMARY
        );

        JLabel sub = Theme.createLabel(
                "Train smart. Track progress. Improve posture.",
                Theme.FONT_SMALL,
                Theme.TEXT_SECONDARY
        );

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(sub);

        top.add(left, BorderLayout.WEST);

        add(top, BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(18, 0));
        main.setOpaque(false);
        main.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel grid = new JPanel(new GridLayout(2, 3, 18, 18));
        grid.setOpaque(false);

        for (Object[] ex : EXERCISES) {
            grid.add(buildExerciseCard(
                    (String) ex[0],
                    (String) ex[1]
            ));
        }

        main.add(grid, BorderLayout.CENTER);

        JPanel side = new JPanel();
        side.setOpaque(false);
        side.setPreferredSize(new Dimension(300, 0));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        side.add(buildWorkoutPanel());
        side.add(Box.createVerticalStrut(16));
        side.add(buildPosturePanel());
        side.add(Box.createVerticalStrut(16));
        side.add(buildScorePanel());

        main.add(side, BorderLayout.EAST);

        add(main, BorderLayout.CENTER);
    }

    private JPanel buildExerciseCard(String name, String icon) {

        JPanel card = Theme.createCard();
        card.setLayout(new BorderLayout(0, 12));

        JLabel iconLabel = Theme.createLabel(
                icon,
                new Font("SansSerif", Font.PLAIN, 30),
                Theme.ACCENT_CYAN
        );
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = Theme.createLabel(
                name,
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btn = Theme.createButton(
                "Start",
                Theme.ACCENT_CYAN,
                Theme.BG_DARKEST
        );

        btn.addActionListener(e -> startExercise(name));

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.add(Box.createVerticalGlue());
        center.add(iconLabel);
        center.add(Box.createVerticalStrut(10));
        center.add(title);
        center.add(Box.createVerticalGlue());

        card.add(center, BorderLayout.CENTER);
        card.add(btn, BorderLayout.SOUTH);

        return card;
    }

    private JPanel buildWorkoutPanel() {

        JPanel card = Theme.createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = Theme.createLabel(
                "Workout Timer",
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        timerLabel = Theme.createLabel(
                "00:00",
                new Font("SansSerif", Font.BOLD, 32),
                Theme.ACCENT_GREEN
        );
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectedLabel = Theme.createLabel(
                "Current: Push-Ups",
                Theme.FONT_SMALL,
                Theme.TEXT_SECONDARY
        );
        selectedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startPauseBtn = Theme.createButton(
                "Start",
                Theme.ACCENT_GREEN,
                Theme.BG_DARKEST
        );

        resetBtn = Theme.createOutlineButton(
                "Reset",
                Theme.ACCENT_CYAN
        );

        stopBtn = Theme.createButton(
                "Stop",
                Theme.ACCENT_PINK,
                Theme.BG_DARKEST
        );

        startPauseBtn.addActionListener(e -> toggleTimer());
        resetBtn.addActionListener(e -> resetTimer());
        stopBtn.addActionListener(e -> stopWorkout());

        timer = new Timer(1000, e -> {
            seconds++;
            updateTimer();
        });

        card.add(title);
        card.add(Box.createVerticalStrut(15));
        card.add(timerLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(selectedLabel);
        card.add(Box.createVerticalStrut(16));
        card.add(startPauseBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(stopBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(resetBtn);

        return card;
    }

    private JPanel buildPosturePanel() {

        JPanel card = Theme.createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = Theme.createLabel(
                "Posture Analysis",
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        postureLabel = Theme.createLabel(
                "Ready to analyze",
                Theme.FONT_SMALL,
                Theme.TEXT_SECONDARY
        );

        postureBar = Theme.createProgressBar(Theme.ACCENT_ORANGE);
        postureBar.setValue(75);

        JButton analyzeBtn = Theme.createButton(
                "Analyze",
                Theme.ACCENT_ORANGE,
                Theme.BG_DARKEST
        );

        analyzeBtn.addActionListener(e -> analyzePosture());

        card.add(title);
        card.add(Box.createVerticalStrut(14));
        card.add(postureLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(postureBar);
        card.add(Box.createVerticalStrut(14));
        card.add(analyzeBtn);

        return card;
    }

    private JPanel buildScorePanel() {

        JPanel card = Theme.createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = Theme.createLabel(
                "Fitness Score",
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        scoreLabel = Theme.createLabel(
                "82 / 100",
                new Font("SansSerif", Font.BOLD, 28),
                Theme.ACCENT_PURPLE
        );

        scoreBar = Theme.createProgressBar(Theme.ACCENT_PURPLE);
        scoreBar.setValue(82);

        card.add(title);
        card.add(Box.createVerticalStrut(14));
        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(scoreBar);

        return card;
    }

    private void startExercise(String name) {

        selectedExercise = name;
        selectedLabel.setText("Current: " + name);

        if (!running) {
            toggleTimer();
        }

        JOptionPane.showMessageDialog(
                this,
                "Started " + name + " workout!"
        );
    }

    private void toggleTimer() {

        if (!running) {
            timer.start();
            running = true;
            startPauseBtn.setText("Pause");
        } else {
            timer.stop();
            running = false;
            startPauseBtn.setText("Start");
        }
    }

    private void resetTimer() {

        timer.stop();
        running = false;
        seconds = 0;
        updateTimer();
        startPauseBtn.setText("Start");
    }

    // NEW STOP METHOD
    private void stopWorkout() {

        timer.stop();
        running = false;

        String time = timerLabel.getText();

        JOptionPane.showMessageDialog(
                this,
                selectedExercise + " Completed!\nTime: " + time
        );

        // Save in tracker if method exists
         mainFrame.getTrackerUI().addWorkout(selectedExercise, seconds);

        seconds = 0;
        updateTimer();
        startPauseBtn.setText("Start");
    }

    private void updateTimer() {

        int min = seconds / 60;
        int sec = seconds % 60;

        timerLabel.setText(
                String.format("%02d:%02d", min, sec)
        );
    }

    private void analyzePosture() {

        PostureResult result =
                new PostureAnalyzer().analyze(selectedExercise);

        int score = 60 + (int) (Math.random() * 40);

        postureBar.setValue(score);

        if (score > 80) {
            postureLabel.setText("Excellent - " + result.feedback);
        } else if (score > 65) {
            postureLabel.setText("Good - " + result.feedback);
        } else {
            postureLabel.setText("Needs Work - " + result.feedback);
        }

        scoreLabel.setText(score + " / 100");
        scoreBar.setValue(score);
    }
}