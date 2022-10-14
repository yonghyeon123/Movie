package controller;

import connector.DBConnector;
import model.TheaterDTO;

import java.sql.Connection;

public class TheaterController {
    private Connection connection;

    public TheaterController(DBConnector connector){
        connection = connector.makeConnection();
    }

    public void insert(TheaterDTO theaterDTO){
        String query = "INSERT INTO `theater` ";
    }

    public void update(TheaterDTO theaterDTO){

    }

    public void delete(int id){

    }
}
