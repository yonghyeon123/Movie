package viewer;

import connector.DBConnector;
import connector.MySqlConnector;
import model.UserDTO;
import util.ScannerUtil;

import java.util.Scanner;

public class TheaterViewer {
    private Scanner scanner;
    private DBConnector dbConnector;
    private UserDTO logIn;

    public TheaterViewer(UserDTO logIn){
        scanner = new Scanner(System.in);
        dbConnector = new MySqlConnector();
        this.logIn = logIn;
    }

    public void showMenu(){
        String message = "1. 영화관 찾기 2. 영화관 추가 3. 뒤로가기";

        while (true){
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){

            }
            else if(userChoice == 2){

            }
            else if(userChoice == 3){

            }
        }
    }

    public void insert(){
        String message;

        message = "영화관의 이름을 입력하세요.";
        String theaterName = ScannerUtil.nextLine(scanner, message);

        message = "영화관의 위치를 입력하세요.";
        String location = ScannerUtil.nextLine(scanner, message);

        message = "영화관의 번호를 입력하세요";
        String number = ScannerUtil.nextLine(scanner, message);
    }

    public void printList(){
        String message;


    }
}
