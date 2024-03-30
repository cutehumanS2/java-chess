package chess.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static chess.ConnectionConst.PASSWORD;
import static chess.ConnectionConst.URL;
import static chess.ConnectionConst.USERNAME;

public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }
}
