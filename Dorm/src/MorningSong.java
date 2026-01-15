import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.sql.*;

public class MorningSong extends JFrame {

    static JTextArea statusArea;

    MorningSong() {
        setTitle("Morning Song");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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

        statusArea = new JTextArea();
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
        singerLabel.setFont(labelFont);
        singerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField singerField = new JTextField();
        singerField.setMaximumSize(new Dimension(200, 25));

        JLabel songLabel = new JLabel("노래 제목");
        songLabel.setFont(labelFont);
        songLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField songField = new JTextField();
        songField.setMaximumSize(new Dimension(200, 25));

        JButton submitBtn = new JButton("신청");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        submitBtn.setBackground(new Color(70, 130, 180));
        submitBtn.setForeground(Color.WHITE);

        submitBtn.addActionListener(e -> {
            String s1 = songField.getText();
            String s2 = singerField.getText();

            if (s1.equals("") || s2.equals("")) {
                JOptionPane.showMessageDialog(null, "모든 칸을 입력해주세요.");
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
                String sql = "insert into morningsong (songName, singerName) value(?, ?);";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, s1);
                pstmt.setString(2, s2);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "신청 완료!");

                dispose();
                new MorningSong();

            } catch (SQLException | ClassNotFoundException E){
                System.out.println("DB 연결 오류");
                E.printStackTrace();
            }
        });

        rightPanel.add(rightTitle);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(singerLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(singerField);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(songLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(songField);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(submitBtn);

        add(leftPanel);
        add(rightPanel);

        morningSongInfo();
        setVisible(true);
    }

    public static void morningSongInfo() {
        statusArea.setText("");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dorm_db",
                    "root",
                    "Dldlgkwns@12"
            );

            String sql = "SELECT songName, singerName FROM morningsong";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String song = rs.getString("songName");
                String singer = rs.getString("singerName");
                statusArea.append(song + " - " + singer + "\n");
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 10, 10, 10);
    }
}
