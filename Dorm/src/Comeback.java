import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Comeback extends JFrame {
    static JPanel comebackBox;
    Comeback(){
        setTitle("Comeback");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        comebackBox = new JPanel(new GridLayout(8,4, 0, 0));
        comebackBox.setBorder(new EmptyBorder(20, 20, 20, 20));
        comebackBox.setFont(new Font("comebackText", Font.BOLD, 14));
        ComebackTimeInfo();
        add(new JScrollPane(comebackBox));
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
                if(returnT) flag = "복귀";
                String info = roomNum + "호 | " + name + " | " + comebackTime + " | " + flag;
                comebackBox.add(new JLabel(info, SwingConstants.CENTER));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Comeback();
    }
}
