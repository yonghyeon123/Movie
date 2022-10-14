package viewer;

import connector.DBConnector;
import connector.MySqlConnector;
import controller.MovieController;
import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

import javax.management.remote.JMXServerErrorException;
import java.util.Scanner;

public class UserViewer {
    private Scanner scanner;
    private DBConnector connector;
    private UserDTO logIn;

    public UserViewer(){
        scanner = new Scanner(System.in);
        connector = new MySqlConnector();
    }

    /**
     * 인덱스 창
     */
    public void showIndex(){
        String message = "\n1. 로그인 2. 회원가입 3. 종료";

        while(true){
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){
                logIn();

                if(logIn != null){
                    showMenu();
                }
            }
            else if(userChoice == 2){
                register();
            }
            else if(userChoice == 3){
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }

    /**
     * 로그인 메소드
     */
    private void logIn(){
        String message;

        message = "아이디를 입력하세요.";
        String username = ScannerUtil.nextLine(scanner, message);

        message = "비밀번호를 입력하세요.";
        String password = ScannerUtil.nextLine(scanner, message);

        UserController userController = new UserController(connector);
        logIn = userController.auth(username, password);

        while(logIn == null){
            System.out.println("잚못된 정보로 인해 로그인이 되지 않았습니다.");
            message = "다시 로그인을 시도하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(scanner, message);

            if(yesNo.equalsIgnoreCase("Y")){
                logIn();
            }
            else{
                System.out.println("로그인을 종료합니다.");
                break;
            }
        }
    }

    /**
     * 회원가입 메소드
     */
    private void register(){
        UserDTO userDTO = new UserDTO();
        UserController userController = new UserController(connector);
        String message;

        message = "아이디를 입력하세요.";
        userDTO.setUsername(ScannerUtil.nextLine(scanner, message));

        message = "비밀번호를 입력하세요.";
        userDTO.setPassword(ScannerUtil.nextLine(scanner, message));

        message = "닉네임을 입력하세요.";
        userDTO.setNickname(ScannerUtil.nextLine(scanner, message));

        //만약 데이터베이스가 비어있다면 첫 계정은 관리자로 지정
        if(userController.dbIsEmpty()){
            userDTO.setGrade(3);
        }
        else{
            userDTO.setGrade(1);
        }

        if(!userController.insert(userDTO)){
            System.out.println("회원가입이 제대로 이루어지지 않았습니다.");
            message = "회원가입을 다시 진행하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(scanner, message);

            if(yesNo.equalsIgnoreCase("Y")){
                register();
            }
        }
    }

    /**
     * 메뉴 출력
     */
    private void showMenu(){
        String message = "\n1. 영화로 이동 2. 회원 정보 보기 3. 로그아웃";


        while(logIn != null){
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){
                MovieViewer movieViewer = new MovieViewer(logIn);
                movieViewer.showMenu();
            }
            else if(userChoice == 2){
                printOne();
            }
            else if(userChoice == 3){
                System.out.println("정상적으로 로그아웃되었습니다.");
                logIn = null;
            }
        }
    }

    /**
     * 현재 로그인중인 회원 정보 출력
     */
    private void printOne(){
        UserController userController = new UserController(connector);
        String message;

        System.out.println("\n----------------------------");
        System.out.println("회원 아이디 : " + logIn.getUsername());
        System.out.println("회원 닉네임 : " + logIn.getNickname());
        System.out.println("회원 등급 : " + userController.parseGradeToString(logIn.getGrade()));
        System.out.println("----------------------------\n");

        message = "1. 수정 2. 삭제 3. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        if(userChoice == 1){
            update();
        }
        else if(userChoice == 2){
            delete();
        }
        else if(userChoice == 3){
            System.out.println("뒤로 돌아갑니다.");
        }
    }

    /**
     * 회원 정보 업데이트
     */
    private void update(){
        String message;

        message = "기존 패스워드를 입력하세요.";
        String password = ScannerUtil.nextLine(scanner, message);

        if(logIn.getPassword().equals(password)){
            UserDTO userDTO = logIn;

            message = "새 비밀번호를 입력하세요.";
            userDTO.setPassword(ScannerUtil.nextLine(scanner, message));

            message = "새 닉네임을 입력하세요.";
            userDTO.setNickname(ScannerUtil.nextLine(scanner, message));

            UserController userController = new UserController(connector);
            userController.update(userDTO);
        }
        else{
            System.out.println("비밀번호를 틀리셨습니다.");
            message = "수정을 계속 진행하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(scanner, message);

            if(yesNo.equalsIgnoreCase("Y")){
                update();
            }
        }
    }

    /**
     * 회원 탈퇴
     */
    private void delete(){
        String message = "정말로 탈퇴하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(scanner, message);

        if(yesNo.equalsIgnoreCase("Y")){
            message = "비밀번호를 입력하세요.";
            String password = ScannerUtil.nextLine(scanner, message);

            while(!logIn.getPassword().equals(password)){
                System.out.println("비밀번호가 일치하지 않습니다.");
                message = "탈퇴를 계속 진행하시겠습니까? Y/N";
                yesNo = ScannerUtil.nextLine(scanner, message);

                if(yesNo.equalsIgnoreCase("N")){
                    System.out.println("회원탈퇴를 종료합니다.");
                    break;
                }
            }

            if(logIn.getPassword().equals(password)){
                UserController userController = new UserController(connector);
                userController.delete(logIn.getId());
                logIn = null;
            }
        }
    }
}
