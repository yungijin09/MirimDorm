import javax.swing.*;
import java.awt.*;

class RoundPanel extends JPanel {
    private int radius;

    public RoundPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
    }
}

public class MDormGUI extends JFrame {
    private Image mirimLogo;

    MDormGUI() {
        setTitle("MirimDorm");
        setSize(720, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mirimLogo = new ImageIcon(
                MDormGUI.class.getResource("/images/mirimLogo.png")
        ).getImage();

        System.out.println(mirimLogo);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        JPanel topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(720, 80));
        topBar.setBackground(Color.WHITE);
        topBar.setOpaque(false);

        RoundPanel titleBox = new RoundPanel(20);
        titleBox.setBackground(new Color(68, 127, 90));
        titleBox.setBounds(20, 20, 180, 40);
        titleBox.setLayout(new BorderLayout());

        JLabel title = new JLabel("Mirim Men's Dorm", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));

        titleBox.add(title);
        topBar.add(titleBox);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setOpaque(false);

        RoundPanel menuBox = new RoundPanel(40);
        menuBox.setBackground(new Color(245, 245, 245));
        menuBox.setPreferredSize(new Dimension(500, 270));
        menuBox.setLayout(null);

        JLabel menuTitle = new JLabel("Menu", SwingConstants.CENTER);
        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 500, 30);

        menuBox.add(menuTitle);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 40, 0);

        wrapper.add(menuBox, gbc);

        backgroundPanel.add(topBar, BorderLayout.NORTH);
        backgroundPanel.add(wrapper, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MDormGUI();
    }

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(mirimLogo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
