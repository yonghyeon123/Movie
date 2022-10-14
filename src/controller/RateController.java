package controller;

import connector.DBConnector;
import model.RateDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RateController {
    private Connection connection;

    public RateController(DBConnector connector){
        connection = connector.makeConnection();
    }

    /**
     * 평점 추가
     * @param rateDTO 추가할 평점 정보
     */
    public void insert(RateDTO rateDTO){
        String query = "INSERT INTO `rate` (`writerId`, `movieId`, `rating`, `review`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rateDTO.getWriterId());
            pstmt.setInt(2, rateDTO.getMovieId());
            pstmt.setInt(3, rateDTO.getRating());
            pstmt.setString(4, rateDTO.getReview());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 평점 정부 수정
     * @param rateDTO 수정할 평점 정보
     */
    public void update(RateDTO rateDTO){
        String query = "UPDATE `rate` SET `rating` = ?, `review` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rateDTO.getRating());
            pstmt.setString(2, rateDTO.getReview());
            pstmt.setInt(3, rateDTO.getId());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 평점 정보 삭제
     * @param id 수정할 평점의 번호
     */
    public void delete(int id){
        String query = "DELETE FROM `rate` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 특정 영화의 평점들을 출력할 메소드
     * @param movieId 출력할 평점의 영화 번호
     * @return 해당 영화의 평점들
     */
    public ArrayList<RateDTO> selectAll(int movieId){
        ArrayList<RateDTO> list = new ArrayList<>();
        String query = "SELECT * FROM `rate` WHERE `movieId` = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                RateDTO r = new RateDTO();
                r.setId(rs.getInt("id"));
                r.setWriterId(rs.getInt("writerId"));
                r.setMovieId(rs.getInt("movieId"));
                r.setRating(rs.getInt("rating"));
                r.setReview(rs.getString("review"));

                list.add(r);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 특정 회원 등급의 평점을 출력하는 메소드
     * @param movieId 평점을 출력할 영화의 번호
     * @param writerGrade 출력할 평점의 회원 등급
     * @return 특정 영화의 특정 회원 등급의 평점들
     */
    public ArrayList<RateDTO> selectAll(int movieId, int writerGrade){
        ArrayList<RateDTO> list = new ArrayList<>();
        String query = "SELECT * FROM `rate` WHERE `movieId` = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                RateDTO r = new RateDTO();
                r.setId(rs.getInt("id"));
                r.setWriterId(rs.getInt("writerId"));
                r.setMovieId(rs.getInt("movieId"));
                r.setRating(rs.getInt("rating"));
                r.setReview(rs.getString("review"));

                list.add(r);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 특정 영화의 전체 평점을 계산해주는 메소드
     * @param movieId 전체 평점을 구할 영화의 번호
     * @return 해당 영화의 전체 평점
     */
    public double averageRating(int movieId){
        double average = 0.0;
        ArrayList<RateDTO> list = selectAll(movieId);

        if(list.isEmpty()){
            return 0.0;
        }

        for(RateDTO r : list){
            average += r.getRating();
        }

        return average / list.size();
    }
}
