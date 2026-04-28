package ui;

import java.awt.*;
import javax.swing.*;
import model.UserProfile;
import service.ProfileService;

/**
 * ProfileSetupUI.java — Onboarding profile setup
 * Compatible with UserProfile model
 */
public class ProfileSetupUI extends JPanel {

    private final MainFrame mainFrame;

    // Form fields
    private JTextField nameField, ageField, weightField, heightField;
    private JTextField bpField, sugarField, sleepField;
    private JComboBox<String> genderBox, goalBox, levelBox;
    private JButton   saveButton;
    private JLabel    errorLabel;

    public ProfileSetupUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);
        buildUI();
    }

    private void buildUI() {
        // ── Top header ────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.BG_SIDEBAR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 32, 20, 32));

        JLabel title = Theme.createLabel("Set Up Your Health Profile", Theme.FONT_TITLE, Theme.TEXT_PRIMARY);
        JLabel sub   = Theme.createLabel("Tell us about yourself to personalise your experience",
                Theme.FONT_BODY, Theme.TEXT_SECONDARY);

        JPanel htxt = new JPanel();
        htxt.setOpaque(false);
        htxt.setLayout(new BoxLayout(htxt, BoxLayout.Y_AXIS));
        htxt.add(title);
        htxt.add(Box.createVerticalStrut(4));
        htxt.add(sub);
        header.add(htxt, BorderLayout.WEST);

        JLabel step = Theme.createBadge("Step 2 of 2", Theme.ACCENT_PURPLE);
        header.add(step, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ── Scrollable form body ──────────────────────────────────────────────
        JPanel formBody = new JPanel(new GridBagLayout());
        formBody.setBackground(Theme.BG_DARKEST);
        formBody.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        int row = 0;

        // ── Section: Personal Info ────────────────────────────────────────────
        row = addSectionHeader(formBody, "Personal Information", Theme.ACCENT_CYAN, row);

        JPanel row1 = buildFieldRow(
                "Full Name", nameField = Theme.createTextField("e.g. Alex Johnson"),
                "Age",       ageField  = Theme.createTextField("e.g. 25")
        );
        addRow(formBody, row1, gbc, row++);

        JPanel row2 = buildComboRow(
                "Gender", genderBox = Theme.createComboBox(new String[]{"Male", "Female", "Other"}),
                "Fitness Level", levelBox = Theme.createComboBox(new String[]{"Beginner", "Intermediate", "Advanced"})
        );
        addRow(formBody, row2, gbc, row++);

        // ── Section: Body Metrics ─────────────────────────────────────────────
        row = addSectionHeader(formBody, "Body Metrics", Theme.ACCENT_GREEN, row);

        JPanel row3 = buildFieldRow(
                "Weight (kg)", weightField = Theme.createTextField("e.g. 70"),
                "Height (cm)", heightField = Theme.createTextField("e.g. 175")
        );
        addRow(formBody, row3, gbc, row++);

        JPanel row4 = buildFieldRow(
                "Blood Pressure", bpField    = Theme.createTextField("e.g. 120/80"),
                "Blood Sugar (mg/dL)", sugarField = Theme.createTextField("e.g. 90")
        );
        addRow(formBody, row4, gbc, row++);

        JPanel row5 = buildFieldRow(
                "Avg Sleep Hours", sleepField = Theme.createTextField("e.g. 7"),
                null, null
        );
        addRow(formBody, row5, gbc, row++);

        // ── Section: Goals ────────────────────────────────────────────────────
        row = addSectionHeader(formBody, "Fitness Goals", Theme.ACCENT_ORANGE, row);

        JPanel row6 = buildComboRow(
                "Primary Goal", goalBox = Theme.createComboBox(new String[]{
                        "Lose Weight", "Build Muscle", "Improve Stamina", "Stay Active",
                        "Reduce Stress", "Improve Flexibility", "Gain Weight"
                }),
                null, null
        );
        addRow(formBody, row6, gbc, row++);

        // Error + save
        errorLabel = Theme.createLabel("", Theme.FONT_SMALL, Theme.ACCENT_PINK);
        GridBagConstraints ec = new GridBagConstraints();
        ec.gridx = 0; ec.gridy = row++;
        ec.fill = GridBagConstraints.HORIZONTAL;
        ec.insets = new Insets(8, 0, 4, 0);
        formBody.add(errorLabel, ec);

        saveButton = Theme.createButton("Save Profile & Continue →", Theme.ACCENT_GREEN, Theme.BG_DARKEST);
        saveButton.setPreferredSize(new Dimension(300, 46));
        GridBagConstraints sc = new GridBagConstraints();
        sc.gridx = 0; sc.gridy = row++;
        sc.anchor = GridBagConstraints.WEST;
        sc.insets = new Insets(0, 0, 20, 0);
        formBody.add(saveButton, sc);

        JScrollPane scroll = Theme.createScrollPane(formBody);
        scroll.setBackground(Theme.BG_DARKEST);
        add(scroll, BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────────────────────
        saveButton.addActionListener(e -> handleSave());
    }

    private int addSectionHeader(JPanel panel, String title, Color accent, int row) {
        JPanel hRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        hRow.setOpaque(false);
        JLabel icon = Theme.createLabel("●", Theme.FONT_SUBHEAD, accent);
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        JLabel lbl = Theme.createLabel(title, Theme.FONT_SUBHEAD, Theme.TEXT_PRIMARY);
        hRow.add(icon);
        hRow.add(lbl);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 0, 8, 0);
        panel.add(hRow, c);
        return row + 1;
    }

    private JPanel buildFieldRow(String lbl1, JTextField f1, String lbl2, JTextField f2) {
        JPanel row = new JPanel(new GridLayout(1, f2 == null ? 1 : 2, 20, 0));
        row.setOpaque(false);
        row.add(buildLabeledField(lbl1, f1));
        if (f2 != null) row.add(buildLabeledField(lbl2, f2));
        return row;
    }

    private JPanel buildComboRow(String lbl1, JComboBox<String> c1, String lbl2, JComboBox<String> c2) {
        JPanel row = new JPanel(new GridLayout(1, c2 == null ? 1 : 2, 20, 0));
        row.setOpaque(false);
        row.add(buildLabeledCombo(lbl1, c1));
        if (c2 != null) row.add(buildLabeledCombo(lbl2, c2));
        return row;
    }

    private JPanel buildLabeledField(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        p.add(Theme.createLabel(label.toUpperCase(), Theme.FONT_LABEL, Theme.TEXT_SECONDARY), BorderLayout.NORTH);
        field.setPreferredSize(new Dimension(260, 44));
        p.add(field, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        return p;
    }

    private JPanel buildLabeledCombo(String label, JComboBox<String> combo) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        p.add(Theme.createLabel(label.toUpperCase(), Theme.FONT_LABEL, Theme.TEXT_SECONDARY), BorderLayout.NORTH);
        combo.setPreferredSize(new Dimension(260, 44));
        p.add(combo, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        return p;
    }

    private void addRow(JPanel panel, JPanel row, GridBagConstraints gbc, int rowIdx) {
        gbc.gridx = 0; gbc.gridy = rowIdx;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(row, gbc);
    }

    private void handleSave() {
        // Validation
        if (nameField.getText().trim().isEmpty()) {
            errorLabel.setText("Name is required.");
            return;
        }
        try { Integer.parseInt(ageField.getText().trim()); }
        catch (NumberFormatException e) { errorLabel.setText("Age must be a valid number."); return; }

        // ── Backend integration ───────────────────────────────────────────────
         UserProfile profile = new UserProfile(

    nameField.getText().trim(),

    Integer.parseInt(ageField.getText().trim()),

    (String) genderBox.getSelectedItem(),

    Double.parseDouble(weightField.getText().trim()),

    Double.parseDouble(heightField.getText().trim()),

    Integer.parseInt(bpField.getText().trim()),

    Integer.parseInt(sugarField.getText().trim()),

    Integer.parseInt(sleepField.getText().trim()),

    0,

    (String) goalBox.getSelectedItem(),

    (String) levelBox.getSelectedItem(),

    "None"
);

    new ProfileService().saveProfile(profile);
        errorLabel.setText("");
        mainFrame.showPage(MainFrame.PAGE_DASH);
    }

    // ── Public getter for profile data (optional for Dashboard refresh) ───────
    public String getGoal()  { return (String) goalBox.getSelectedItem(); }
    public String getLevel() { return (String) levelBox.getSelectedItem(); }
}