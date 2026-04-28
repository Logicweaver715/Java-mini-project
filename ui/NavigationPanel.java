package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

/**
 * NavigationPanel.java — Left sidebar navigation
 * Shared across all authenticated pages via MainFrame's app shell
 */
public class NavigationPanel extends JPanel {

    private final MainFrame mainFrame;
    private String activePage = MainFrame.PAGE_DASH;

    // {label, pageKey, icon}
    private static final Object[][] NAV_ITEMS = {
        {"Dashboard",  MainFrame.PAGE_DASH,     "⬡"},
        {"Exercise",   MainFrame.PAGE_EXERCISE, "💪"},
        {"Diet",       MainFrame.PAGE_DIET,     "🥗"},
        {"Tracker",    MainFrame.PAGE_TRACKER,  "📊"},
        {"Booking",    MainFrame.PAGE_BOOKING,  "📅"},
        {"Support",    MainFrame.PAGE_SUPPORT,  "🛟"},
    };

    private final JButton[] navButtons = new JButton[NAV_ITEMS.length];

    public NavigationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(220, 800));
        setMinimumSize(new Dimension(220, 800));
        setLayout(new BorderLayout());
        setBackground(Theme.BG_SIDEBAR);
        buildUI();
    }

    private void buildUI() {
        // ── Logo / brand ──────────────────────────────────────────────────────
        JPanel brand = new JPanel(new BorderLayout());
        brand.setBackground(Theme.BG_SIDEBAR);
        brand.setBorder(BorderFactory.createEmptyBorder(22, 20, 22, 20));

        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        logoRow.setOpaque(false);

        JLabel logoIcon = new JLabel("⚡") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(Theme.ACCENT_CYAN.getRed(), Theme.ACCENT_CYAN.getGreen(),
                        Theme.ACCENT_CYAN.getBlue(), 20));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logoIcon.setFont(new Font("SansSerif", Font.PLAIN, 22));
        logoIcon.setForeground(Theme.ACCENT_CYAN);
        logoIcon.setPreferredSize(new Dimension(38, 38));
        logoIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel appName = Theme.createLabel("FitPulse", new Font("SansSerif", Font.BOLD, 18), Theme.TEXT_PRIMARY);

        logoRow.add(logoIcon);
        logoRow.add(appName);
        brand.add(logoRow, BorderLayout.CENTER);

        // Thin bottom border
        brand.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.DIVIDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        add(brand, BorderLayout.NORTH);

        // ── Nav buttons ───────────────────────────────────────────────────────
        JPanel navList = new JPanel();
        navList.setBackground(Theme.BG_SIDEBAR);
        navList.setLayout(new BoxLayout(navList, BoxLayout.Y_AXIS));
        navList.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        for (int i = 0; i < NAV_ITEMS.length; i++) {
            final String label   = (String) NAV_ITEMS[i][0];
            final String pageKey = (String) NAV_ITEMS[i][1];
            final String icon    = (String) NAV_ITEMS[i][2];
            final int    idx     = i;

            navButtons[i] = buildNavButton(label, icon, pageKey.equals(activePage));
            navButtons[i].addActionListener(e -> {
                setActivePage(pageKey);
                mainFrame.showPage(pageKey);
            });

            navList.add(navButtons[i]);
            navList.add(Box.createVerticalStrut(2));
        }

        add(navList, BorderLayout.CENTER);

        // ── Bottom: user info + logout ─────────────────────────────────────────
        JPanel bottom = new JPanel();
        bottom.setBackground(Theme.BG_SIDEBAR);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.DIVIDER),
                BorderFactory.createEmptyBorder(12, 14, 16, 14)
        ));

        // User chip
        JPanel userChip = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        userChip.setOpaque(false);
        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JPanel userInfo = new JPanel();
        userInfo.setOpaque(false);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.add(Theme.createLabel("My Account", Theme.FONT_LABEL, Theme.TEXT_PRIMARY));
        userInfo.add(Theme.createLabel("Premium Plan", Theme.FONT_SMALL, Theme.ACCENT_CYAN));
        userChip.add(avatar);
        userChip.add(userInfo);
        bottom.add(userChip);
        bottom.add(Box.createVerticalStrut(8));

        // Logout button
        JButton logoutBtn = buildNavButton("Logout", "🚪", false);
        logoutBtn.setForeground(Theme.ACCENT_PINK);
        logoutBtn.addActionListener(e -> mainFrame.onLogout());
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        bottom.add(logoutBtn);

        add(bottom, BorderLayout.SOUTH);
        revalidate();
repaint();
    }

    /** Build a sidebar nav button */
    private JButton buildNavButton(String label, String icon, boolean active) {
        final Color activeColor = Theme.ACCENT_CYAN;

        JButton btn = new JButton() {
            private boolean hovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }

            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isActive = label.equals(getActiveLabel());

                if (isActive) {
                    // Active state: filled pill with left accent
                    g2.setColor(new Color(activeColor.getRed(), activeColor.getGreen(), activeColor.getBlue(), 18));
                    g2.fill(new RoundRectangle2D.Float(4, 2, getWidth()-8, getHeight()-4, 10, 10));
                    g2.setColor(activeColor);
                    g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.draw(new RoundRectangle2D.Float(0, 6, 3, getHeight()-12, 3, 3));
                } else if (hovered) {
                    g2.setColor(new Color(0xFF, 0xFF, 0xFF, 8));
                    g2.fill(new RoundRectangle2D.Float(4, 2, getWidth()-8, getHeight()-4, 10, 10));
                }
                g2.dispose();
                super.paintComponent(g);
            }

            @Override protected void paintBorder(Graphics g) {}
        };

        // Build button content
        btn.setLayout(new BorderLayout(10, 0));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 14));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        iconLbl.setForeground(active ? activeColor : Theme.TEXT_PRIMARY);

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(Theme.FONT_NAV);
        textLbl.setForeground(active ? Theme.TEXT_PRIMARY : Theme.TEXT_SECONDARY);

        btn.add(iconLbl, BorderLayout.WEST);
        btn.add(textLbl, BorderLayout.CENTER);

        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Store label for active check
        btn.setName(label);

        return btn;

    }

    public void setActivePage(String pageKey) {
        this.activePage = pageKey;
        repaint();
        // Update label colors
        for (int i = 0; i < NAV_ITEMS.length; i++) {
            boolean isThis = NAV_ITEMS[i][1].equals(pageKey);
            // Force repaint for active indicator
            if (navButtons[i] != null) navButtons[i].repaint();
        }
    }

    private String getActiveLabel() {
        for (Object[] item : NAV_ITEMS) {
            if (item[1].equals(activePage)) return (String) item[0];
        }
        return "";
    }
}