package util;
// 키보드 입력을 할 때
// 도움이 될 메소드를 모아둔 클래스

import java.util.Scanner;

public class ScannerUtil {
    // 메시지를 예쁘게 출력할 메소드
    public static void printMessage(String message) {
        System.out.println(message);
        System.out.print("> ");
    }

    // int를 입력받는 메소드
    public static int nextInt(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        String pattern = "^[-]?\\d+$";

        while (!temp.matches(pattern)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }

        return Integer.parseInt(temp);

    }

    // 특정 범위의 int를 입력받는 메소드
    // 만약 해당 범위를 벗어날 경우, 벗어나지 않을때까지 다시 입력을 받는다.
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int temp = nextInt(scanner, message);
        while (temp < min || temp > max) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextInt(scanner, message);
        }
        return temp;
    }

    // 스캐너 버그를 해결한 nextLine()
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);
        String temp = scanner.nextLine();
        // String 클래스변수의 현재 값이 아무런 내용없는 빈 스트링인지 확인하여
        // 비어잇으면 true, 비어있지 않으면 false가 리턴되는
        // isEmpty() 메소드를 사용하여
        // temp가 비어있는지 체크한다.
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }

        return temp;
    }
}











