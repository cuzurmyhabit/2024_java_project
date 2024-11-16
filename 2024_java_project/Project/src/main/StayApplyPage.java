package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StayApplyPage extends JFrame {
    public StayApplyPage() {
        setTitle("잔류 신청하기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLayout(new GridLayout(8, 1, 10, 10));

        // 호실 입력
        add(createLabelAndField("호실", "자신의 호실을 입력해 주세요."));

        // 이름 입력
        add(createLabelAndField("이름", "이름을 입력해 주세요."));

        // 잔류 신청하기 버튼
        JButton submitButton = new JButton("잔류 신청하기");
        submitButton.setBackground(Color.PINK);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        submitButton.addActionListener(new ActionListener() {	//버튼 클릭 시 동작
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "잔류 신청이 완료되었습니다!");
                dispose();	//GUI 닫기
                ChoosePage.main(new String[]{});
            }
        });

        add(submitButton);
        setVisible(true);
    }

    // 라벨, 텍스트 필드 생성 메서드
    private JPanel createLabelAndField(String labelText, String placeholderText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(placeholderText);

        // 텍스트 설정
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholderText);
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