package io.github.cuzurmyhabit;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystem {

    public void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.err.println("Firebase 초기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void login() {
        initializeFirebase();

        Scanner scanner = new Scanner(System.in);

        System.out.print("학생은 1, 학부모는 2를 입력: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("호실 입력: ");
        int room = scanner.nextInt();
        scanner.nextLine();

        System.out.print("학년 입력: ");
        int grade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("반 입력: ");
        int classNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("이름 입력: ");
        String name = scanner.nextLine();

        Firestore db = FirestoreClient.getFirestore();
        String collectionName = (choice == 1) ? "student_info" : "parent_info";

        ApiFuture<QuerySnapshot> query = db.collection(collectionName)
                .whereEqualTo("room", room)
                .whereEqualTo("grade", grade)
                .whereEqualTo("class", classNumber)
                .whereEqualTo("name", name)
                .get();

        try {
            if (!query.get().isEmpty()) {
                System.out.println("인증 성공!");
            } else {
                System.out.println("인증 실패! 정보를 다시 확인하세요.");
            }
        } catch (Exception e) {
            System.err.println("인증 과정 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.login();
    }
}