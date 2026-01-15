import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.StringTokenizer;

public class Comeback extends JFrame {
    static JPanel comebackBox;

    Comeback() {
        setTitle("Comeback");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        comebackBox = new JPanel(new GridLayout(0, 1, 0, 5));
        comebackBox.setBorder(new EmptyBorder(20, 20, 20, 20));
        comebackBox.setFont(new Font("comebackText", Font.BOLD, 14));
        ComebackTimeInfo();

        JPanel wrap = new JPanel();
        wrap.add(comebackBox, BorderLayout.NORTH);
        add(new JScrollPane(wrap), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel inputInfo = new JLabel("호실 | 이름 | 복귀 시간 | 복귀 여부 : ");
        inputInfo.setFont(new Font("맑은 고딕", Font.BOLD, 12));

        // JTextField(글자수)를 지정해야 빈 칸일 때도 모양이 유지됩니다.
        JTextField input = new JTextField(18);
        JButton save = new JButton("저장");
        save.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String content = input.getText().trim();
                        StringTokenizer stk = new StringTokenizer(content, " ");
                        String roomNum = stk.nextToken();
                        String name = stk.nextToken();
                        Time combackTime = Time.valueOf(stk.nextToken());
                        boolean returnT = Boolean.parseBoolean(stk.nextToken());

                        inputComebackTime(roomNum, name, combackTime, returnT);

                        comebackBox.removeAll();      // 기존에 그려진 라벨들 싹 지우기
                        ComebackTimeInfo();           // DB에서 최신 데이터 다시 가져와서 add 하기
                        comebackBox.revalidate();     // 레이아웃 재계산
                        comebackBox.repaint();        // 화면 다시 그리기
                        input.setText("");
                        JOptionPane.showMessageDialog(null, "저장 완료!");
                    }
                }
        );
        JPanel bottom = new JPanel();
        bottom.setSize(60,40);
        JButton backBtn = new JButton("home");
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
        // 프레임의 NORTH(상단)에 배치
        add(inputPanel, BorderLayout.NORTH);
        inputPanel.add(inputInfo);
        inputPanel.add(input);
        inputPanel.add(save);
        setVisible(true);
    }

    public static void ComebackTimeInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "SELECT * FROM comebacktime;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String roomNum = rs.getString("roomNum");
                String name = rs.getString("name");
                Time comebackTime = rs.getTime("comebackTime");
                boolean returnT = rs.getBoolean("returnT");
                String flag = "미복귀";
                if (returnT) flag = "복귀";
                String info = roomNum + "호 | " + name + " | " + comebackTime + " | " + flag;
                comebackBox.add(new JLabel(info, SwingConstants.CENTER));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void inputComebackTime(String roomNum, String name, Time comebackTime, boolean returnT) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "INSERT into comebacktime (roomNum, name, comebackTime, returnT) value(?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, roomNum);
            pstmt.setString(2, name);
            pstmt.setTime(3, comebackTime);
            pstmt.setBoolean(4, returnT);
            pstmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Comeback();
    }
}