package controller;

import connector.DBConnector;
import model.InfoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InfoController {
    private Connection connection;

    public InfoController(DBConnector connector){
        connection = connector.makeConnection();
    }

    public void insert(InfoDTO infoDTO){
        String query = "INSERT INTO `info` (`movieId`, `theaterId`, `screenTime`) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, infoDTO.getMovieId());
            pstmt.setInt(2, infoDTO.getTheaterId());
            pstmt.setString(3, infoDTO.getScreenTime());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(InfoDTO infoDTO){
        String query = "UPDATE `info` SET `screenTime` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, infoDTO.getScreenTime());
            pstmt.setInt(2, infoDTO.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        String query = "DELETE FROM `info` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
