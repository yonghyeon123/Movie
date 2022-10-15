package controller;

import connector.DBConnector;
import model.TheaterDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TheaterController {
    private Connection connection;

    public TheaterController(DBConnector connector){
        connection = connector.makeConnection();
    }

    /**
     * 영화 정보 추가
     * @param theaterDTO 추가할 영화의 정보
     */
    public void insert(TheaterDTO theaterDTO){
        String query = "INSERT INTO `theater` (`theaterName`, `location`, `number`) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, theaterDTO.getTheaterName());
            pstmt.setString(2, theaterDTO.getLocation());
            pstmt.setString(3, theaterDTO.getNumber());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 극정 정보 업데이트
     * @param theaterDTO 수정할 극장의 정보
     */
    public void update(TheaterDTO theaterDTO){
        String query = "UPDATE `theater` SET `theaterName` = ?, `location` = ?, `number` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, theaterDTO.getTheaterName());
            pstmt.setString(2, theaterDTO.getLocation());
            pstmt.setString(3, theaterDTO.getNumber());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 극장 정보 삭제
     * @param id 삭제할 극장의 번호
     */
    public void delete(int id){
        String query = "DELETE FROM `theater` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
