import chess.controller.ChessGame;
import chess.repository.dao.ChessGameDao;
import chess.repository.ChessGameRepository;
import chess.repository.dao.MovementDao;
import chess.repository.MovementRepository;
import chess.service.ChessGameService;

public class Application {

    public static void main(final String[] args) {

        final ChessGameRepository gameRepository = new ChessGameDao();
        final MovementRepository movementRepository = new MovementDao();
        final ChessGameService gameService = new ChessGameService(gameRepository, movementRepository);

        final ChessGame chessGame = new ChessGame(gameService);
        chessGame.run();
    }
}
