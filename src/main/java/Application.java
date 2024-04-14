import chess.controller.ChessGameController;
import chess.controller.RoomController;
import chess.dao.MovementDao;
import chess.dao.MovementDaoImpl;
import chess.dao.RoomDao;
import chess.dao.RoomDaoImpl;
import chess.service.ChessGameService;
import chess.service.RoomService;
import chess.view.InputView;
import chess.view.outputview.ChessGameOutputView;
import chess.view.outputview.RoomOutputView;

public class Application {

    public static void main(final String[] args) {

        final InputView inputView = new InputView();

        final MovementDao movementDao = new MovementDaoImpl();
        final ChessGameService gameService = new ChessGameService(movementDao);
        final ChessGameController chessGame = new ChessGameController(inputView, new ChessGameOutputView(), gameService);

        final RoomDao roomDao = new RoomDaoImpl();
        final RoomService roomService = new RoomService(roomDao);
        final RoomController roomController = new RoomController(inputView, new RoomOutputView(), roomService, chessGame);
        roomController.run();
    }
}
