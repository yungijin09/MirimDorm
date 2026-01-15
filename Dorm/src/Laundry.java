import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Laundry extends JFrame {
    static JPanel laundryBox;
    Laundry(){
        setTitle("Laundry");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        laundryBox = new JPanel(new GridLayout(8, 4, 0, 0));
        laundryBox.setBorder((new EmptyBorder(20, 20, 20, 20)));
        laundryBox.setFont(new Font("laundryText", Font.BOLD, 14));

    }

    public static void main(String[] args) {
        new Laundry();
    }
}
