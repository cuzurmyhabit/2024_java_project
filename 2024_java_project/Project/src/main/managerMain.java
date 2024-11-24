package main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class managerMain {

    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("관리자 페이지");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);

        // JTabbedPane 생성
        JTabbedPane tabbedPane = new JTabbedPane();

        // 첫 번째 탭: 잔류 학생
        JPanel tab1 = createTab("data/stay.txt", "이번 주 잔류 사생", Color.PINK);

        // 두 번째 탭: 외박 학생
        JPanel tab2 = createTab("data/friday_out.txt", "이번 주 외박 사생", Color.PINK);

        // 세 번째 탭
        JPanel tab3 = new JPanel();
        tab3.setBackground(Color.CYAN);
        tab3.add(new JLabel("Tab 3"));

        // 탭에 패널 추가
        tabbedPane.addTab("이번 주 잔류 사생", tab1);
        tabbedPane.addTab("이번 주 외박 사생", tab2);
        tabbedPane.addTab("Tab 3", tab3);

        // JTabbedPane을 JFrame에 추가
        frame.add(tabbedPane);

        // 프레임 표시
        frame.setVisible(true);
    }

    // 공통된 탭 생성 로직을 함수로 리팩토링
    private static JPanel createTab(String filePath, String tabTitle, Color headerColor) {
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());

        // 파일에서 학생 정보 읽기
        List<String> studentDetails = readStudentDetails(filePath);
        int studentCount = studentDetails.size();

        // 상단 헤더 패널 생성
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(headerColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel headerLabel = new JLabel(tabTitle + ": " + studentCount + "명");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel);

        tabPanel.add(headerPanel, BorderLayout.NORTH);

        // 학생 정보 패널 생성
        JPanel studentPanel = new JPanel();
        studentPanel.setBackground(Color.WHITE);
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        for (String detail : studentDetails) {
            JLabel studentLabel = new JLabel(detail);
            studentLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            studentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            studentPanel.add(studentLabel);
        }

        // 스크롤 가능하도록 설정
        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabPanel.add(scrollPane, BorderLayout.CENTER);

        return tabPanel;
    }

    // 파일에서 학생 정보를 읽는 메서드 (외박 학생과 잔류 학생을 구분)
    private static List<String> readStudentDetails(String filePath) {
        List<String> studentDetails = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(","); // 쉼표 기준으로 분리

                    // 학생 정보가 충분히 분리되었는지 확인
                    if (parts.length >= 2) {  // 최소 2개의 요소(호실, 이름)가 있어야 한다
                        String room = parts[0].trim();  // 호실
                        String name = parts[1].trim();  // 이름

                        // "stay.txt" 파일에서는 "잔류" 텍스트가 포함된 학생만 필터링
                        if (filePath.contains("stay")) {
                            studentDetails.add("호실: " + room + " | 이름: " + name);
                        }
                        // "friday_out.txt" 파일에서는 외박인 학생만 필터링
                        else if (filePath.contains("friday") && parts.length >= 4) {
                            String status = parts[3].trim(); // 외박 여부
                            if (status.equals("금토외박")) {
                                studentDetails.add("호실: " + room + " | 이름: " + name);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "파일을 읽는 중 오류가 발생했습니다: " + filePath,
                    "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // 오류 로깅
        }
        return studentDetails;
    }
}
