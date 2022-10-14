package controller;

import connector.DBConnector;
import model.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private Connection connection;

    public UserController(DBConnector dbConnector){
        connection = dbConnector.makeConnection();
    }

    /**
     * 회원 정보 추가
     * @param userDTO 추가할 회원 정보
     * @return 회원가입이 이루어졌다면 true 아니라면 false
     */
    public boolean insert(UserDTO userDTO){
        String query = "INSERT INTO `user` (`username`, `password`, `nickname`, `grade`) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userDTO.getUsername());
            pstmt.setString(2, userDTO.getPassword());
            pstmt.setString(3, userDTO.getNickname());
            pstmt.setInt(4, userDTO.getGrade());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * 회원 정보 수정
     * @param userDTO 수정할 회원 정보
     */
    public void update(UserDTO userDTO){
        String query = "UPDATE `user` SET `password` = ?, `nickname` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userDTO.getPassword());
            pstmt.setString(2, userDTO.getNickname());
            pstmt.setInt(3, userDTO.getId());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원 정보 삭제
     * @param id 삭제할 회원의 번호
     */
    public void delete(int id){
        String query = "DELETE FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 로그인을 하기 위한 메소드
     * @param username 로그인할 아이디
     * @param password 로그인할 패스워드
     * @return 로그인된 유저 정보(로그인이 되지 않았다면 null)
     */
    public UserDTO auth(String username, String password){
        String query = "SELECT * FROM `user` WHERE `username` = ? AND `password` = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                UserDTO u = new UserDTO();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setNickname(rs.getString("nickname"));
                u.setGrade(rs.getInt("grade"));

                return u;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 데이터베이스가 비어있는지 확인
     * @return 비어있다면 true, 아니면 false 반환
     */
    public boolean dbIsEmpty(){
        String query = "SELECT * FROM `user`";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()){
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * int로 저장된 회원 등급을 String으로 변환하는 메소드
     * @param grade int로 저장된 회원 등급
     * @return String으로 변환된 회원 등급
     */
    public String parseGradeToString(int grade){
        String stringGrade = "";

        switch (grade){
            case 1:
                stringGrade = "일반 관람객";
                break;
            case 2:
                stringGrade = "전문 평론가";
                break;
            case 3:
                stringGrade = "관리자";
                break;
        }

        return stringGrade;
    }

    public String getNicknameById(int id){
        String nickname = "";
        String query = "SELECT `nickname` FROM `user` WHERE `id` = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                nickname = rs.getString("nickname");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return nickname;
    }
}
