package controller;

import connector.DBConnector;
import model.MovieDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieController {
    private Connection connection;

    public MovieController(DBConnector connector){
        connection = connector.makeConnection();
    }

    /**
     * 영화 정보 추가
     * @param movieDTO 추가할 영화 정보
     */
    public void insert(MovieDTO movieDTO){
        String query = "INSERT INTO `movie` (`title`, `storyLine`, `degree`) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movieDTO.getTitle());
            pstmt.setString(2, movieDTO.getStoryLine());
            pstmt.setInt(3, movieDTO.getDegree());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 영화 정보 수정
     * @param movieDTO 수정할 영화 정보
     */
    public void update(MovieDTO movieDTO){
        String query = "UPDATE `movie` SET `title` = ?, `storyLine` = ?, `degree` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movieDTO.getTitle());
            pstmt.setString(2, movieDTO.getStoryLine());
            pstmt.setInt(3, movieDTO.getDegree());
            pstmt.setInt(4, movieDTO.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 영화 정보 삭제
     * @param id 삭제할 영화 번호
     */
    public void delete(int id){
        String query = "DELETE FROM `movie` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 영화 정보 전체 출력
     * @return 데이터베이스에 저장되어있는 영화 정보
     */
    public ArrayList<MovieDTO> selectAll(){
        ArrayList<MovieDTO> list = new ArrayList<>();
        String query = "SELECT * FROM `movie`";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                MovieDTO m = new MovieDTO();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setStoryLine(rs.getString("storyLine"));
                m.setDegree(rs.getInt("degree"));

                list.add(m);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 원하는 영화 정보 출력
     * @param id 출력하고 싶은 영화의 번호
     * @return 원하는 영화 정보
     */
    public MovieDTO selectOne(int id){
        MovieDTO movieDTO = new MovieDTO();
        String query = "SELECT * FROM `movie` WHERE `id` = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                MovieDTO m = new MovieDTO();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setStoryLine(rs.getString("storyLine"));
                m.setDegree(rs.getInt("degree"));

                return m;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * int로 저장된 영화등급을 String 으로 변환
     * @param movieDegree 변환할 int형 영화등급
     * @return String으로 변환된 영화등급
     */
    public String parseDegreeToString(int movieDegree){
        String degree = "";

        switch (movieDegree){
            case 1:
                degree = "전체 관람가";
                break;
            case 2:
                degree = "12세 관람가";
                break;
            case 3:
                degree = "15세 관람가";
                break;
            case 4:
                degree = "청소년 관람불가";
                break;
        }

        return degree;
    }
}
