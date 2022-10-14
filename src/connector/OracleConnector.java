package connector;

import java.sql.Connection;

public class OracleConnector implements DBConnector{

    @Override
    public Connection makeConnection() {
        System.out.println("Starting Oracle Connection");

        System.out.println("Oracle Connection Made");
        return null;
    }
}
