package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector implements DBConnector {
    private final String ADDRESS = "jdbc:mysql://localhost:3306/movieproject";
    private final String USERNAME = "root";
    private final String PASSWORD = "1111";

    @Override
    public Connection makeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(ADDRESS, USERNAME, PASSWORD);

            return connection;
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
