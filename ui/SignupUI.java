package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  
import service.AuthService;
/**
 * SignupUI.java — Create new account screen
 * Compatible with AuthService.register(username, password)
 */
public class SignupUI extends JPanel {

    private final MainFrame mainFrame;

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel         errorLabel;
    private JButton        createButton;
    private JButton        backButton;

    private AuthService authService = new AuthService();

    public SignupUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);
        buildUI();
    }

    private void buildUI() {
        // ── Decorative left bar ───────────────────────────────────────────────
        JPanel accent = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, Theme.ACCENT_PURPLE,
                        0, getHeight(), Theme.ACCENT_CYAN);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        accent.setPreferredSize(new Dimension(6, 0));
        add(accent, BorderLayout.WEST);

        // ── Centre form ───────────────────────────────────────────────────────
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Theme.BG_DARKEST);

        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(420, 560));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0);

        // Header
        JLabel icon = Theme.createLabel("🏋", new Font("SansSerif", Font.PLAIN, 32), Theme.ACCENT_PURPLE);
        c.anchor = GridBagConstraints.CENTER;
        card.add(icon, c); c.gridy++;

        JLabel title = Theme.createLabel("Create Your Account", new Font("SansSerif", Font.BOLD, 22), Theme.TEXT_PRIMARY);
        c.insets = new Insets(0, 0, 4, 0);
        card.add(title, c); c.gridy++;

        JLabel sub = Theme.createLabel("Start your fitness transformation today", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        c.insets = new Insets(0, 0, 24, 0);
        card.add(sub, c); c.gridy++;

        c.anchor = GridBagConstraints.WEST;

        // Username
        card.add(Theme.createLabel("USERNAME", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), c); c.gridy++;
        usernameField = Theme.createTextField("Choose a username");
        usernameField.setPreferredSize(new Dimension(360, 44));
        c.insets = new Insets(4, 0, 16, 0);
        card.add(usernameField, c); c.gridy++;

        // Password
        c.insets = new Insets(0, 0, 4, 0);
        card.add(Theme.createLabel("PASSWORD", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), c); c.gridy++;
        passwordField = Theme.createPasswordField("Min. 8 characters");
        passwordField.setPreferredSize(new Dimension(360, 44));
        c.insets = new Insets(4, 0, 16, 0);
        card.add(passwordField, c); c.gridy++;

        // Confirm Password
        c.insets = new Insets(0, 0, 4, 0);
        card.add(Theme.createLabel("CONFIRM PASSWORD", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), c); c.gridy++;
        confirmField = Theme.createPasswordField("Re-enter password");
        confirmField.setPreferredSize(new Dimension(360, 44));
        c.insets = new Insets(4, 0, 16, 0);
        card.add(confirmField, c); c.gridy++;

        // Password strength indicator
        JPanel strengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        strengthPanel.setOpaque(false);
        JLabel strengthLbl = Theme.createLabel("Password strength: ", Theme.FONT_SMALL, Theme.TEXT_MUTED);
        JLabel strengthVal = Theme.createLabel("—", Theme.FONT_SMALL, Theme.TEXT_MUTED);
        strengthPanel.add(strengthLbl);
        strengthPanel.add(strengthVal);
        c.insets = new Insets(0, 0, 8, 0);
        card.add(strengthPanel, c); c.gridy++;

        passwordField.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                String pw = new String(passwordField.getPassword());
                if (pw.length() < 6) {
                    strengthVal.setText("Weak"); strengthVal.setForeground(Theme.ACCENT_PINK);
                } else if (pw.length() < 10) {
                    strengthVal.setText("Medium"); strengthVal.setForeground(Theme.ACCENT_ORANGE);
                } else {
                    strengthVal.setText("Strong"); strengthVal.setForeground(Theme.ACCENT_GREEN);
                }
            }
        });

        // Error label
        errorLabel = Theme.createLabel("", Theme.FONT_SMALL, Theme.ACCENT_PINK);
        c.insets = new Insets(0, 0, 8, 0);
        card.add(errorLabel, c); c.gridy++;

        // Create button
        createButton = Theme.createButton("Create Account", Theme.ACCENT_PURPLE, Theme.TEXT_PRIMARY);
        createButton.setPreferredSize(new Dimension(360, 46));
        c.insets = new Insets(0, 0, 10, 0);
        card.add(createButton, c); c.gridy++;

        // Back to login
        backButton = Theme.createOutlineButton("Back to Sign In", Theme.ACCENT_CYAN);
        backButton.setPreferredSize(new Dimension(360, 44));
        c.insets = new Insets(0, 0, 0, 0);
        card.add(backButton, c);

        center.add(card);
        add(center, BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────────────────────
        createButton.addActionListener(e -> handleSignup());
        backButton.addActionListener(e -> mainFrame.showPage(MainFrame.PAGE_LOGIN));
    }

    private void handleSignup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirm  = new String(confirmField.getPassword()).trim();
         String email =
            username + "@fitpulse.com";
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }
        if (password.length() < 8) {
            errorLabel.setText("Password must be at least 8 characters.");
            return;
        }
        if (!password.equals(confirm)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        // ── Backend integration ───────────────────────────────────────────────
        boolean success = authService.signup(username, password,email);
         if (success) {
             errorLabel.setText("");
             mainFrame.showPage(MainFrame.PAGE_PROFILE);
         } else {
             errorLabel.setText("Username already exists.");
         }

        // ── Demo logic ────────────────────────────────────────────────────────
    }
}

