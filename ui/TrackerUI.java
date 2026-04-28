package ui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * TrackerUI.java — Workout history and progress tracking
 * Compatible with WorkoutTracker service and WorkoutSession model
 */
public class TrackerUI extends JPanel {

    private final MainFrame mainFrame;
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public TrackerUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);
        buildUI();
    }

    private void buildUI() {
        // ── Top bar ───────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.BG_SIDEBAR);
        topBar.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));
        topBar.add(Theme.createSectionTitle("Workout Tracker"), BorderLayout.WEST);
        topBar.add(Theme.createBadge("This Month", Theme.ACCENT_GREEN), BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // ── Main content ──────────────────────────────────────────────────────
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Theme.BG_DARKEST);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // ── Stats row ─────────────────────────────────────────────────────────
        content.add(buildStatsRow());
        content.add(Box.createVerticalStrut(20));

        // ── Weekly chart ──────────────────────────────────────────────────────
        content.add(buildProgressChart());
        content.add(Box.createVerticalStrut(20));

        // ── History table ─────────────────────────────────────────────────────
        content.add(buildHistoryTable());

        add(Theme.createScrollPane(content), BorderLayout.CENTER);
    }

    /** Summary stat cards */
    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 14, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        Object[][] stats = {
                {"Sessions", "24", "This month",     Theme.ACCENT_CYAN,   "🏋"},
                {"Minutes",  "720","Total logged",    Theme.ACCENT_GREEN,  "⏱"},
                {"Calories", "9,840","kcal burned",   Theme.ACCENT_ORANGE, "🔥"},
                {"Streak",   "7",  "Day streak 🔥",   Theme.ACCENT_PURPLE, "⚡"},
        };

        for (Object[] s : stats) {
            JPanel card = Theme.createAccentCard((Color) s[3]);
            card.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;

            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            top.add(Theme.createLabel((String)s[4], new Font("SansSerif", Font.PLAIN, 18), (Color)s[3]), BorderLayout.WEST);
            top.add(Theme.createLabel((String)s[0], Theme.FONT_LABEL, Theme.TEXT_SECONDARY), BorderLayout.CENTER);
            c.insets = new Insets(0,0,6,0);
            card.add(top, c); c.gridy++;

            JLabel val = Theme.createLabel((String)s[1], Theme.FONT_CARD_NUM, Theme.TEXT_PRIMARY);
            c.insets = new Insets(0,0,4,0);
            card.add(val, c); c.gridy++;

            card.add(Theme.createLabel((String)s[2], Theme.FONT_SMALL, Theme.TEXT_MUTED), c);
            row.add(card);
        }
        return row;
    }

    /** Activity line chart */
    private JPanel buildProgressChart() {
        JPanel card = Theme.createCard();
        card.setLayout(new BorderLayout(0, 10));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        titleRow.add(Theme.createLabel("Weekly Workout Minutes", Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY), BorderLayout.WEST);
        titleRow.add(Theme.createBadge("↑ 18% vs last week", Theme.ACCENT_GREEN), BorderLayout.EAST);
        card.add(titleRow, BorderLayout.NORTH);

        int[] data = {30, 45, 0, 60, 45, 75, 50};
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        JPanel chart = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight() - 24;
                int max = 90;
                int step = w / (data.length - 1);

                // Draw area fill under line
                GeneralPath area = new GeneralPath();
                for (int i = 0; i < data.length; i++) {
                    int x = i * step;
                    int y = h - (int)((double) data[i] / max * h);
                    if (i == 0) area.moveTo(x, y);
                    else        area.lineTo(x, y);
                }
                area.lineTo((data.length-1)*step, h);
                area.lineTo(0, h);
                area.closePath();
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(Theme.ACCENT_GREEN.getRed(), Theme.ACCENT_GREEN.getGreen(),
                                Theme.ACCENT_GREEN.getBlue(), 60),
                        0, h, new Color(0,0,0,0));
                g2.setPaint(gp);
                g2.fill(area);

                // Draw line
                g2.setColor(Theme.ACCENT_GREEN);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                GeneralPath line = new GeneralPath();
                for (int i = 0; i < data.length; i++) {
                    int x = i * step;
                    int y = h - (int)((double) data[i] / max * h);
                    if (i == 0) line.moveTo(x, y);
                    else        line.lineTo(x, y);
                }
                g2.draw(line);

                // Dots
                for (int i = 0; i < data.length; i++) {
                    int x = i * step;
                    int y = h - (int)((double) data[i] / max * h);
                    g2.setColor(Theme.ACCENT_GREEN);
                    g2.fillOval(x-5, y-5, 10, 10);
                    g2.setColor(Theme.BG_CARD);
                    g2.fillOval(x-3, y-3, 6, 6);

                    // Day labels
                    g2.setColor(Theme.TEXT_MUTED);
                    g2.setFont(Theme.FONT_SMALL);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(days[i], x - fm.stringWidth(days[i])/2, getHeight()-4);
                }
                g2.dispose();
            }
        };
        chart.setOpaque(false);
        card.add(chart, BorderLayout.CENTER);
        return card;
    }

    /** History table */
    private JPanel buildHistoryTable() {
        JPanel card = Theme.createCard();
        card.setLayout(new BorderLayout(0, 10));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        titleRow.add(Theme.createLabel("Workout History", Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY), BorderLayout.WEST);
        JButton clearBtn = Theme.createOutlineButton("Clear All", Theme.ACCENT_PINK);
        clearBtn.setPreferredSize(new Dimension(100, 30));
        clearBtn.setFont(Theme.FONT_SMALL);
        titleRow.add(clearBtn, BorderLayout.EAST);
        card.add(titleRow, BorderLayout.NORTH);

        String[] cols = {"Date", "Exercise", "Duration", "Calories", "Level", "Status"};
        Object[][] rows = {
                {"Apr 23, 2025", "Push-Ups + Plank",  "35 min", "210 kcal", "Intermediate", "✅ Done"},
                {"Apr 22, 2025", "Yoga Flow",          "45 min", "180 kcal", "Beginner",     "✅ Done"},
                {"Apr 21, 2025", "Cycling",            "60 min", "540 kcal", "Advanced",     "✅ Done"},
                {"Apr 20, 2025", "—",                  "—",      "—",        "—",            "⏭ Skipped"},
                {"Apr 19, 2025", "Squats + Walking",   "50 min", "320 kcal", "Beginner",     "✅ Done"},
                {"Apr 18, 2025", "Push-Ups",           "25 min", "125 kcal", "Beginner",     "✅ Done"},
        };

        tableModel = new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        historyTable = new JTable(tableModel) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                c.setBackground(row % 2 == 0 ? Theme.BG_CARD : Theme.BG_DARKEST);
                c.setForeground(Theme.TEXT_PRIMARY);
                return c;
            }
        };
        historyTable.setRowHeight(34);
        historyTable.setFont(Theme.FONT_BODY);
        historyTable.setForeground(Theme.TEXT_PRIMARY);
        historyTable.setBackground(Theme.BG_CARD);
        historyTable.setGridColor(Theme.BORDER_COLOR);
        historyTable.setSelectionBackground(Theme.BG_CARD_HOVER);
        historyTable.setSelectionForeground(Theme.ACCENT_CYAN);
        historyTable.setShowHorizontalLines(true);
        historyTable.setShowVerticalLines(false);
        historyTable.getTableHeader().setFont(Theme.FONT_LABEL);
        historyTable.getTableHeader().setForeground(Theme.TEXT_SECONDARY);
        historyTable.getTableHeader().setBackground(Theme.BG_SIDEBAR);
        historyTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0,0,1,0,Theme.BORDER_COLOR));

        card.add(Theme.createScrollPane(historyTable), BorderLayout.CENTER);

        clearBtn.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this,
                    "Clear all workout history?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) tableModel.setRowCount(0);
        });

        return card;
    }

    /** Called externally to add a new session row */
    public void addWorkout(String exercise, int seconds) {

    String duration;

    if (seconds < 60) {
        duration = seconds + " sec";
    } else {
       int min = seconds / 60;
    int sec = seconds % 60;

duration = min + " min " + sec + " sec";
    }

    String calories = (seconds * 2) + " kcal";

    tableModel.insertRow(0, new Object[]{
            "Today",
            exercise,
            duration,
            calories,
            "Beginner",
            "✅ Done"
    });
}
}