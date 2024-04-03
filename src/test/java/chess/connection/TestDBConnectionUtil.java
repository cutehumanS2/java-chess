package chess.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnectionUtil {

    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "110423";
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess_test";
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    private TestDBConnectionUtil() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }
}
