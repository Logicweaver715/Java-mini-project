package ui;

import java.awt.*;
import javax.swing.*;
import model.SupportTicket;
import service.SupportService;

/**
 * SupportUI.java — Help & Support ticket system
 * Compatible with SupportTicket model and SupportService
 */
public class SupportUI extends JPanel {

    private final MainFrame mainFrame;

    private JComboBox<String> issueBox;
    private JTextArea descArea;
    private JTextArea troubleshootArea;
    private JLabel ticketStatusLabel;
    private JLabel ticketIdLabel;

    private static final String[] ISSUES = {
        "Select an issue...",
        "Unable to log in",
        "App crashes on startup",
        "Payment / billing issue",
        "Workout data not syncing",
        "Trainer booking failed",
        "Diet plan not loading",
        "Incorrect BMI / health data",
        "Timer not working",
        "Profile settings not saving",
        "Notification not receiving",
        "Exercise video not playing",
        "Other / General inquiry",
    };

    private static final String[][] SOLUTIONS = {
        {},
        {"1. Ensure your username and password are correct.", "2. Try resetting your password via 'Forgot Password'.", "3. Check internet connectivity.", "4. Clear app cache and retry."},
        {"1. Check that your Java version is 11 or above.", "2. Delete the app config folder and restart.", "3. Run as administrator.", "4. Check logs in /logs/app.log for errors."},
        {"1. Verify your payment method is valid.", "2. Check for pending transactions in your bank.", "3. Ensure subscription is active.", "4. Contact billing@fitpulse.app for disputes."},
        {"1. Check internet connection.", "2. Manually sync from Settings → Sync Data.", "3. Ensure background sync is enabled.", "4. Re-login to force data refresh."},
        {"1. Check available trainer slots.", "2. Ensure your account has no payment holds.", "3. Retry the booking after 5 minutes.", "4. Contact support if trainer is unavailable."},
        {"1. Refresh the Diet page.", "2. Ensure your profile health data is saved.", "3. Clear app cache.", "4. Restart the application."},
        {"1. Verify your Weight and Height are saved correctly.", "2. Navigate to Profile → Edit and re-save.", "3. BMI recalculates on next login.", "4. Contact support if issue persists."},
        {"1. Ensure the exercise page is fully loaded.", "2. Click Start and wait 2 seconds.", "3. If frozen, stop and restart the timer.", "4. Check for background processes."},
        {"1. Ensure all required fields are filled.", "2. Click Save and wait for confirmation.", "3. Check disk space on your device.", "4. Re-open the profile settings page."},
        {"1. Check notification permission in OS settings.", "2. Ensure Do Not Disturb is off.", "3. Go to Settings → Notifications → Enable All.", "4. Restart app."},
        {"1. Check your internet speed.", "2. Update to the latest version.", "3. Disable VPN if in use.", "4. Try a different network."},
        {"1. Describe your issue in detail below.", "2. Our team will respond within 24 hours.", "3. For urgent issues call +1-800-FIT-HELP.", "4. Visit fitpulse.app/help for FAQs."},
    };

