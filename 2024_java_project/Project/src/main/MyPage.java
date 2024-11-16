package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyPage extends JFrame {

    public MyPage() {
        setTitle("ViaSign");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 프로필 패널
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("ViaSign");
        titleLabel.setForeground(Color.PINK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel profileImage = new JLabel(new ImageIcon(new ImageIcon("profile_placeholder.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        profileImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel = new JLabel("지수민");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("1학년 4반 406호");
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilePanel.add(titleLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(profileImage);
        profilePanel.add(nameLabel);
        profilePanel.add(infoLabel);

        // 하단 기록 패널
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);

        JLabel historyTitle = new JLabel("지수민 님의 외박 히스토리");
        historyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        historyPanel.add(historyTitle);
        historyPanel.add(Box.createVerticalStrut(10));

        // 예시 데이터 추가
        List<String[]> historyData = new ArrayList<>();
        historyData.add(new String[]{"9월 첫째 주", "09월 06일 ~ 09월 08일", "잔류"});
        historyData.add(new String[]{"8월 넷째 주", "08월 31일 ~ 09월 01일", "금토외박"});
        historyData.add(new String[]{"8월 셋째 주", "08월 23일 ~ 08월 25일", "금토외박"});
        historyData.add(new String[]{"8월 둘째 주", "08월 16일 ~ 08월 18일", "잔류"});
        historyData.add(new String[]{"7월 둘째 주", "07월 12일 ~ 07월 14일", "금토외박"});

        for (String[] record : historyData) {
            JPanel recordPanel = new JPanel();
            recordPanel.setLayout(new BorderLayout());
            recordPanel.setBackground(Color.WHITE);

            JLabel weekLabel = new JLabel(record[0]);

            JLabel dateLabel = new JLabel(record[1]);
            dateLabel.setForeground(Color.GRAY);

            JLabel statusLabel = new JLabel(record[2]);
            statusLabel.setForeground(record[2].equals("금토외박") ? Color.PINK : Color.BLACK);

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new GridLayout(2, 1));
            textPanel.add(weekLabel);
            textPanel.add(dateLabel);
            textPanel.setBackground(Color.WHITE);

            recordPanel.add(textPanel, BorderLayout.CENTER);
            recordPanel.add(statusLabel, BorderLayout.EAST);

            historyPanel.add(recordPanel);
            historyPanel.add(Box.createVerticalStrut(5)); // 각 기록 사이의 간격
        }

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBorder(null);

        add(profilePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyPage app = new MyPage();
            app.setVisible(true);
        });
    }
}
