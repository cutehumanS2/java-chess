import chess.controller.ChessGame;
import chess.dao.ChessGameDaoImpl;
import chess.dao.ChessGameDao;
import chess.dao.MovementDaoImpl;
import chess.dao.MovementDao;
import chess.service.ChessGameService;

public class Application {

    public static void main(final String[] args) {

        final ChessGameDao gameRepository = new ChessGameDaoImpl();
        final MovementDao movementDao = new MovementDaoImpl();
        final ChessGameService gameService = new ChessGameService(gameRepository, movementDao);

        final ChessGame chessGame = new ChessGame(gameService);
        chessGame.run();
    }
}
