package viewer;

import connector.DBConnector;
import connector.MySqlConnector;
import controller.RateController;
import controller.UserController;
import model.RateDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class RateViewer {
    private Scanner scanner;
    private DBConnector connector;
    private UserDTO logIn;

    public RateViewer(UserDTO logIn){
        scanner = new Scanner(System.in);
        connector = new MySqlConnector();
        this.logIn = logIn;
    }

    public void showMenu(int movieId){
        String message = "\n1. 영화 평점 보기 2. 평점 작성하기 3. 뒤로가기";

        while(true){
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1){
                printRate(movieId);
            }
            else if(userChoice == 2){
                write(movieId);
            }
            else if(userChoice == 3){
                System.out.println("뒤로 돌아갑니다.");
                break;
            }
        }
    }

    /**
     * 특정 영화의 평점 리스트 출력
     * @param movieId 출력할 평점의 영화 번호
     */
    private void printRate(int movieId){
        UserController userController = new UserController(connector);
        ArrayList<RateDTO> list = new ArrayList<>();

        if(list.isEmpty()){
            System.out.println("아직 등록된 평점이 존재하지 않습니다.");
        }
        else{

            System.out.println("\n----------------------------");
            for(RateDTO r : list){
                System.out.printf("%d점 - %s", r.getRating(), r.getWriterId());
            }
            System.out.println("----------------------------\n");
        }
    }

    /**
     * 평점 작성
     * @param movieId 평점이 작성될 영화의 번호
     */
    private void write(int movieId){
        RateDTO rateDTO = new RateDTO();
        String message;

        rateDTO.setWriterId(logIn.getId());
        rateDTO.setMovieId(movieId);

        message = "평점을 입력해주세요.(1점부터 5점)";
        rateDTO.setRating(ScannerUtil.nextInt(scanner, message, 1, 5));

        if(logIn.getGrade() == 2){
            message = "평론을 입력해주세요.";
            rateDTO.setReview(ScannerUtil.nextLine(scanner, message));
        }

        RateController rateController = new RateController(connector);
        rateController.insert(rateDTO);
    }
}
