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
        frame.setSize(350, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints screen = new GridBagConstraints();
        screen.fill = GridBagConstraints.HORIZONTAL;
        screen.insets = new Insets(10, 10, 10, 10);

        addComponent(frame, screen);

        frame.setVisible(true);
    }

    // 프레임에 컴포넌트 추가
    private void addComponent(JFrame frame, GridBagConstraints screen) {
        Dimension fieldSize = new Dimension(100, 30); // 필드 크기 설정

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

        // ✅ 수정: createLoginButton()에 passwordField 전달
        JButton loginButton = createLoginButton(frame, gradeBox, classBox, roomField, nameField, passwordField);

        // 그리드 배치
        screen.gridx = 0;
        screen.gridy = 0;
        frame.add(gradeLabel, screen);

        screen.gridx = 1;
        frame.add(gradeBox, screen);

        screen.gridx = 0;
        screen.gridy = 1;
        frame.add(classLabel, screen);

        screen.gridx = 1;
        frame.add(classBox, screen);

        screen.gridx = 0;
        screen.gridy = 2;
        frame.add(roomLabel, screen);

        screen.gridx = 1;
        frame.add(roomField, screen);

        screen.gridx = 0;
        screen.gridy = 3;
        frame.add(nameLabel, screen);

        screen.gridx = 1;
        frame.add(nameField, screen);

        screen.gridx = 0;
        screen.gridy = 4;
        frame.add(passwordLabel, screen);

        screen.gridx = 1;
        frame.add(passwordField, screen);

        // ✅ 수정: 버튼이 한 번만 추가되도록 수정
        screen.gridx = 0;
        screen.gridy = 5;
        screen.gridwidth = 2;
        screen.anchor = GridBagConstraints.CENTER;
        frame.add(loginButton, screen);
    }

    // 로그인 버튼
    private JButton createLoginButton(JFrame frame, JComboBox<String> gradeBox, JComboBox<String> classBox, JTextField roomField, JTextField nameField, JPasswordField passwordField) {
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
                String password = new String(passwordField.getPassword()); // 비밀번호 가져오기

                String resultMessage = CheckLogin.checkLogin(grade, classNumber, room, name, password);
                if (resultMessage.equals("success")) {
                    JOptionPane.showMessageDialog(frame, "로그인 성공!");
                    frame.dispose();
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

                if (studentInfo.length == 5) { // 비밀번호 필드 추가됨
                    boolean gradeMatch = studentInfo[0].equals(grade);
                    boolean classMatch = studentInfo[1].equals(classNumber);
                    boolean roomMatch = studentInfo[2].equals(room);
                    boolean nameMatch = studentInfo[3].equals(name);
                    boolean passwordMatch = studentInfo[4].equals(password); // 비밀번호 검증

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