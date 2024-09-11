import java.util.Scanner;

public class ApplySystem {
    public static void main(String args []){
            Scanner scanner = new Scanner(System.in);

            System.out.print("호실 입력:");
            String room_apply = scanner.nextLine();

            System.out.print("이름:");
            String name_apply = scanner.nextLine();

            System.out.print("부모님 연락처:");
            String cellnum_apply = scanner.nextLine();

            System.out.print("외박 선택 (금토외박 or 토요외박):");
            String getsleepover = scanner.nextLine();
            String sleepover;
            if (getsleepover.equals("금토외박")) {
                sleepover = "금토외박";
            } else {
                sleepover = "토요외박";
            }

            System.out.print("목적지:");
            String place_apply = scanner.nextLine();

            if (sleepover.equals("금토외박")) {
                System.out.println("금토외박 신청이 완료되었습니다!");
            } else {
                System.out.println("토요외박 신청이 완료되었습니다!");
            }

        }
    }