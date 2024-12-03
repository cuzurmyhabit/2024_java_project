package main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyPage extends JFrame {

    public MyPage(String userInfo) {
        setTitle("ViaSign");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 프로필 패널
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);

        // 전달받은 사용자 정보 파싱
        String[] userInfoArray = userInfo.split(",");
        String name = userInfoArray[3]; // 이름
        String gradeClassRoom = String.format("%s학년 %s반 %s호", userInfoArray[0], userInfoArray[1], userInfoArray[2]);

        JLabel profileImage = new JLabel(new ImageIcon(new ImageIcon("data/profile.png")
                .getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        profileImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel(gradeClassRoom);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(profileImage);
        profilePanel.add(nameLabel);
        profilePanel.add(infoLabel);

        // 하단 기록 패널
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);

        JLabel historyTitle = new JLabel(name + " 님의 외박 히스토리");
        historyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        historyPanel.add(historyTitle);
        historyPanel.add(Box.createVerticalStrut(5)); // 제목과 첫 항목 사이 간격 축소

        // 히스토리 데이터 로드
        List<String[]> historyData = loadHistoryData("data/myapply.txt");

        for (String[] record : historyData) {
            JPanel recordPanel = new JPanel();
            recordPanel.setLayout(new BorderLayout());
            recordPanel.setBackground(Color.WHITE);

            JLabel dateLabel = new JLabel(record[0]); // 날짜
            dateLabel.setForeground(Color.GRAY);

            JLabel destinationLabel = new JLabel(record[1]); // 목적지

            JLabel statusLabel = new JLabel(record[2]); // 외박
            statusLabel.setForeground(record[2].equals("금토외박") ? Color.PINK : Color.BLACK);

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new GridLayout(2, 1));
            textPanel.add(dateLabel);
            textPanel.add(destinationLabel);
            textPanel.setBackground(Color.WHITE);

            recordPanel.add(textPanel, BorderLayout.CENTER);
            recordPanel.add(statusLabel, BorderLayout.EAST);

            historyPanel.add(recordPanel);
            historyPanel.add(Box.createVerticalStrut(2)); // 각 기록 사이의 간격을 줄임
        }

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBorder(null);

        add(profilePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private List<String[]> loadHistoryData(String filePath) {
        List<String[]> historyData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // 파일은 "호실,이름,번호,외박,날짜,목적지" 형식으로 가정
                if (parts.length == 6) {
                    // 앞의 3개(호실, 이름, 번호)를 제외하고 날짜, 목적지, 외박 순으로 추가
                    historyData.add(new String[]{parts[4], parts[5], parts[3]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyData;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 테스트를 위해 임의의 사용자 정보 전달
            String mockUserInfo = "1,4,406,지수민"; // 학년, 반, 호실, 이름
            MyPage app = new MyPage(mockUserInfo);
            app.setVisible(true);
        });
    }
}
