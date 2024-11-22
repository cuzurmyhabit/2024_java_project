package main;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class OutApplyPage extends JFrame {

    private JTextField roomField, nameField, contactField, dateField, destinationField;

    public OutApplyPage(String presetName, String presetRoom) {
        // JFrame 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLayout(new GridLayout(8, 1, 10, 10));

        // 전역 변수로 텍스트 필드 선언
        roomField = new JTextField(presetRoom); // 미리 제공된 호실 값
        nameField = new JTextField(presetName); // 미리 제공된 이름 값
        contactField = new JTextField();
        dateField = new JTextField();
        destinationField = new JTextField();

        // 미리 제공된 값은 수정할 수 없도록 비활성화
        roomField.setEditable(false);
        nameField.setEditable(false);

        add(createLabelAndField("호실", roomField));
        add(createLabelAndField("이름", nameField));
        add(createLabelAndField("연락처 (ex.01012345678)", contactField));
        add(createLabelAndField("날짜 (yyyy-mm-dd)", dateField));
        add(createLabelAndField("목적지", destinationField));

        // 외박 선택 버튼
        JPanel choice = new JPanel();
        choice.setLayout(new FlowLayout());
        JRadioButton fridaySaturdayButton = new JRadioButton("금토외박");
        JRadioButton saturdayButton = new JRadioButton("토요외박");
        ButtonGroup group = new ButtonGroup();
        group.add(fridaySaturdayButton);
        group.add(saturdayButton);
        choice.add(fridaySaturdayButton);
        choice.add(saturdayButton);
        add(createLabelAndPanel("외박 선택", choice));

        // 제출 버튼
        JButton submitButton = new JButton("외박 신청하기");
        submitButton.setBackground(Color.PINK);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        submitButton.addActionListener(e -> handleSubmit(fridaySaturdayButton, saturdayButton));
        add(submitButton);

        setVisible(true);
    }

    private JPanel createLabelAndField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLabelAndPanel(String labelText, JPanel innerPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(innerPanel, BorderLayout.CENTER);
        return panel;
    }

    private void handleSubmit(JRadioButton fridaySaturdayButton, JRadioButton saturdayButton) {
        try {
            // 입력 필드 값 가져오기
            String roomNumber = roomField.getText().trim();
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String date = dateField.getText().trim();
            String destination = destinationField.getText().trim();

            String stayType = "";
            String fileName = "";

            if (fridaySaturdayButton.isSelected()) {
                stayType = "금토외박";
                fileName = "data/friday_out.txt";
            } else if (saturdayButton.isSelected()) {
                stayType = "토요외박";
                fileName = "data/saturday_out.txt";
            }

            if (contact.isEmpty() || date.isEmpty() || destination.isEmpty() || stayType.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 채워주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String data = String.format("%s,%s,%s,%s,%s,%s", roomNumber, name, contact, stayType, date, destination);

            Path typeFilePath = Paths.get(fileName);
            Files.createDirectories(typeFilePath.getParent());
            Files.write(typeFilePath, (data + "\n").getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);

            Path allDataPath = Paths.get("data/myapply.txt");
            Files.write(allDataPath, (data + "\n").getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);

            JOptionPane.showMessageDialog(this, String.format("외박 신청이 완료되었습니다!\n저장된 파일:\n%s\n%s", fileName, "data/myapply.txt"));
            dispose();

            ChoosePage.main(data.split(","));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "오류가 발생했습니다: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
    	
        String userName = "지수민";
        String room = "406";
        new OutApplyPage(userName, room);
    }
}
