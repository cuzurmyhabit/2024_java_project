package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 외박/잔류 신청
class ApplyPanel extends JPanel {
    public ApplyPanel(JFrame frame) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBackground(Color.WHITE);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messagePanel.setBackground(Color.WHITE);
        JLabel message = new JLabel("어서오세요. 지수민 님!", SwingConstants.LEFT); // 추후 DB 연동
        messagePanel.add(message);

        JButton myPageButton = new JButton("마이 페이지로 이동");
        myPageButton.setPreferredSize(new Dimension(160, 30));
        myPageButton.setBackground(Color.PINK);
        myPageButton.setForeground(Color.BLACK);
        messagePanel.add(myPageButton);
        add(messagePanel);

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
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                StayApplyPage.main(new String[]{}); // 잔류 신청 페이지로 이동
            }
        });
        add(stayButton);
    }
}

// 외박증 제출
class SubmitPanel extends JPanel {
    public SubmitPanel() {
        setLayout(new GridLayout(7, 1, 10, 10));

        // 호실 입력
        JLabel roomLabel = new JLabel("호실");
        JTextField roomField = new JTextField();
        roomField.setToolTipText("자신의 호실을 입력해 주세요.");
        add(roomLabel);
        add(roomField);

        // 이름 입력
        JLabel nameLabel = new JLabel("이름");
        JTextField nameField = new JTextField();
        nameField.setToolTipText("이름을 입력해 주세요.");
        add(nameLabel);
        add(nameField);

        // 퇴실 시간 입력
        JLabel exitTimeLabel = new JLabel("퇴실 시간");
        JTextField exitTimeField = new JTextField();
        exitTimeField.setToolTipText("퇴실 시간을 입력해 주세요.");
        add(exitTimeLabel);
        add(exitTimeField);

        // 입실 시간 입력
        JLabel enterTimeLabel = new JLabel("입실 시간");
        JTextField enterTimeField = new JTextField();
        enterTimeField.setToolTipText("입실 시간을 입력해 주세요.");
        add(enterTimeLabel);
        add(enterTimeField);

        // 부모님 확인
        JCheckBox parentCheckBox = new JCheckBox("부모님이 확인했습니다.");
        add(parentCheckBox);

        // 제출 버튼
        JButton submitButton = new JButton("외박증 제출하기");
        submitButton.setBackground(Color.PINK);
        submitButton.setFont(new Font("나눔고딕", Font.BOLD, 15));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "외박증 제출이 완료되었습니다.");
            }
        });
        add(submitButton);
    }
}

// 메인 프레임
public class ChoosePage {
    public ChoosePage() {
        JFrame frame = new JFrame("ViaSign");
        frame.setSize(350, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(); // 탭 생성

        // 외박/잔류 신청 탭
        ApplyPanel applyPanel = new ApplyPanel(frame);
        tabbedPane.addTab("외박/잔류 신청", applyPanel);

        // 외박증 제출 탭
        SubmitPanel submitPanel = new SubmitPanel();
        tabbedPane.addTab("외박증 제출하기", submitPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ChoosePage();
    }
}
