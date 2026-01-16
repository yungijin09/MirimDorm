import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Point extends JFrame{
    static JPanel pointBox;
    Point(){
        setTitle("Point");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pointBox = new JPanel(new GridLayout(8,4, 0, 0));
        pointBox.setBorder(new EmptyBorder(20, 20, 20, 20));
        pointBox.setFont(new Font("comebackText", Font.BOLD, 14));
        pointInfo();
        add(new JScrollPane(pointBox));
        setVisible(true);
    }

    public void pointInfo(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "SELECT * FROM penalty;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                String name = rs.getString("name");
                int point = rs.getInt("point");
                String info = "이름: " + name + " | " + " " + point;
                pointBox.add(new JLabel(info, SwingConstants.CENTER));
            }
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }

        JPanel bottom = new JPanel();
        bottom.setSize(60, 40);
        JButton homeBtn = new JButton("Home");

        homeBtn.addActionListener(e -> {
            dispose();
            new MDormGUI();
        });

        bottom.add(homeBtn);
        add(bottom, BorderLayout.SOUTH);

    }

    static void main() {
        new Point();
    }
}
