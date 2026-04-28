package ui;

import java.awt.*;
import javax.swing.*;

public class DashboardUI extends JPanel {

    private final MainFrame mainFrame;
    private String username = "User";

    private JLabel bmiValue, weightValue, bpValue, sugarValue;
    private JLabel stepsValue, caloriesValue, scoreValue;
    private JLabel greetLabel;
    private JProgressBar goalProgress;

    public DashboardUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);

        buildUI();
    }

    public void setUsername(String username) {
        this.username = username;

        if (greetLabel != null) {
            greetLabel.setText("Welcome back, " + username + " 👋");
        }
    }

    private void buildUI() {

        // TOP BAR
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.BG_SIDEBAR);
        topBar.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        greetLabel = Theme.createLabel(
                "Welcome back, " + username + " 👋",
                new Font("SansSerif", Font.BOLD, 20),
                Theme.TEXT_PRIMARY
        );

        JLabel dateLabel = Theme.createLabel(
                java.time.LocalDate.now().toString(),
                Theme.FONT_SMALL,
                Theme.TEXT_MUTED
        );

        left.add(greetLabel);
        left.add(Box.createVerticalStrut(3));
        left.add(dateLabel);

        topBar.add(left, BorderLayout.WEST);

        JButton refreshBtn =
                Theme.createOutlineButton("↻ Refresh", Theme.ACCENT_CYAN);

        refreshBtn.setPreferredSize(new Dimension(120, 36));

        refreshBtn.addActionListener(e -> refreshMetrics());

        topBar.add(refreshBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // CONTENT
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Theme.BG_DARKEST);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        // ROW 1
        JPanel row1 = new JPanel(new GridLayout(1, 4, 16, 0));
        row1.setOpaque(false);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        bmiValue = new JLabel("23.4");
        weightValue = new JLabel("70 kg");
        bpValue = new JLabel("120/80");
        sugarValue = new JLabel("88");

        row1.add(buildCard("BMI", bmiValue, Theme.ACCENT_CYAN, "⚖"));
        row1.add(buildCard("Weight", weightValue, Theme.ACCENT_GREEN, "🏋"));
        row1.add(buildCard("BP", bpValue, Theme.ACCENT_PURPLE, "❤"));
        row1.add(buildCard("Sugar", sugarValue, Theme.ACCENT_ORANGE, "🩸"));

        content.add(row1);
        content.add(Box.createVerticalStrut(16));

        // ROW 2
        JPanel row2 = new JPanel(new GridLayout(1, 3, 16, 0));
        row2.setOpaque(false);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        stepsValue = new JLabel("6842");
        caloriesValue = new JLabel("432 kcal");
        scoreValue = new JLabel("82 / 100");

        row2.add(buildCard("Steps", stepsValue, Theme.ACCENT_CYAN, "👟"));
        row2.add(buildCard("Calories", caloriesValue, Theme.ACCENT_ORANGE, "🔥"));
        row2.add(buildCard("Score", scoreValue, Theme.ACCENT_GREEN, "🏆"));

        content.add(row2);
        content.add(Box.createVerticalStrut(20));

        // GOAL CARD
        JPanel goalCard = Theme.createCard();
        goalCard.setLayout(new BorderLayout(0, 12));
        goalCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        JLabel title = Theme.createLabel(
                "Goal Progress",
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        goalCard.add(title, BorderLayout.NORTH);

        JPanel mid = new JPanel();
        mid.setOpaque(false);
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));

        JLabel goalText = Theme.createLabel(
                "Lose Weight",
                Theme.FONT_SMALL,
                Theme.TEXT_SECONDARY
        );

        goalProgress = Theme.createProgressBar(Theme.ACCENT_CYAN);
        goalProgress.setValue(72);

        mid.add(goalText);
        mid.add(Box.createVerticalStrut(8));
        mid.add(goalProgress);

        goalCard.add(mid, BorderLayout.CENTER);

        content.add(goalCard);
        content.add(Box.createVerticalStrut(20));

        // WEEKLY CHART
        content.add(buildChart());

        JScrollPane scroll = Theme.createScrollPane(content);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buildCard(
            String title,
            JLabel value,
            Color color,
            String icon
    ) {
        JPanel card = Theme.createAccentCard(color);
        card.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel titleLabel =
                Theme.createLabel(icon + " " + title,
                        Theme.FONT_LABEL,
                        Theme.TEXT_SECONDARY);

        card.add(titleLabel, c);

        c.gridy++;

        value.setFont(Theme.FONT_CARD_NUM);
        value.setForeground(Theme.TEXT_PRIMARY);

        card.add(value, c);

        return card;
    }

    private JPanel buildChart() {

        JPanel card = Theme.createCard();
        card.setLayout(new BorderLayout());

        JLabel title = Theme.createLabel(
                "Weekly Activity",
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        card.add(title, BorderLayout.NORTH);

        int[] data = {320, 410, 280, 490, 360, 520, 432};
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

        JPanel chart = new JPanel() {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                int w = getWidth();
                int h = getHeight();

                int barWidth = w / 14;

                for (int i = 0; i < data.length; i++) {

                    int barHeight = data[i] / 2;

                    int x = 20 + i * (barWidth + 20);
                    int y = h - barHeight - 30;

                    g2.setColor(Theme.ACCENT_CYAN);
                    g2.fillRoundRect(
                            x, y,
                            barWidth,
                            barHeight,
                            8, 8
                    );

                    g2.setColor(Theme.TEXT_SECONDARY);
                    g2.drawString(days[i], x, h - 10);
                }
            }
        };

        chart.setPreferredSize(new Dimension(700, 220));
        chart.setOpaque(false);

        card.add(chart, BorderLayout.CENTER);

        return card;
    }

    public void refreshMetrics() {

    bmiValue.setText("24.1");
    weightValue.setText("71 kg");
    bpValue.setText("118/79");
    sugarValue.setText("90");

    stepsValue.setText("7450");
    caloriesValue.setText("510 kcal");
    scoreValue.setText("86 / 100");

    goalProgress.setValue(80);

    repaint();

}
}
