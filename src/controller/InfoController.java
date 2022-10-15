package controller;

import connector.DBConnector;
import model.InfoDTO;

import java.sql.Connection;

public class InfoController {
    private Connection connection;

    public InfoController(DBConnector connector){
        connection = connector.makeConnection();
    }

    public void insert(InfoDTO infoDTO){

    }
}
