package chess.service;

import chess.dao.MovementDao;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementRequestDto;

import java.util.List;

public class ChessGameService {

    //private final ChessGameDao gameDao;
    private final MovementDao movementDao;

    //    public ChessGameService(final ChessGameDao gameDao, final MovementDao movementDao) {
//        this.gameDao = gameDao;
//        this.movementDao = movementDao;
//    }
    public ChessGameService(final MovementDao movementDao) {
        this.movementDao = movementDao;
    }

//    public Long upsertCurrentTurn(final GameStatus currentStatus, final Long roomId) {
//        final Optional<GameStatus> gameStatus = gameDao.findGameStatusById(roomId);
//
//        if (gameStatus.isPresent()) {
//            gameDao.update(roomId, currentStatus);
//            return roomId;
//        }
//        return gameDao.save(currentStatus);
//    }

    public Long saveMovement(final Long roomId, final Square source, final Square target) {
        return movementDao.save(MovementRequestDto.toDto(roomId, source, target));
    }

    public List<Movement> loadMovements(final Long roomId) {
        return movementDao.findMovementsById(roomId);
    }
}
