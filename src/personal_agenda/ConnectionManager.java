package personal_agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String DB_URL = "jdbc:mariadb://localhost:3307/personal_agenda";
    private static final String USERNAME = "cris";
    private static final String PASSWORD = "crissy";
    private ConnectionManager() { };

    private static ConnectionManager connectionManager = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return connectionManager;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
