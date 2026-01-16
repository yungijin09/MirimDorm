import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.StringTokenizer;

public class Laundry extends JFrame {
    static JPanel laundryBox;

    Laundry() {
        setTitle("Laundry");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        laundryBox = new JPanel(new GridLayout(0, 1, 0, 5));
        laundryBox.setBorder((new EmptyBorder(20, 20, 20, 20)));
        laundryBox.setFont(new Font("laundryText", Font.BOLD, 14));
        ShowLaundry();

        JPanel wrap = new JPanel();
        wrap.add(laundryBox, BorderLayout.CENTER);
        add(new JScrollPane(wrap), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel inputInfo = new JLabel("호실 | 이름 | 학년 | 시간 : ");
        inputInfo.setFont(new Font("맑은 고딕", Font.BOLD, 12));

        JTextField input = new JTextField(18);
        JButton save = new JButton("예약");
        save.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String content = input.getText().trim();
                        StringTokenizer stk = new StringTokenizer(content, " ");
                        String roomNum = stk.nextToken();
                        String name = stk.nextToken();
                        String grade = stk.nextToken();
                        Time reserve = Time.valueOf(stk.nextToken());

                        reserveLaundry(roomNum, name, grade, reserve);

                        laundryBox.removeAll();
                        ShowLaundry();
                        laundryBox.revalidate();
                        laundryBox.repaint();
                        input.setText("");

                        JOptionPane.showMessageDialog(null, "예약 성공!");
                    }
                }
        );
        JPanel bottom = new JPanel();
        bottom.setSize(60, 40);
        JButton backBtn = new JButton("Home");
        backBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new MDormGUI();
                    }
                }
        );
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        inputPanel.add(inputInfo);
        inputPanel.add(input);
        inputPanel.add(save);

        setVisible(true);
    }

    public static void ShowLaundry() {
        String room = "";
        String name = "";
        String grade = "";
        String reserve = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "select roomNum, name, grade, reserve from laundrytbl";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                room = rs.getString("roomNum");
                name = rs.getString("name");
                grade = rs.getString("grade");
                reserve = rs.getString("reserve");
                String info = room + "호 | " + name + " | " + grade + "학년 | " + reserve;
                laundryBox.add(new JLabel(info, SwingConstants.CENTER));
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }

    public static void reserveLaundry(String roomNum, String name, String grade, Time reserve) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "insert into laundrytbl (roomNum, name, grade, reserve) values (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomNum);
            pstmt.setString(2, name);
            pstmt.setString(3, grade);
            pstmt.setTime(4, reserve);
            pstmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Laundry();
    }
}
