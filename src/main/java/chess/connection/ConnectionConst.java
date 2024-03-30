package chess.connection;

public abstract class ConnectionConst {

    public static final String SERVER = "localhost:3306";
    public static final String DATABASE = "chess";
    public static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "110423";
}
