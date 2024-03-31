import chess.controller.ChessGame;
import chess.dao.ChessGameDao;
import chess.dao.ChessGameRepository;
import chess.service.ChessGameService;

public class Application {

    public static void main(final String[] args) {

        final ChessGameRepository gameRepository = new ChessGameDao();
        final ChessGameService gameService = new ChessGameService(gameRepository);

        final ChessGame chessGame = new ChessGame(gameService);
        chessGame.run();
    }
}
