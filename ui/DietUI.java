package ui;

import java.awt.*;
import javax.swing.*;

/**
 * DietUI.java — Daily meal plan and nutrition dashboard
 */
public class DietUI extends JPanel {

    private final MainFrame mainFrame;
    private int waterGlasses = 0;
    private JLabel waterCount;
    private JProgressBar waterBar;

    // {meal, calories, items[], icon, accentColor}
    private static final Object[][] MEALS = {
            {"Breakfast", 420, new String[]{"Oatmeal with berries","2 Boiled eggs","Green tea"}, "🌅", Theme.ACCENT_ORANGE},
            {"Lunch", 650, new String[]{"Grilled chicken 150g","Brown rice","Mixed salad","Lemon water"}, "☀", Theme.ACCENT_GREEN},
            {"Dinner", 520, new String[]{"Baked salmon 120g","Steamed broccoli","Quinoa","Herbal tea"}, "🌙", Theme.ACCENT_PURPLE},
            {"Snacks", 180, new String[]{"Apple","10 Almonds","Greek yogurt"}, "🍎", Theme.ACCENT_CYAN},
    };

    public DietUI(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARKEST);
        buildUI();
    }

    private void buildUI() {

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.BG_SIDEBAR);
        topBar.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));
        topBar.add(Theme.createSectionTitle("Daily Meal Plan"), BorderLayout.WEST);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightTop.setOpaque(false);
        rightTop.add(Theme.createBadge("1,770 kcal total", Theme.ACCENT_ORANGE));
        rightTop.add(Theme.createBadge("Today", Theme.ACCENT_CYAN));

        topBar.add(rightTop, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Theme.BG_DARKEST);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        content.add(buildNutritionSummary());
        content.add(Box.createVerticalStrut(20));

        JPanel mealGrid = new JPanel(new GridLayout(2, 2, 14, 14));
        mealGrid.setOpaque(false);

        for (Object[] meal : MEALS) {
            mealGrid.add(buildMealCard(meal));
        }

        content.add(mealGrid);
        content.add(Box.createVerticalStrut(20));

        content.add(buildWaterPanel());
        content.add(Box.createVerticalStrut(20));

        content.add(buildMacroPanel());

        add(Theme.createScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel buildNutritionSummary() {

        JPanel panel = new JPanel(new GridLayout(1,4,12,0));
        panel.setOpaque(false);

        Color[] colors = {
                Theme.ACCENT_ORANGE,
                Theme.ACCENT_CYAN,
                Theme.ACCENT_GREEN,
                Theme.ACCENT_PURPLE
        };

        String[][] data = {
                {"Calories","1770 kcal"},
                {"Protein","128 g"},
                {"Carbs","185 g"},
                {"Fat","58 g"}
        };

        for (int i = 0; i < data.length; i++) {

            JPanel card = Theme.createCard();
            card.setLayout(new BorderLayout());

            JLabel value = Theme.createLabel(
                    data[i][1],
                    new Font("SansSerif", Font.BOLD, 22),
                    colors[i]
            );

            JLabel title = Theme.createLabel(
                    data[i][0],
                    Theme.FONT_SMALL,
                    Theme.TEXT_SECONDARY
            );

            card.add(value, BorderLayout.CENTER);
            card.add(title, BorderLayout.SOUTH);

            panel.add(card);
        }

        return panel;
    }

    // ================= FULL UPDATED CARD =================
    private JPanel buildMealCard(Object[] meal) {

        String name = (String) meal[0];
        int cal = (int) meal[1];
        String[] items = (String[]) meal[2];
        String icon = (String) meal[3];
        Color color = (Color) meal[4];

        JPanel card = Theme.createAccentCard(color);
        card.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setOpaque(false);

        JLabel iconLbl = Theme.createLabel(
                icon + "  " + name,
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        JLabel calLbl = Theme.createLabel(
                cal + " kcal",
                Theme.FONT_LABEL,
                color
        );

        hdr.add(iconLbl, BorderLayout.WEST);
        hdr.add(calLbl, BorderLayout.EAST);

        c.insets = new Insets(0,0,10,0);
        card.add(hdr,c);
        c.gridy++;

        // items
        for(String item : items){

            JLabel itemLbl = Theme.createLabel(
                    "  •  " + item,
                    Theme.FONT_SMALL,
                    Theme.TEXT_SECONDARY
            );

            c.insets = new Insets(0,0,3,0);
            card.add(itemLbl,c);
            c.gridy++;
        }

        // buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT,6,0));
        btns.setOpaque(false);

        JButton editBtn = Theme.createOutlineButton("Edit", color);
        JButton swapBtn = Theme.createOutlineButton("Swap", Theme.TEXT_MUTED);

        editBtn.setPreferredSize(new Dimension(80,30));
        swapBtn.setPreferredSize(new Dimension(80,30));

        editBtn.setFont(Theme.FONT_SMALL);
        swapBtn.setFont(Theme.FONT_SMALL);

        // ===== EDIT =====
        editBtn.addActionListener(e -> {

            String newMeal = JOptionPane.showInputDialog(
                    this,
                    "Enter new meal item for " + name + ":"
            );

            if(newMeal != null && !newMeal.trim().isEmpty()){

                Container parent = card.getParent();
                parent.remove(card);

                Object[] updatedMeal = {
                        name,
                        cal,
                        new String[]{newMeal},
                        icon,
                        color
                };

                parent.add(buildMealCard(updatedMeal));
                parent.revalidate();
                parent.repaint();
            }
        });

        // ===== SWAP =====
        swapBtn.addActionListener(e -> {

            String[] swappedItems = {
                    "Idli",
                    "Sambar",
                    "Fresh Juice"
            };

            if(name.equals("Lunch")){
                swappedItems = new String[]{
                        "Grilled Paneer",
                        "Brown Rice",
                        "Salad"
                };
            }
            else if(name.equals("Dinner")){
                swappedItems = new String[]{
                        "Soup",
                        "Chapati",
                        "Vegetables"
                };
            }
            else if(name.equals("Snacks")){
                swappedItems = new String[]{
                        "Banana",
                        "Mixed Nuts",
                        "Greek Yogurt"
                };
            }

            Container parent = card.getParent();
            parent.remove(card);

            Object[] updatedMeal = {
                    name,
                    cal,
                    swappedItems,
                    icon,
                    color
            };

            parent.add(buildMealCard(updatedMeal));
            parent.revalidate();
            parent.repaint();
        });

        btns.add(editBtn);
        btns.add(swapBtn);

        c.insets = new Insets(10,0,0,0);
        card.add(btns,c);

        return card;
    }

    private JPanel buildWaterPanel() {

        JPanel card = Theme.createAccentCard(Theme.ACCENT_CYAN);
        card.setLayout(new BorderLayout(16,0));

        JLabel title = Theme.createLabel(
                "💧 Water Intake Tracker",
                Theme.FONT_SUBHEAD,
                Theme.TEXT_PRIMARY
        );

        waterCount = Theme.createLabel(
                waterGlasses + " / 8 glasses",
                Theme.FONT_SUBHEAD,
                Theme.ACCENT_CYAN
        );

        JButton plusBtn = Theme.createButton(
                "+",
                Theme.ACCENT_CYAN,
                Theme.BG_DARKEST
        );

        JButton minBtn = Theme.createOutlineButton(
                "-",
                Theme.ACCENT_CYAN
        );

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.add(minBtn);
        right.add(waterCount);
        right.add(plusBtn);

        waterBar = Theme.createProgressBar(Theme.ACCENT_CYAN);
        waterBar.setMaximum(8);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(title, BorderLayout.NORTH);
        left.add(waterBar, BorderLayout.SOUTH);

        card.add(left, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);

        plusBtn.addActionListener(e -> {
            if(waterGlasses < 8){
                waterGlasses++;
                waterCount.setText(waterGlasses + " / 8 glasses");
                waterBar.setValue(waterGlasses);
            }
        });

        minBtn.addActionListener(e -> {
            if(waterGlasses > 0){
                waterGlasses--;
                waterCount.setText(waterGlasses + " / 8 glasses");
                waterBar.setValue(waterGlasses);
            }
        });

        return card;
    }

    private JPanel buildMacroPanel() {

        JPanel card = Theme.createCard();
        card.setLayout(new GridLayout(3,1,0,8));

        card.add(buildMacroRow("Protein",64,Theme.ACCENT_CYAN));
        card.add(buildMacroRow("Carbs",55,Theme.ACCENT_ORANGE));
        card.add(buildMacroRow("Fat",29,Theme.ACCENT_PURPLE));

        return card;
    }

    private JPanel buildMacroRow(String name,int pct,Color color){

        JPanel row = new JPanel(new BorderLayout(10,0));
        row.setOpaque(false);

        JLabel lbl = Theme.createLabel(
                name,
                Theme.FONT_SMALL,
                Theme.TEXT_SECONDARY
        );

        JProgressBar bar = Theme.createProgressBar(color);
        bar.setValue(pct);

        JLabel pctLbl = Theme.createLabel(
                pct + "%",
                Theme.FONT_SMALL,
                color
        );

        row.add(lbl,BorderLayout.WEST);
        row.add(bar,BorderLayout.CENTER);
        row.add(pctLbl,BorderLayout.EAST);

        return row;
    }
}