    public SupportUI(MainFrame mainFrame) {
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
        topBar.add(Theme.createSectionTitle("Help & Support"), BorderLayout.WEST);
        topBar.add(Theme.createBadge("24/7 Support", Theme.ACCENT_GREEN), BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // ── Split layout ──────────────────────────────────────────────────────
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerSize(1);
        split.setBackground(Theme.BORDER_COLOR);
        split.setResizeWeight(0.5);

        split.setLeftComponent(buildTicketForm());
        split.setRightComponent(buildTroubleshootPanel());
        add(split, BorderLayout.CENTER);
    }

    /** Left: ticket submission form */
    private JPanel buildTicketForm() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Theme.BG_DARKEST);
        outer.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 12));

        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;

        // Title
        c.insets = new Insets(0,0,4,0);
        card.add(Theme.createLabel("Submit a Support Ticket", Theme.FONT_HEADING, Theme.TEXT_PRIMARY), c); c.gridy++;
        c.insets = new Insets(0,0,20,0);
        card.add(Theme.createLabel("We'll get back to you within 24 hours.",
                Theme.FONT_SMALL, Theme.TEXT_SECONDARY), c); c.gridy++;

        // Issue dropdown
        c.insets = new Insets(0,0,4,0);
        card.add(Theme.createLabel("ISSUE CATEGORY", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), c); c.gridy++;

        issueBox = Theme.createComboBox(ISSUES);
        issueBox.setPreferredSize(new Dimension(380, 44));
        issueBox.addActionListener(e -> onIssueSelected());
        c.insets = new Insets(0,0,16,0);
        card.add(issueBox, c); c.gridy++;

        // Description
        c.insets = new Insets(0,0,4,0);
        card.add(Theme.createLabel("DESCRIPTION", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), c); c.gridy++;

        descArea = Theme.createTextArea(6, 30);
        descArea.setPreferredSize(new Dimension(380, 120));
        JScrollPane descScroll = Theme.createScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(380, 120));
        c.insets = new Insets(0,0,16,0);
        card.add(descScroll, c); c.gridy++;

        // Priority
        c.insets = new Insets(0,0,4,0);
        card.add(Theme.createLabel("PRIORITY", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), c); c.gridy++;
        JComboBox<String> priorityBox = Theme.createComboBox(new String[]{"Low", "Medium", "High", "Critical"});
        priorityBox.setPreferredSize(new Dimension(380, 44));
        c.insets = new Insets(0,0,16,0);
        card.add(priorityBox, c); c.gridy++;

        // Status
        ticketStatusLabel = Theme.createLabel("No ticket submitted yet.", Theme.FONT_SMALL, Theme.TEXT_MUTED);
        ticketIdLabel     = Theme.createLabel("", Theme.FONT_MONO, Theme.ACCENT_CYAN);
        c.insets = new Insets(0,0,4,0);
        card.add(ticketStatusLabel, c); c.gridy++;
        c.insets = new Insets(0,0,10,0);
        card.add(ticketIdLabel, c); c.gridy++;

        // Submit button
        JButton submitBtn = Theme.createButton("Submit Ticket", Theme.ACCENT_CYAN, Theme.BG_DARKEST);
        submitBtn.setPreferredSize(new Dimension(380, 44));
        submitBtn.addActionListener(e -> handleSubmit(
                (String) issueBox.getSelectedItem(),
                descArea.getText(),
                (String) priorityBox.getSelectedItem()
        ));
        c.insets = new Insets(0,0,0,0);
        card.add(submitBtn, c);

        outer.add(card, BorderLayout.CENTER);
        return outer;
    }

    /** Right: troubleshooting steps + ticket status */
    private JPanel buildTroubleshootPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Theme.BG_DARKEST);
        outer.setBorder(BorderFactory.createEmptyBorder(20, 12, 20, 24));

        JPanel wrap = new JPanel();
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setBackground(Theme.BG_DARKEST);

        // ── Troubleshooting card ──────────────────────────────────────────────
        JPanel tsCard = Theme.createAccentCard(Theme.ACCENT_ORANGE);
        tsCard.setLayout(new BorderLayout(0, 10));

        JLabel tsTitle = Theme.createLabel("🔧  Troubleshooting Steps", Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY);
        tsCard.add(tsTitle, BorderLayout.NORTH);

        troubleshootArea = Theme.createTextArea(8, 30);
        troubleshootArea.setEditable(false);
        troubleshootArea.setForeground(Theme.TEXT_SECONDARY);
        troubleshootArea.setText("Select an issue category to see\nautomatic troubleshooting steps.");
        JScrollPane tsScroll = Theme.createScrollPane(troubleshootArea);
        tsCard.add(tsScroll, BorderLayout.CENTER);

        wrap.add(tsCard);
        wrap.add(Box.createVerticalStrut(14));

        // ── My tickets card ───────────────────────────────────────────────────
        JPanel ticketsCard = Theme.createAccentCard(Theme.ACCENT_PURPLE);
        ticketsCard.setLayout(new BorderLayout(0, 10));

        JLabel tkTitle = Theme.createLabel("📋  My Recent Tickets", Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY);
        ticketsCard.add(tkTitle, BorderLayout.NORTH);

        String[] cols = {"Ticket ID", "Issue", "Status"};
        Object[][] data = {
                {"TKT-0041", "Data not syncing",   "🟢 Resolved"},
                {"TKT-0038", "Login issue",         "🟡 In Review"},
                {"TKT-0031", "Billing question",    "🟢 Resolved"},
        };
        JTable ticketTable = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        ticketTable.setFont(Theme.FONT_SMALL);
        ticketTable.setForeground(Theme.TEXT_PRIMARY);
        ticketTable.setBackground(Theme.BG_CARD);
        ticketTable.setRowHeight(30);
        ticketTable.setGridColor(Theme.BORDER_COLOR);
        ticketTable.getTableHeader().setFont(Theme.FONT_LABEL);
        ticketTable.getTableHeader().setForeground(Theme.TEXT_SECONDARY);
        ticketTable.getTableHeader().setBackground(Theme.BG_SIDEBAR);
        ticketTable.setSelectionBackground(Theme.BG_CARD_HOVER);

        ticketsCard.add(Theme.createScrollPane(ticketTable), BorderLayout.CENTER);

        wrap.add(ticketsCard);

        // ── FAQ quick links ───────────────────────────────────────────────────
        wrap.add(Box.createVerticalStrut(14));
        JPanel faqCard = Theme.createCard();
        faqCard.setLayout(new BoxLayout(faqCard, BoxLayout.Y_AXIS));
        faqCard.add(Theme.createLabel("📚  Helpful Resources", Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY));
        faqCard.add(Box.createVerticalStrut(8));

        String[] faqs = {"How do I reset my password?", "How to export my health report?",
                "How does BMI calculation work?", "How to cancel a trainer booking?"};
        for (String faq : faqs) {
            JLabel lnk = Theme.createLabel("→  " + faq, Theme.FONT_SMALL, Theme.ACCENT_CYAN);
            lnk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lnk.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            faqCard.add(lnk);
        }
        wrap.add(faqCard);

        outer.add(Theme.createScrollPane(wrap), BorderLayout.CENTER);
        return outer;
    }

    private void onIssueSelected() {
        int idx = issueBox.getSelectedIndex();
        if (idx <= 0 || idx >= SOLUTIONS.length) {
            troubleshootArea.setText("Select an issue category to see\nautomatic troubleshooting steps.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Troubleshooting: ").append(ISSUES[idx]).append("\n");
        sb.append("─".repeat(40)).append("\n\n");
        for (String step : SOLUTIONS[idx]) {
            sb.append(step).append("\n\n");
        }
        troubleshootArea.setText(sb.toString());
        troubleshootArea.setCaretPosition(0);
    }

    private void handleSubmit(String issue, String desc, String priority) {
        if (issueBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select an issue category.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (desc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please describe your issue.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Backend integration ───────────────────────────────────────────────
         SupportTicket ticket =
        new SupportTicket("User", issue);

new SupportService().addTicket(ticket);

String ticketId =
        "TKT-" + String.format(
        "%04d",
        (int)(Math.random() * 9000) + 1000
);

ticketStatusLabel.setText(
        "✅ Ticket submitted successfully!"
);

ticketStatusLabel.setForeground(
        Theme.ACCENT_GREEN
);

ticketIdLabel.setText(
        "Your Ticket ID: " + ticketId
);

descArea.setText("");
issueBox.setSelectedIndex(0);
    }
}