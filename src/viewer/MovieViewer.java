package viewer;

import connector.DBConnector;
import connector.MySqlConnector;
import controller.MovieController;
import controller.RateController;
import model.MovieDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class MovieViewer {
    private Scanner scanner;
    private DBConnector connector;
    private UserDTO logIn;

    public MovieViewer(UserDTO logIn){
        scanner = new Scanner(System.in);
        connector = new MySqlConnector();
        this.logIn = logIn;
    }

    /**
     * 영화 메뉴 출력
     */
    public void showMenu(){
        String message = "\n1. 영화 목록 보기 2. 새 영화 추가 3. 뒤로가기";

        while(true){
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){
                printList();
            }
            else if(userChoice == 2){
                //해당 계정이 관리자 계정이라면
                if(logIn.getGrade() == 3){
                    insert();
                }
                else{
                    System.out.println("해당 계정은 관리자가 아닙니다.");
                }
            }
            else if(userChoice == 3){
                System.out.println("뒤로 돌아갑니다.");
                break;
            }
        }
    }

    private void insert(){
        MovieDTO movieDTO = new MovieDTO();
        String message;

        message = "영화의 제목을 입력하세요.";
        movieDTO.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "영화의 줄거리를 입력하세요.";
        movieDTO.setStoryLine(ScannerUtil.nextLine(scanner, message));

        System.out.println("영화의 시청등급을 정하세요.");
        message = "1. 전체이용가 2. 12세 이용가 3. 15세 이용가 4. 청소년 이용불가";
        movieDTO.setDegree(ScannerUtil.nextInt(scanner, message, 1, 4));

        MovieController movieController = new MovieController(connector);
        movieController.insert(movieDTO);
    }

    /**
     * 영화 리스트 출력
     */
    private void printList(){
        MovieController movieController = new MovieController(connector);
        ArrayList<MovieDTO> list = movieController.selectAll();

        System.out.println("\n----------------------------");
        for(MovieDTO m : list){
            System.out.printf("%d, %s - %s\n", m.getId(), m.getTitle(), movieController.parseDegreeToString(m.getDegree()));
        }
        System.out.println("----------------------------\n");

        String message = "상세보기할 영화의 번호나 0을 입력하여 뒤로 돌아가세요.";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        if(userChoice != 0 && movieController.selectOne(userChoice) == null){
            System.out.println("해당 번호의 영화는 존재하지 않습니다.");
            message = "상세보기할 영화의 번호나 0을 입력하여 뒤로 돌아가세요.";
            userChoice = ScannerUtil.nextInt(scanner, message);
        }

        if(userChoice != 0){
            printOne(userChoice);
        }
    }

    /**
     * 원하는 영화 정보 출력
     * @param id 출력하고 싶은 영화의 번호
     */
    private void printOne(int id){
        MovieController movieController = new MovieController(connector);
        RateController rateController = new RateController(connector);
        MovieDTO movieDTO = movieController.selectOne(id);

        System.out.println("\n----------------------------");
        System.out.println("영화 이름 : " + movieDTO.getTitle());
        System.out.printf("영화 평점 : %.1f\n", rateController.averageRating(id));
        System.out.println("영화 관람등급 : " + movieController.parseDegreeToString(movieDTO.getDegree()));
        System.out.println("영화 줄거리");
        System.out.println(movieDTO.getStoryLine());
        System.out.println("----------------------------");

        //만약 로그인된 계정이 관리자라면
        if(logIn.getGrade() == 3){
            String message = "1. 영화 수정 2. 영화 삭제 3. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){
                update(id);
            }
            else if(userChoice == 2){
                delete(id);
            }
            else if(userChoice == 3){
                System.out.println("뒤로 돌아갑니다.");
                printList();
            }
        }
        else{
            String message = "\n1. 상영중인 영화관으로 이동 2. 평점 이동 3. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){

            }
            else if(userChoice == 2){
                RateViewer rateViewer = new RateViewer(logIn);
                rateViewer.showMenu(id);
            }
            else if(userChoice == 3){
                printList();
            }
        }
    }

    /**
     * 영화 정보 업테이트
     * @param id 업데이트할 영화의 번호
     */
    private void update(int id){
        MovieController movieController = new MovieController(connector);
        MovieDTO movieDTO = movieController.selectOne(id);
        String message;

        message = "새 영화 제목을 입력하세요.";
        movieDTO.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "새 영화 줄거리를 입력하세요.";
        movieDTO.setStoryLine(ScannerUtil.nextLine(scanner, message));

        System.out.println("영화의 등급을 정하세요.");
        message = "1. 전체이용가 2. 12세 이용가 3. 15세 이용가 4. 청소년 이용불가";
        movieDTO.setDegree(ScannerUtil.nextInt(scanner, message, 1, 4));

        movieController.update(movieDTO);
    }

    /**
     * 영화 정보 삭제
     * @param id 삭제할 영화의 번호
     */
    private void delete(int id){
        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(scanner, message);

        if(yesNo.equalsIgnoreCase("Y")){
            MovieController movieController = new MovieController(connector);
            System.out.println("성공적으로 삭제했습니다.");
            movieController.delete(id);
        }
    }
}
