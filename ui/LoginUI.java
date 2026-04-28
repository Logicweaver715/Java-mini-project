package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import service.AuthService;

/**
 * LoginUI.java — Premium dark login page
 * Compatible with AuthService.login(username, password)
 */
public class LoginUI extends JPanel {

    private final MainFrame mainFrame;

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JCheckBox      showPasswordBox;
    private JButton        loginButton;
    private JButton        signupButton;
    private JLabel         errorLabel;

    // ── backend hook ──
     private AuthService authService = new AuthService();

    public LoginUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);
        buildUI();
    }

    private void buildUI() {
        // ── Left: branding panel ──────────────────────────────────────────────
        JPanel brandPanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(0x0A0D20),
                        getWidth(), getHeight(), new Color(0x0C1428));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // decorative circles
                g2.setColor(new Color(0x00D4FF, false).darker());
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.06f));
                g2.fillOval(-60, -60, 350, 350);
                g2.setColor(new Color(0x00FF88, false));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
                g2.fillOval(200, 300, 280, 280);
                g2.dispose();
            }
        };
        brandPanel.setPreferredSize(new Dimension(480, 0));
        brandPanel.setBackground(new Color(0x0A0D20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(6, 20, 6, 20);

        // Logo icon placeholder
        JLabel logoIcon = new JLabel("⚡") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0x00D4FF, false));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
                g2.fillOval(0, 0, 80, 80);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logoIcon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        logoIcon.setForeground(Theme.ACCENT_CYAN);
        logoIcon.setHorizontalAlignment(SwingConstants.CENTER);
        logoIcon.setPreferredSize(new Dimension(80, 80));

        JLabel appName = Theme.createLabel("FitPulse", Theme.FONT_TITLE, Theme.TEXT_PRIMARY);
        appName.setFont(new Font("SansSerif", Font.BOLD, 34));

        JLabel tagline = Theme.createLabel("Your Premium Health Companion", Theme.FONT_BODY, Theme.TEXT_SECONDARY);

        JPanel featurePanel = new JPanel();
        featurePanel.setOpaque(false);
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));
        String[] features = { "✦  AI-Powered Health Analysis", "✦  Personalised Diet & Exercise Plans",
                "✦  Real-Time Workout Tracking", "✦  Expert Trainer Booking" };
        for (String f : features) {
            JLabel fl = Theme.createLabel(f, Theme.FONT_BODY, Theme.TEXT_SECONDARY);
            fl.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
            featurePanel.add(fl);
        }

        brandPanel.add(logoIcon,     gbc);
        brandPanel.add(appName,      gbc);
        brandPanel.add(tagline,      gbc);
        gbc.insets = new Insets(24, 20, 6, 20);
        brandPanel.add(featurePanel, gbc);

        // ── Right: form panel ─────────────────────────────────────────────────
        JPanel formOuter = new JPanel(new GridBagLayout());
        formOuter.setBackground(Theme.BG_DARKEST);

        JPanel formCard = Theme.createCard();
        formCard.setLayout(new GridBagLayout());
        formCard.setPreferredSize(new Dimension(400, 520));

        GridBagConstraints fc = new GridBagConstraints();
        fc.gridx = 0; fc.gridy = 0;
        fc.fill = GridBagConstraints.HORIZONTAL;
        fc.insets = new Insets(0, 0, 6, 0);

        // Heading
        JLabel heading = Theme.createLabel("Welcome Back", new Font("SansSerif", Font.BOLD, 24), Theme.TEXT_PRIMARY);
        fc.insets = new Insets(0, 0, 4, 0);
        formCard.add(heading, fc); fc.gridy++;

        JLabel sub = Theme.createLabel("Sign in to continue your fitness journey", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        fc.insets = new Insets(0, 0, 24, 0);
        formCard.add(sub, fc); fc.gridy++;

        // Username
        JLabel userLbl = Theme.createLabel("USERNAME", Theme.FONT_LABEL, Theme.TEXT_SECONDARY);
        fc.insets = new Insets(0, 0, 6, 0);
        formCard.add(userLbl, fc); fc.gridy++;

        usernameField = Theme.createTextField("Enter your username");
        usernameField.setPreferredSize(new Dimension(340, 44));
        fc.insets = new Insets(0, 0, 16, 0);
        formCard.add(usernameField, fc); fc.gridy++;

        // Password label row
        JPanel pwLabelRow = new JPanel(new BorderLayout());
        pwLabelRow.setOpaque(false);
        pwLabelRow.add(Theme.createLabel("PASSWORD", Theme.FONT_LABEL, Theme.TEXT_SECONDARY), BorderLayout.WEST);
        JLabel forgotLbl = Theme.createLabel("Forgot password?", Theme.FONT_SMALL, Theme.ACCENT_CYAN);
        forgotLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pwLabelRow.add(forgotLbl, BorderLayout.EAST);
        fc.insets = new Insets(0, 0, 6, 0);
        formCard.add(pwLabelRow, fc); fc.gridy++;

        passwordField = Theme.createPasswordField("Enter your password");
        passwordField.setPreferredSize(new Dimension(340, 44));
        fc.insets = new Insets(0, 0, 8, 0);
        formCard.add(passwordField, fc); fc.gridy++;

        // Show password checkbox
        showPasswordBox = new JCheckBox("Show password");
        showPasswordBox.setFont(Theme.FONT_SMALL);
        showPasswordBox.setForeground(Theme.TEXT_SECONDARY);
        showPasswordBox.setBackground(Theme.BG_CARD);
        showPasswordBox.setOpaque(false);
        showPasswordBox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordBox.isSelected() ? '\0' : '●');
        });
        fc.insets = new Insets(0, 0, 16, 0);
        formCard.add(showPasswordBox, fc); fc.gridy++;

        // Error label
        errorLabel = Theme.createLabel("", Theme.FONT_SMALL, Theme.ACCENT_PINK);
        fc.insets = new Insets(0, 0, 8, 0);
        formCard.add(errorLabel, fc); fc.gridy++;

        // Login button
        loginButton = Theme.createButton("Sign In", Theme.ACCENT_CYAN, Theme.BG_DARKEST);
        loginButton.setPreferredSize(new Dimension(340, 46));
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        fc.insets = new Insets(0, 0, 12, 0);
        formCard.add(loginButton, fc); fc.gridy++;

        // Divider
        JPanel divRow = new JPanel(new GridLayout(1, 3, 8, 0));
        divRow.setOpaque(false);
        divRow.add(Theme.createDivider());
        JLabel orLbl = Theme.createLabel("OR", Theme.FONT_SMALL, Theme.TEXT_MUTED);
        orLbl.setHorizontalAlignment(SwingConstants.CENTER);
        divRow.add(orLbl);
        divRow.add(Theme.createDivider());
        fc.insets = new Insets(0, 0, 12, 0);
        formCard.add(divRow, fc); fc.gridy++;

        // Signup link
        signupButton = Theme.createOutlineButton("Create New Account", Theme.ACCENT_GREEN);
        signupButton.setPreferredSize(new Dimension(340, 44));
        fc.insets = new Insets(0, 0, 0, 0);
        formCard.add(signupButton, fc);

        formOuter.add(formCard);
        add(brandPanel, BorderLayout.WEST);
        add(formOuter,  BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────────────────────
        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> mainFrame.showPage(MainFrame.PAGE_SIGNUP));

        // Enter key triggers login
        KeyAdapter enter = new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) handleLogin();
            }
        };
        usernameField.addKeyListener(enter);
        passwordField.addKeyListener(enter);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        // ── Backend integration ───────────────────────────────────────────────
         boolean success = authService.login(username, password);
         if (success) {
            errorLabel.setText("");
            mainFrame.onLoginSuccess(username);
         } else {
             errorLabel.setText("Invalid username or password.");
         }

        // ── Demo / placeholder logic ──────────────────────────────────────────
        
}
}