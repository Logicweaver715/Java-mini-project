package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Theme.java — Central design system for Health & Fitness Management System
 * Dark premium UI with cyan/green/purple/orange accents
 */
public class Theme {

    // ─── COLORS ───────────────────────────────────────────────────────────────
    public static final Color BG_DARKEST    = new Color(0x0A0D14);
    public static final Color BG_DARK       = new Color(0x0F1320);
    public static final Color BG_CARD       = new Color(0x161B2E);
    public static final Color BG_CARD_HOVER = new Color(0x1E2540);
    public static final Color BG_INPUT      = new Color(0x1A1F35);
    public static final Color BG_SIDEBAR    = new Color(0x0C0F1A);

    public static final Color ACCENT_CYAN   = new Color(0x00D4FF);
    public static final Color ACCENT_GREEN  = new Color(0x00FF88);
    public static final Color ACCENT_PURPLE = new Color(0xAA55FF);
    public static final Color ACCENT_ORANGE = new Color(0xFF8800);
    public static final Color ACCENT_PINK   = new Color(0xFF4488);

    public static final Color TEXT_PRIMARY   = new Color(0xEEF2FF);
    public static final Color TEXT_SECONDARY = new Color(0x8892AA);
    public static final Color TEXT_MUTED     = new Color(0x4A5270);
    public static final Color BORDER_COLOR   = new Color(0x252D45);
    public static final Color DIVIDER        = new Color(0x1E2540);

    // ─── FONTS ────────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE   = new Font("SansSerif", Font.BOLD,  26);
    public static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD,  18);
    public static final Font FONT_SUBHEAD = new Font("SansSerif", Font.BOLD,  14);
    public static final Font FONT_BODY    = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("SansSerif", Font.PLAIN, 11);
    public static final Font FONT_LABEL   = new Font("SansSerif", Font.BOLD,  12);
    public static final Font FONT_MONO    = new Font("Monospaced", Font.BOLD, 13);
    public static final Font FONT_NAV     = new Font("SansSerif", Font.BOLD,  13);
    public static final Font FONT_CARD_NUM= new Font("SansSerif", Font.BOLD,  28);

    // ─── ROUNDED BUTTON ───────────────────────────────────────────────────────
    public static JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = hovered ? bg.brighter() : bg;
                if (hovered) {
                    g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 220));
                } else {
                    g2.setColor(base);
                }
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                // glow effect on hover
                if (hovered) {
                    g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 60));
                    g2.setStroke(new BasicStroke(3f));
                    g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 12, 12));
                }
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) { /* no default border */ }
        };
        btn.setFont(FONT_SUBHEAD);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 42));
        return btn;
    }

    /** Outlined ghost button */
    public static JButton createOutlineButton(String text, Color accent) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hovered) {
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 30));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                }
                g2.setColor(accent);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(FONT_SUBHEAD);
        btn.setForeground(accent);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 40));
        return btn;
    }

    // ─── STYLED TEXT FIELD ────────────────────────────────────────────────────
    public static JTextField createTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isFocusOwner() ? ACCENT_CYAN : BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 10, 10));
                g2.dispose();
            }
        };
        field.setFont(FONT_BODY);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(BG_INPUT);
        field.setCaretColor(ACCENT_CYAN);
        field.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        field.setOpaque(false);
        field.setPreferredSize(new Dimension(280, 44));
        // placeholder
        field.putClientProperty("placeholder", placeholder);
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { field.repaint(); }
            @Override public void focusLost(FocusEvent e)   { field.repaint(); }
        });
        return field;
    }

    /** Password field styled like text field */
    public static JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isFocusOwner() ? ACCENT_CYAN : BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 10, 10));
                g2.dispose();
            }
        };
        field.setFont(FONT_BODY);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(BG_INPUT);
        field.setCaretColor(ACCENT_CYAN);
        field.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        field.setOpaque(false);
        field.setEchoChar('●');
        field.setPreferredSize(new Dimension(280, 44));
        return field;
    }

    /** Styled JComboBox */
    public static <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> box = new JComboBox<>(items);
        box.setFont(FONT_BODY);
        box.setForeground(TEXT_PRIMARY);
        box.setBackground(BG_INPUT);
        box.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        box.setPreferredSize(new Dimension(280, 44));
        return box;
    }

    // ─── CARD PANEL ───────────────────────────────────────────────────────────
    public static JPanel createCard() {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        return card;
    }

    /** Card with colored top accent stripe */
    public static JPanel createAccentCard(Color accent) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), 6, 4, 4));
                g2.setColor(accent);
                g2.fillRect(0, 0, getWidth(), 4);
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 16, 16, 16));
        return card;
    }

    // ─── SECTION TITLE ────────────────────────────────────────────────────────
    public static JLabel createSectionTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_HEADING);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        return lbl;
    }

    /** Horizontal divider line */
    public static JSeparator createDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(DIVIDER);
        sep.setBackground(DIVIDER);
        return sep;
    }

    /** Scrollpane styled for dark theme */
    public static JScrollPane createScrollPane(Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        sp.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        sp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 6));
        return sp;
    }

    /** Dark minimal scrollbar UI */
    static class DarkScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override protected void configureScrollBarColors() {
            thumbColor      = new Color(0x2A3150);
            trackColor      = new Color(0x10141F);
        }
        @Override protected JButton createDecreaseButton(int o) { return createZeroButton(); }
        @Override protected JButton createIncreaseButton(int o) { return createZeroButton(); }
        private JButton createZeroButton() {
            JButton b = new JButton(); b.setPreferredSize(new Dimension(0,0)); return b;
        }
        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 6, 6));
            g2.dispose();
        }
    }

    /** Styled JTextArea */
    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea ta = new JTextArea(rows, cols);
        ta.setFont(FONT_BODY);
        ta.setForeground(TEXT_PRIMARY);
        ta.setBackground(BG_INPUT);
        ta.setCaretColor(ACCENT_CYAN);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        return ta;
    }

    /** Badge label */
    public static JLabel createBadge(String text, Color bg) {
        JLabel badge = new JLabel(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 30));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(bg);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 20, 20));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(FONT_SMALL);
        badge.setForeground(bg);
        badge.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        badge.setOpaque(false);
        return badge;
    }

    /** Progress bar styled */
    public static JProgressBar createProgressBar(Color accent) {
        JProgressBar bar = new JProgressBar() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                int fillW = (int)(getWidth() * getPercentComplete());
                if (fillW > 0) {
                    GradientPaint gp = new GradientPaint(0, 0, accent, fillW, 0,
                        accent.darker());
                    g2.setPaint(gp);
                    g2.fill(new RoundRectangle2D.Float(0, 0, fillW, getHeight(), 8, 8));
                }
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setBorderPainted(false);
        bar.setStringPainted(false);
        bar.setPreferredSize(new Dimension(200, 8));
        return bar;
    }
}