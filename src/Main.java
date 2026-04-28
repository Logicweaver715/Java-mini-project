import javax.swing.SwingUtilities;
import ui.MainFrame;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                new MainFrame();
            }
        });
    }
}