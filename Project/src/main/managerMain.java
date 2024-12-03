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
        
        JTabbedPane tabbedPane = new JTabbedPane();

        // 잔류 사생
        JPanel tab1 = createTab("data/stay.txt", "이번 주 잔류 사생", Color.PINK);

        // 외박 사생
        JPanel tab2 = createTab("data/friday_out.txt", "data/saturday_out.txt", "이번 주 외박 사생", Color.PINK);

        // 외박증 제출
        JPanel tab3 = createTab("data/Checkin.txt", "제출된 외박증", Color.PINK);

        // 탭에 패널 추가
        tabbedPane.addTab("이번 주 잔류 사생", tab1);
        tabbedPane.addTab("이번 주 외박 사생", tab2);
        tabbedPane.addTab("제출된 외박증", tab3);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // 외박 사생 처리 메소드
    private static JPanel createTab(String fridayFilePath, String saturdayFilePath, String tabTitle, Color headerColor) {
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());

        // 두 파일에서 학생 정보 읽기
        List<String> studentDetails = readStudentDetails(fridayFilePath, saturdayFilePath);
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

    // 외박 사생 처리 메소드
    private static List<String> readStudentDetails(String fridayFilePath, String saturdayFilePath) {
        List<String> studentDetails = new ArrayList<>();

        studentDetails.addAll(readStudentDetails(fridayFilePath));
        studentDetails.addAll(readStudentDetails(saturdayFilePath));

        return studentDetails;
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

    // ㅇ외박증 제출 탭 파일 처리 메소드
    private static List<String> readStudentDetails(String filePath) {
        List<String> studentDetails = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(","); // 쉼표 기준으로 분리

                    // Checkin.txt 파일 처리
                    if (filePath.contains("Checkin") && parts.length >= 7) {
                        String room = parts[0].trim();   // 호실
                        String name = parts[1].trim();   // 이름
                        String parentCheck = parts[6].trim(); // 부모님 확인 완료

                        studentDetails.add("호실: " + room + " | 이름: " + name + " | " + parentCheck);
                    } 
                    else if (parts.length >= 2) {
                        String room = parts[0].trim();  // 호실
                        String name = parts[1].trim();  // 이름

                        if (filePath.contains("stay")) {
                            studentDetails.add("호실: " + room + " | 이름: " + name);
                        } else if ((filePath.contains("friday") || filePath.contains("saturday")) && parts.length >= 4) {
                            String status = parts[3].trim(); // 외박 여부
                            if (status.equals("금토외박") || status.equals("토요외박")) {
                                String stayType = status.equals("금토외박") ? "금토외박" : "토요외박";
                                studentDetails.add("호실: " + room + " | 이름: " + name + " | 외박: " + stayType);
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
