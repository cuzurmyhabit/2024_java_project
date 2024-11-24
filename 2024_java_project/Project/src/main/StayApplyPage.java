package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class StayApplyPage extends JFrame {
    private JTextField roomField; // 호실 입력 필드
    private JTextField nameField; // 이름 입력 필드

    public StayApplyPage() {
        setTitle("잔류 신청하기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLayout(new GridLayout(8, 1, 10, 10));

        // 호실 입력
        roomField = new JTextField("자신의 호실을 입력해 주세요.");
        add(createLabelAndField("호실", roomField));

        // 이름 입력
        nameField = new JTextField("이름을 입력해 주세요.");
        add(createLabelAndField("이름", nameField));

        // 잔류 신청하기 버튼
        JButton submitButton = new JButton("잔류 신청하기");
        submitButton.setBackground(Color.PINK);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 필드에서 값 가져오기
                String room = roomField.getText().trim();
                String name = nameField.getText().trim();

                // 입력 값 확인
                if (room.isEmpty() || room.equals("자신의 호실을 입력해 주세요.") ||
                    name.isEmpty() || name.equals("이름을 입력해 주세요.")) {
                    JOptionPane.showMessageDialog(null, "모든 필드를 올바르게 입력해 주세요.");
                    return;
                }

                // 파일에 데이터 저장
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/myapply.txt", true))) {
                    writer.write(room + "," + name + ",잔류");
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "잔류 신청이 완료되었습니다!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }

                dispose(); // 현재 창 닫기
                ChoosePage.main(new String[]{}); // ChoosePage로 이동
            }
        });

        add(submitButton);
        setVisible(true);
    }

    // 라벨과 텍스트 필드를 생성하는 메서드
    private JPanel createLabelAndField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(labelText);

        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(labelText + "을 입력해 주세요.")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(labelText + "을 입력해 주세요.");
                }
            }
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        new StayApplyPage();
    }
}
