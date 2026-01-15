import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class MorningSong extends JFrame {

    MorningSong() {
        setTitle("Morning Song");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 전체 레이아웃
        setLayout(new GridLayout(1, 2, 10, 0));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel leftTitle = new JLabel("기상송 신청 현황", SwingConstants.CENTER);
        leftTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));

        JTextArea statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        statusArea.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(statusArea);

        leftPanel.add(leftTitle, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel rightTitle = new JLabel("기상송 신청");
        rightTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rightTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font labelFont = new Font("맑은 고딕", Font.PLAIN, 12);

        JLabel singerLabel = new JLabel("가수 이름");
        singerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        singerLabel.setFont(labelFont);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(200, 25));

        JLabel songLabel = new JLabel("노래 제목");
        songLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        songLabel.setFont(labelFont);

        JTextField songField = new JTextField();
        songField.setMaximumSize(new Dimension(200, 25));

        JButton submitBtn = new JButton("신청");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        submitBtn.setBackground(new Color(70, 130, 180));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setOpaque(true);

        submitBtn.setBorder(new RoundedBorder(15, new Color(70, 130, 180)));
        submitBtn.setContentAreaFilled(false);
        submitBtn.setOpaque(false);


        rightPanel.add(rightTitle);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(singerLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(nameField);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(songLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(songField);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(submitBtn);

        add(leftPanel);
        add(rightPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        new MorningSong();
    }
}

class RoundedBorder extends AbstractBorder {

    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g,
                            int x, int y, int width, int height) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setColor(color);
        g2.drawRoundRect(
                x, y, width - 1, height - 1,
                radius, radius
        );
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 10, 10, 10);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(10, 10, 10, 10);
        return insets;
    }
}
