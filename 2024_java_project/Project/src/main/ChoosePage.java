package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class ApplyPanel extends JPanel {
    public ApplyPanel(JFrame frame, String userName, String roomNumber) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBackground(Color.WHITE);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messagePanel.setBackground(Color.WHITE);
        JLabel message = new JLabel("어서오세요. " + userName + " 님! (" + roomNumber + "호)", SwingConstants.LEFT);
        message.setFont(new Font("나눔고딕", Font.BOLD, 15));
        messagePanel.add(message);

        // 마이페이지 버튼
        JButton myPageButton = new JButton("마이 페이지로 이동");
        myPageButton.setPreferredSize(new Dimension(160, 30));
        myPageButton.setBackground(Color.PINK);
        myPageButton.setForeground(Color.BLACK);
        messagePanel.add(myPageButton);
        add(messagePanel);
        myPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MyPage.main(new String[]{}); // 마이페이지로 이동
            }
        });

        // 외박 신청 버튼
        JButton outButton = new JButton("외박 신청하러 가기");
        outButton.setBackground(Color.PINK);
        outButton.setFont(new Font("나눔고딕", Font.BOLD, 15));
        outButton.setPreferredSize(new Dimension(200, 60));
        outButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                OutApplyPage.main(new String[]{}); // 외박 신청 페이지로 이동
            }
        });
        add(outButton);

        // 잔류 신청 버튼
        JButton stayButton = new JButton("잔류 신청하러 가기");
        stayButton.setBackground(Color.PINK);
        stayButton.setFont(new Font("나눔고딕", Font.BOLD, 15));
        stayButton.setPreferredSize(new Dimension(200, 60));
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStayData(roomNumber, userName); // 잔류 데이터 저장
            }
        });
        add(stayButton);
    }

    // 잔류 데이터를 파일에 저장하는 메서드
    private void saveStayData(String roomNumber, String userName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/stay.txt", true))) {
            writer.write(roomNumber + "," + userName + ",잔류");
            writer.newLine();
            JOptionPane.showMessageDialog(null, "잔류 신청이 완료되었습니다!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.");
            ex.printStackTrace();
        }
    }
}

// 메인 프레임
public class ChoosePage {
    public ChoosePage(String userName, String roomNumber) {
        JFrame frame = new JFrame("ViaSign");
        frame.setSize(350, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(); // 탭 생성

        // 외박/잔류 신청 탭
        ApplyPanel applyPanel = new ApplyPanel(frame, userName, roomNumber);
        tabbedPane.addTab("외박/잔류 신청", applyPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 학년, 반, 번호 설정
        String grade = "1";
        String classroom = "4";
        String studentNumber = "406";

        // 파일에서 사용자 이름 가져오기
        String userName = getUserNameFromFile("data/students.txt", grade, classroom, studentNumber);

        // 호실 계산 (예: 406 -> 4층 06호)
        String roomNumber = classroom + studentNumber.substring(1);

        // 메인 페이지 실행
        new ChoosePage(userName, roomNumber);
    }

    // 파일에서 학년, 반, 번호에 해당하는 이름 찾기
    public static String getUserNameFromFile(String filePath, String grade, String classroom, String studentNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // ","로 데이터 분리
                if (parts.length == 4
                        && parts[0].trim().equals(grade)
                        && parts[1].trim().equals(classroom)
                        && parts[2].trim().equals(studentNumber)) {
                    return parts[3].trim(); // 이름 반환
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "알 수 없는 사용자"; // 조건에 맞는 사용자를 찾지 못했을 때
    }
}
