package ui;

import java.awt.*;
import javax.swing.*;
import service.BookingService;

/**
 * BookingUI.java — Trainer booking interface
 * Compatible with Trainer model, BookingService
 */
public class BookingUI extends JPanel {

    private final MainFrame mainFrame;

    // {name, expertise, price, rating, slots[], bio, icon}
    private static final Object[][] TRAINERS = {
        {"Alex Carter",   "Strength & Conditioning", "$45/hr", "4.9",
         new String[]{"Mon 9 AM","Mon 2 PM","Wed 10 AM","Fri 11 AM"},
         "5+ years specialising in functional strength training.", "💪"},
        {"Priya Sharma",  "Yoga & Mindfulness",      "$35/hr", "4.8",
         new String[]{"Tue 7 AM","Thu 6 PM","Sat 8 AM","Sun 9 AM"},
         "Certified yoga instructor with a focus on stress relief.", "🧘"},
        {"Marcus James",  "HIIT & Cardio",            "$40/hr", "4.7",
         new String[]{"Mon 6 AM","Wed 7 PM","Fri 6 AM","Sat 10 AM"},
         "High-intensity specialist — burns fat, builds endurance.", "🔥"},
        {"Sofia Lee",     "Nutrition & Diet",         "$50/hr", "5.0",
         new String[]{"Tue 11 AM","Wed 3 PM","Thu 10 AM","Sat 1 PM"},
         "Registered dietitian and certified sports nutritionist.", "🥗"},
        {"David Okafor",  "Marathon & Running",       "$38/hr", "4.6",
         new String[]{"Mon 5 AM","Thu 7 AM","Sat 7 AM","Sun 6 AM"},
         "Former marathon runner helping you hit your personal best.", "🏃"},
        {"Yuki Tanaka",   "Pilates & Flexibility",    "$42/hr", "4.9",
         new String[]{"Tue 8 AM","Thu 9 AM","Fri 4 PM","Sat 11 AM"},
         "Expert in posture correction and flexibility improvement.", "🌸"},
    };

    public BookingUI(MainFrame mainFrame) {
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
        topBar.add(Theme.createSectionTitle("Book a Trainer"), BorderLayout.WEST);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightTop.setOpaque(false);
        rightTop.add(Theme.createBadge("6 Trainers Available", Theme.ACCENT_GREEN));
        topBar.add(rightTop, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // ── Filter bar ────────────────────────────────────────────────────────
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        filterBar.setBackground(Theme.BG_DARKEST);
        filterBar.setBorder(BorderFactory.createEmptyBorder(4, 20, 0, 20));

        filterBar.add(Theme.createLabel("Filter:", Theme.FONT_LABEL, Theme.TEXT_SECONDARY));
        String[] filters = {"All", "Strength", "Yoga", "HIIT", "Nutrition", "Running", "Pilates"};
        for (String f : filters) {
            JButton fb = Theme.createOutlineButton(f, Theme.TEXT_MUTED);
            fb.setPreferredSize(new Dimension(90, 30));
            fb.setFont(Theme.FONT_SMALL);
            fb.addActionListener(e -> {
                // filter logic placeholder
            });
            filterBar.add(fb);
        }
        add(filterBar, BorderLayout.AFTER_LAST_LINE);

        // ── Trainer card grid ─────────────────────────────────────────────────
        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 16));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(16, 24, 24, 24));

        for (Object[] trainer : TRAINERS) {
            grid.add(buildTrainerCard(trainer));
        }

        add(Theme.createScrollPane(grid), BorderLayout.CENTER);
    }

    private JPanel buildTrainerCard(Object[] trainer) {
        String   name      = (String)   trainer[0];
        String   expertise = (String)   trainer[1];
        String   price     = (String)   trainer[2];
        String   rating    = (String)   trainer[3];
        String[] slots     = (String[]) trainer[4];
        String   bio       = (String)   trainer[5];
        String   icon      = (String)   trainer[6];

        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        // Avatar + name + expertise
        JPanel topRow = new JPanel(new BorderLayout(10, 0));
        topRow.setOpaque(false);

        JLabel avatar = new JLabel(icon, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0x252D45));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setFont(new Font("SansSerif", Font.PLAIN, 28));
        avatar.setPreferredSize(new Dimension(56, 56));

        JPanel nameArea = new JPanel();
        nameArea.setOpaque(false);
        nameArea.setLayout(new BoxLayout(nameArea, BoxLayout.Y_AXIS));
        JLabel nameLbl = Theme.createLabel(name, Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY);
        JLabel expLbl  = Theme.createLabel(expertise, Theme.FONT_SMALL, Theme.ACCENT_CYAN);
        nameArea.add(nameLbl);
        nameArea.add(Box.createVerticalStrut(2));
        nameArea.add(expLbl);

        topRow.add(avatar,   BorderLayout.WEST);
        topRow.add(nameArea, BorderLayout.CENTER);

        c.insets = new Insets(0,0,10,0);
        card.add(topRow, c); c.gridy++;

        // Rating + price row
        JPanel metaRow = new JPanel(new BorderLayout());
        metaRow.setOpaque(false);
        metaRow.add(Theme.createBadge("⭐ " + rating, Theme.ACCENT_ORANGE), BorderLayout.WEST);
        metaRow.add(Theme.createLabel(price, Theme.FONT_SUBHEAD, Theme.ACCENT_GREEN), BorderLayout.EAST);
        c.insets = new Insets(0,0,8,0);
        card.add(metaRow, c); c.gridy++;

        // Bio
        JLabel bioLbl = Theme.createLabel("<html>" + bio + "</html>", Theme.FONT_SMALL, Theme.TEXT_SECONDARY);
        bioLbl.setPreferredSize(new Dimension(200, 36));
        c.insets = new Insets(0,0,10,0);
        card.add(bioLbl, c); c.gridy++;

        // Slot selector
        JLabel slotLbl = Theme.createLabel("AVAILABLE SLOTS", Theme.FONT_LABEL, Theme.TEXT_MUTED);
        c.insets = new Insets(0,0,4,0);
        card.add(slotLbl, c); c.gridy++;

        JComboBox<String> slotBox = Theme.createComboBox(slots);
        slotBox.setPreferredSize(new Dimension(220, 38));
        c.insets = new Insets(0,0,10,0);
        card.add(slotBox, c); c.gridy++;

        // Book button
        JButton bookBtn = Theme.createButton("Book Session", Theme.ACCENT_CYAN, Theme.BG_DARKEST);
        bookBtn.setPreferredSize(new Dimension(220, 38));
        bookBtn.addActionListener(e -> handleBooking(name, (String) slotBox.getSelectedItem(), price));
        c.insets = new Insets(0,0,0,0);
        card.add(bookBtn, c);

        return card;
    }

    private void handleBooking(String trainerName, String slot, String price) {
        // ── Backend integration ───────────────────────────────────────────────
        BookingService bs = new BookingService();

        bs.saveBooking(
        "User",
        trainerName,
        "Today",
        slot
); 

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><b>Confirm Booking</b><br><br>" +
                "Trainer: <b>" + trainerName + "</b><br>" +
                "Slot: <b>" + slot + "</b><br>" +
                "Price: <b>" + price + "</b></html>",
                "Book Session", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (confirm == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "✅  Session booked with " + trainerName + " at " + slot + "!",
                    "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}