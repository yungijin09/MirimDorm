import java.sql.*;
import java.util.*;

public class DormMain {
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
            System.out.println("입력 성공!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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

                System.out.println(roomNum + "호 " + name + " " + comebackTime + " " + flag);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inputMorningSong(String songName, String singerName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "INSERT into morningsong (songName, singerName) value(?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, songName);
            pstmt.setString(2, singerName);
            pstmt.executeUpdate();
            System.out.println("신청 완료!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void reserveLaundry(String roomNum, String name, int grade, Time reserve) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "insert into laundrytbl (roomNum, name, grade, reserve) values (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomNum);
            pstmt.setString(2, name);
            pstmt.setInt(3, grade);
            pstmt.setTime(4, reserve);
            pstmt.executeUpdate();
            System.out.println("예약 완료\n");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }

    public static void ShowlaunDry() {
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
                System.out.println(room + "호 | " + grade + "학년 | " + name + " | " + reserve + "\n");
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }

    public static void ShowMorningSong() {
        String song = "";
        String singer = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "select * from laundrytbl";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                song = rs.getString("song");
                singer = rs.getString("singer");
                System.out.println(song + " - " + singer);
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }

    public static void showPoint() {
        String name = "";
        int point = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dorm_db", "root", "Dldlgkwns@12");
            String sql = "select * from penalty";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("name");
                point = rs.getInt("point");
                System.out.println(name + " | " + point + "점");
            }
            System.out.println();


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. 복귀 시간 입력");
            System.out.println("2. 복귀 명단 출력");
            System.out.println("3. 빨래 예약");
            System.out.println("4. 빨래 명단 출력");
            System.out.println("5. 기상송 신청");
            System.out.println("6. 오늘의 기상송");
            System.out.println("7. 벌점 현황");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택 --> ");
            String num = sc.next();

            switch (num) {
                case "1" -> {
                    System.out.println("호실 | 이름 | 복귀 시간(예: HH:MM:SS) | 복귀 여부(true, false)");
                    inputComebackTime(sc.next(), sc.next() , Time.valueOf(sc.next()), sc.hasNextBoolean());
                }
                case "2" -> {
                    ComebackTimeInfo();
                }
                case "3" -> {
                    System.out.println("호실 | 이름 | 학년 | 시간 (예: HH:MM:SS)");
                    reserveLaundry(sc.next(), sc.next(), sc.nextInt(), Time.valueOf(sc.next()));
                }

                case "4" -> {
                    ShowlaunDry();
                }
                case "5" -> {
                    System.out.println("노래 제목 | 가수");
                    inputMorningSong(sc.next(), sc.next());
                }

                case "6" -> {
                    ShowMorningSong();
                }

                case "7" -> {
                    showPoint();
                }

                case "0" -> {
                    return;
                }
            }
        }
    }
}
