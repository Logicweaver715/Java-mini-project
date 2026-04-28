package ui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    public static final String PAGE_LOGIN    = "LOGIN";
    public static final String PAGE_SIGNUP   = "SIGNUP";
    public static final String PAGE_PROFILE  = "PROFILE";
    public static final String PAGE_DASH     = "DASHBOARD";
    public static final String PAGE_EXERCISE = "EXERCISE";
    public static final String PAGE_DIET     = "DIET";
    public static final String PAGE_TRACKER  = "TRACKER";
    public static final String PAGE_BOOKING  = "BOOKING";
    public static final String PAGE_SUPPORT  = "SUPPORT";

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private NavigationPanel navPanel;

    private LoginUI loginUI;
    private SignupUI signupUI;
    private ProfileSetupUI profileSetupUI;
    private DashboardUI dashboardUI;
    private ExerciseUI exerciseUI;
    private DietUI dietUI;
    private TrackerUI trackerUI;
    private BookingUI bookingUI;
    private SupportUI supportUI;

    public MainFrame() {

        setTitle("Health & Fitness Management System");
        setSize(1280, 800);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        initPages();

        add(cardPanel, BorderLayout.CENTER);

        showPage(PAGE_LOGIN);

        setVisible(true);
        revalidate();
        repaint();
    }

    private void initPages() {

        loginUI = new LoginUI(this);
        signupUI = new SignupUI(this);
        profileSetupUI = new ProfileSetupUI(this);
        dashboardUI = new DashboardUI(this);
        exerciseUI = new ExerciseUI(this);
        dietUI = new DietUI(this);
        trackerUI = new TrackerUI(this);
        bookingUI = new BookingUI(this);
        supportUI = new SupportUI(this);

        navPanel = new NavigationPanel(this);
        navPanel.setPreferredSize(new Dimension(240, 0));
        navPanel.setMinimumSize(new Dimension(240, 0));

        cardPanel.add(loginUI, PAGE_LOGIN);
        cardPanel.add(signupUI, PAGE_SIGNUP);
        cardPanel.add(profileSetupUI, PAGE_PROFILE);

        cardPanel.add(buildAppShell(dashboardUI), PAGE_DASH);
        cardPanel.add(buildAppShell(exerciseUI), PAGE_EXERCISE);
        cardPanel.add(buildAppShell(dietUI), PAGE_DIET);
        cardPanel.add(buildAppShell(trackerUI), PAGE_TRACKER);
        cardPanel.add(buildAppShell(bookingUI), PAGE_BOOKING);
        cardPanel.add(buildAppShell(supportUI), PAGE_SUPPORT);
    }

    private JPanel buildAppShell(JPanel content) {

        JPanel shell = new JPanel(new BorderLayout());

        shell.add(new NavigationPanel(this), BorderLayout.WEST);
        shell.add(content, BorderLayout.CENTER);
        navPanel.setVisible(true);
        return shell;
    }

    public void showPage(String pageName) {

        cardLayout.show(cardPanel, pageName);

        if (navPanel != null &&
        !pageName.equals(PAGE_LOGIN) &&
       !pageName.equals(PAGE_SIGNUP) &&
       !pageName.equals(PAGE_PROFILE)) {
            navPanel.setVisible(true);
            navPanel.setActivePage(pageName);
        }

        revalidate();
        repaint();
    }

    public void onLoginSuccess(String username) {

        dashboardUI.setUsername(username);
        showPage(PAGE_DASH);
    }

    public void onLogout() {

        showPage(PAGE_LOGIN);
    }

    public DashboardUI getDashboardUI() {
        return dashboardUI;
    }

    public TrackerUI getTrackerUI() {
        return trackerUI;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}