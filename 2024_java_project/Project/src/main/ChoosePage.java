package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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

        // 외박증 제출 탭
        SubmitLeavePage submitLeavePage = new SubmitLeavePage(frame, userName, roomNumber);
        tabbedPane.addTab("외박증 제출", submitLeavePage);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 학년, 반, 번호 설정
        String grade = "1";
        String classroom = "4";
        String room = "406";

        // 파일에서 사용자 이름 가져오기
        String userName = getUserName("data/students.txt", grade, classroom, room);

        String roomNumber = classroom + room.substring(1);

        // 메인 페이지 실행
        new ChoosePage(userName, roomNumber);
    }

    // 파일에서 학년, 반, 번호에 해당하는 이름 찾기
    public static String getUserName(String filePath, String grade, String classroom, String studentNumber) {
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

    // 외박/잔류 신청 패널
    static class ApplyPanel extends JPanel {
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
            try (BufferedWriter writer1 = new BufferedWriter(new FileWriter("data/stay.txt", true));
                 BufferedWriter writer2 = new BufferedWriter(new FileWriter("data/myapply.txt", true))) {

                String data = roomNumber + "," + userName + ",잔류";
                
                // 첫 번째 파일 (stay.txt) 저장
                writer1.write(data);
                writer1.newLine();

                // 두 번째 파일 (myapply.txt) 저장
                writer2.write(data);
                writer2.newLine();

                JOptionPane.showMessageDialog(null, "잔류 신청이 완료되었습니다!");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.");
                ex.printStackTrace();
            }
        }
    }

    // 외박증 제출 페이지
    static class SubmitLeavePage extends JPanel {
        public SubmitLeavePage(JFrame frame, String userName, String roomNumber) {
            setLayout(new BorderLayout(10, 10));
            setBackground(Color.WHITE);

            // 제목
            JLabel titleLabel = new JLabel("외박증 제출", SwingConstants.CENTER);
            titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
            add(titleLabel, BorderLayout.NORTH);

            // 외박 정보 입력
            JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            inputPanel.setBackground(Color.WHITE);

            JLabel leaveReasonLabel = new JLabel("퇴실 날짜:");
            JTextField leaveReasonField = new JTextField();
            JLabel leaveDateLabel = new JLabel("퇴실 시간:");
            JTextField leaveDateField = new JTextField();
            JLabel leaveOutTimeLabel = new JLabel("입실 날짜:");
            JTextField leaveOutTimeField = new JTextField();
            JLabel leaveInTimeLabel = new JLabel("입실 시간:");
            JTextField leaveInTimeField = new JTextField();

            // 부모님 확인 체크박스
            JCheckBox parentCheckBox = new JCheckBox("부모님 확인 완료");
            
            inputPanel.add(leaveReasonLabel);
            inputPanel.add(leaveReasonField);
            inputPanel.add(leaveDateLabel);
            inputPanel.add(leaveDateField);
            inputPanel.add(leaveOutTimeLabel);
            inputPanel.add(leaveOutTimeField);
            inputPanel.add(leaveInTimeLabel);
            inputPanel.add(leaveInTimeField);
            inputPanel.add(new JLabel());  // 빈 공간
            inputPanel.add(parentCheckBox);

            add(inputPanel, BorderLayout.CENTER);

            // 제출 버튼
            JButton submitButton = new JButton("외박증 제출");
            submitButton.setBackground(Color.PINK);
            submitButton.setFont(new Font("나눔고딕", Font.BOLD, 15));
            submitButton.setPreferredSize(new Dimension(200, 50));

            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String reason = leaveReasonField.getText();
                    String date = leaveDateField.getText();
                    String outTime = leaveOutTimeField.getText();
                    String inTime = leaveInTimeField.getText();
                    boolean isParentChecked = parentCheckBox.isSelected();

                    if (reason.isEmpty() || date.isEmpty() || outTime.isEmpty() || inTime.isEmpty() || !isParentChecked) {
                        JOptionPane.showMessageDialog(frame, "모든 필드를 입력하고 부모님 확인을 완료해주세요.");
                    } else {
                        saveLeaveData(roomNumber, userName, reason, date, outTime, inTime, isParentChecked); // 외박 정보 저장
                    }
                }
            });

            add(submitButton, BorderLayout.SOUTH);
        }

        // 외박 정보를 파일에 저장하는 메서드
        private void saveLeaveData(String roomNumber, String userName, String reason, String date, String outTime, String inTime, boolean isParentChecked) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/Checkin.txt", true))) {
                String parentCheck = isParentChecked ? "부모님 확인 완료" : "부모님 확인 미완료";
                String data = roomNumber + "," + userName + "," + reason + "," + date + "," + outTime + "," + inTime + "," + parentCheck;
                writer.write(data);
                writer.newLine();

                JOptionPane.showMessageDialog(null, "외박증이 제출되었습니다!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.");
                ex.printStackTrace();
            }
        }
    }
}
