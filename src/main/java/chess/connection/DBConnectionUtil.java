package chess.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "110423";

    private DBConnectionUtil() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }
}
