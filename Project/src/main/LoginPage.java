package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// 로그인 페이지
public class LoginPage {
    public static void main(String[] args) {
        new LoginPage().createLoginFrame();
    }

    // 로그인 프레임 생성
    public void createLoginFrame() {
        JFrame frame = new JFrame("로그인 화면");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);  // ✅ 프레임 크기 지정
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());  // ✅ 패널 추가 (UI 안정화)
        GridBagConstraints screen = new GridBagConstraints();
        screen.fill = GridBagConstraints.HORIZONTAL;
        screen.insets = new Insets(10, 10, 10, 10);

        addComponent(panel, screen);
        frame.add(panel); // ✅ 패널을 JFrame에 추가

        frame.pack(); // ✅ 모든 컴포넌트 추가 후 호출
        frame.setVisible(true);
    }

    // 컴포넌트 추가 (패널에 추가하도록 변경)
    private void addComponent(JPanel panel, GridBagConstraints screen) {
        Dimension fieldSize = new Dimension(120, 30); // 필드 크기 설정

        JLabel gradeLabel = new JLabel("학년:");
        JComboBox<String> gradeBox = new JComboBox<>(new String[]{"1", "2", "3"});
        gradeBox.setPreferredSize(fieldSize);

        JLabel classLabel = new JLabel("반:");
        JComboBox<String> classBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
        classBox.setPreferredSize(fieldSize);

        JLabel roomLabel = new JLabel("호실:");
        JTextField roomField = new JTextField();
        roomField.setPreferredSize(fieldSize);

        JLabel nameLabel = new JLabel("이름:");
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(fieldSize);

        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(fieldSize);

        // ✅ 로그인 버튼 생성
        JButton loginButton = createLoginButton(panel, gradeBox, classBox, roomField, nameField, passwordField);

        // ✅ 컴포넌트 배치
        screen.gridx = 0; screen.gridy = 0;
        panel.add(gradeLabel, screen);
        screen.gridx = 1;
        panel.add(gradeBox, screen);

        screen.gridx = 0; screen.gridy = 1;
        panel.add(classLabel, screen);
        screen.gridx = 1;
        panel.add(classBox, screen);

        screen.gridx = 0; screen.gridy = 2;
        panel.add(roomLabel, screen);
        screen.gridx = 1;
        panel.add(roomField, screen);

        screen.gridx = 0; screen.gridy = 3;
        panel.add(nameLabel, screen);
        screen.gridx = 1;
        panel.add(nameField, screen);

        screen.gridx = 0; screen.gridy = 4;
        panel.add(passwordLabel, screen);
        screen.gridx = 1;
        panel.add(passwordField, screen);

        // ✅ 로그인 버튼 배치 수정 (아래쪽으로 배치)
        screen.gridx = 0; screen.gridy = 5;
        screen.gridwidth = 2;
        screen.weighty = 1;  // 🔥 아래쪽 공간 확보 (UI 깨짐 방지)
        screen.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, screen);
    }

    // 로그인 버튼 생성
    private JButton createLoginButton(JPanel panel, JComboBox<String> gradeBox, JComboBox<String> classBox, JTextField roomField, JTextField nameField, JPasswordField passwordField) {
        JButton loginButton = new JButton("로그인");
        loginButton.setBackground(Color.PINK);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String grade = gradeBox.getSelectedItem().toString();
                String classNumber = classBox.getSelectedItem().toString();
                String room = roomField.getText();
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());

                String resultMessage = CheckLogin.checkLogin(grade, classNumber, room, name, password);
                if (resultMessage.equals("success")) {
                    JOptionPane.showMessageDialog(panel, "로그인 성공!");
                    SwingUtilities.getWindowAncestor(panel).dispose();  // ✅ 현재 프레임 닫기
                    ChoosePage.main(new String[]{grade, classNumber, room});
                } else {
                    JOptionPane.showMessageDialog(null, resultMessage);
                }
            }
        });
        return loginButton;
    }
}

// 로그인 검증
class CheckLogin {
    private static final String FILE_PATH = "data/students.txt";

    public static String checkLogin(String grade, String classNumber, String room, String name, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] studentInfo = line.split(",");

                if (studentInfo.length == 5) {
                    boolean gradeMatch = studentInfo[0].equals(grade);
                    boolean classMatch = studentInfo[1].equals(classNumber);
                    boolean roomMatch = studentInfo[2].equals(room);
                    boolean nameMatch = studentInfo[3].equals(name);
                    boolean passwordMatch = studentInfo[4].equals(password);

                    if (gradeMatch && classMatch && roomMatch && nameMatch && passwordMatch) {
                        return "success";
                    } else {
                        if (!gradeMatch) return "로그인 실패: 학년이 일치하지 않습니다.";
                        if (!classMatch) return "로그인 실패: 반이 일치하지 않습니다.";
                        if (!roomMatch) return "로그인 실패: 호실이 일치하지 않습니다.";
                        if (!nameMatch) return "로그인 실패: 이름이 일치하지 않습니다.";
                        if (!passwordMatch) return "로그인 실패: 비밀번호가 일치하지 않습니다.";
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "로그인 실패: 학생 정보 파일을 찾을 수 없습니다.";
        }
        return "로그인 실패: 기숙사생이 아닙니다.";
    }
}
