package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OutApplyPage extends JFrame {
    public OutApplyPage() {
    	
        JFrame frame = new JFrame("외박 신청하기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        // 호실 입력
        add(createLabelAndField("호실", "자신의 호실을 입력해 주세요."));

        // 이름 입력
        add(createLabelAndField("이름", "이름을 입력해 주세요."));

        // 연락처 입력
        add(createLabelAndField("연락처 (ex.01012345678)", "부모님의 연락처를 입력해 주세요."));

        // 외박 선택 버튼
        JPanel choice = new JPanel();
        choice.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JRadioButton fridaySaturdayButton = new JRadioButton("금토외박");
        JRadioButton saturdayButton = new JRadioButton("토요외박");
        
        ButtonGroup group = new ButtonGroup();
        group.add(fridaySaturdayButton);
        group.add(saturdayButton);
        
        choice.add(fridaySaturdayButton);
        choice.add(saturdayButton);
        add(createLabelAndPanel("외박 선택", choice));

        // 날짜 입력
        add(createLabelAndField("날짜 (yyyy-mm-dd)", "외박 날짜를 입력해 주세요."));

        // 목적지 입력
        add(createLabelAndField("목적지 (yyyy-mm-dd)", "목적지를 입력해 주세요."));

        // 외박 신청하기 버튼
        JButton submitButton = new JButton("외박 신청하기");
        submitButton.setBackground(Color.PINK);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "외박 신청이 완료되었습니다!");
                dispose();
                ChoosePage.main(new String[]{});
            }
        });

        add(submitButton);

        setVisible(true);
    }

    // 라벨과 텍스트 필드 생성 메서드
    private JPanel createLabelAndField(String labelText, String placeholderText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(placeholderText);

        // 플래이스홀더 텍스트 설정
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

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

    // 라벨, 외박 선택 버튼 생성 메서드
    private JPanel createLabelAndPanel(String labelText, JPanel innerPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(innerPanel, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        new OutApplyPage();
    }
}
