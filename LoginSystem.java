import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginSystem {
    private List<Student> students = new ArrayList<>();
    private List<Parent> parents = new ArrayList<>();

    public LoginSystem() {
        // 예제용 데이터 추가
        students.add(new Student("406", "1", "5", "문지우"));
        students.add(new Student("406", "1", "5", "박솔하"));
        students.add(new Student("406", "1", "5", "김다은"));
        students.add(new Student("406", "1", "4", "지수민"));
    }git commit --amend

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("학생은 1 학부모는 2를 입력:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("호실 입력:");
        String room = scanner.nextLine();

        System.out.print("학년:");
        String grade = scanner.nextLine();

        System.out.print("반:");
        String classRoom = scanner.nextLine();

        System.out.print("이름:");
        String name = scanner.nextLine();

        boolean authenticated = false;

        if (choice == 1) {
            for (Student student : students) {
                if (student.authenticate(room, grade, classRoom, name)) {
                    authenticated = true;
                }
            }
        } else if (choice == 2) {
            for (Parent parent : parents) {
                if (parent.authenticate(room, grade, classRoom, name)) {
                    authenticated = true;
                }
            }
        }

        if (authenticated) {
            System.out.println("정상적으로 로그인 되었습니다.");
        } else {
            System.out.println("존재하지 않는 기숙사생입니다.");
        }
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.login();
    }

}