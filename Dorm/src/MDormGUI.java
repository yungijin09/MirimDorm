import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// 둥근 패널 클래스 (작성하신 것 유지)
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
        setLayout(new BorderLayout());

        mirimLogo = new ImageIcon(MDormGUI.class.getResource("images/mirimLogo.png")).getImage();
        // 1. 상단 바 설정
        JPanel topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(720, 80));

        // 타이틀에 라운드 적용: RoundPanel 안에 JLabel을 넣는 방식
        RoundPanel titleBox = new RoundPanel(20);
        titleBox.setBackground(new Color(68, 127, 90));
        titleBox.setBounds(20, 20, 180, 40);
        titleBox.setLayout(new BorderLayout()); // 글자를 중앙에 맞추기 위해

        JLabel title = new JLabel("Mirim Men's Dorm", SwingConstants.CENTER);
        title.setForeground(Color.WHITE); // 배경이 진하므로 흰색 글자 추천
        title.setFont(new Font("SansSerif", Font.BOLD, 16));

        titleBox.add(title);
        topBar.add(titleBox);
        add(topBar, BorderLayout.NORTH);

        // 2. 중앙 영역 설정
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.setBackground(Color.WHITE);

        // 메뉴 박스에 라운드 적용
        RoundPanel menuBox = new RoundPanel(40) {
            @Override
            protected void paintComponent(Graphics g) {
                // 부모의 paintComponent 호출 (둥근 배경 그리기)
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                // 로고 투명도 설정 (0.1f ~ 0.2f 추천)
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));

                // 메뉴 박스 내부에서의 로고 크기 및 위치 (중앙)
                int size = 200;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                g2d.drawImage(mirimLogo, x, y, size, size, this);
                g2d.dispose();
            }
        };
        menuBox.setBackground(new Color(245, 245, 245)); // 약간 회색빛 흰색
        menuBox.setPreferredSize(new Dimension(500, 270));
        menuBox.setLayout(null); // 내부 자유 배치를 위해 null
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 50, 50));
        btnPanel.setBounds(100, 80, 300, 140);
        btnPanel.setOpaque(false);
        JButton[] menuBtn = new JButton[4];
        String[] menuText = {"복귀 현황", "빨래 예약", "기상송 신청", "상벌점 현황"};
        for (int i = 0; i < menuBtn.length; i++) {
            menuBtn[i] = new JButton(menuText[i]);
            menuBtn[i].setFont(new Font("맑은 고딕", Font.BOLD, 13));
            menuBtn[i].setBackground(Color.WHITE);
            btnPanel.add(menuBtn[i]);
        }
        menuBtn[0].addActionListener(e ->{
            dispose();
            new Comeback();
        });
        menuBtn[1].addActionListener(
                e -> {
                    dispose();
                    new Laundry();
                }
        );
        menuBtn[2].addActionListener(
                e->{
                    dispose();
                    new MorningSong();
                }
        );
        menuBtn[3].addActionListener(
                e -> {
                    dispose();
                    new Point();
                }
        );
        menuBox.add(btnPanel);

        JLabel menuTitle = new JLabel("Menu", SwingConstants.CENTER);
        menuTitle.setForeground(Color.BLACK);
        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 500, 30); // 상단 중앙 배치

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 40, 0);

        menuBox.add(menuTitle);
        wrapper.add(menuBox, gbc);
        add(wrapper, BorderLayout.CENTER);

        setVisible(true);
    }

    class BackgroundPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {        // 배경을 그릴 때 자동으로 불러지는 대표적인 함수
            super.paintComponent(g); // Swing 기본 컴포넌트 렌더링
            g.drawImage(mirimLogo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        new MDormGUI();
    }
}