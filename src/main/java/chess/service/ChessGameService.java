package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MovementDao;
import chess.domain.game.GameStatus;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementRequestDto;

import java.util.List;
import java.util.Optional;

public class ChessGameService {

    private final ChessGameDao gameRepository;
    private final MovementDao movementDao;

    public ChessGameService(final ChessGameDao gameRepository, final MovementDao movementDao) {
        this.gameRepository = gameRepository;
        this.movementDao = movementDao;
    }

    public Long upsertCurrentTurn(final GameStatus currentStatus) {
        final Long gameId = 1L;
        final Optional<GameStatus> gameStatus = gameRepository.findGameStatusById(gameId);

        if (gameStatus.isPresent()) {
            gameRepository.update(gameId, currentStatus);
            return gameId;
        }
        return gameRepository.save(currentStatus);
    }

    public Long saveMovement(final Long gameId, final Square source, final Square target) {
        return movementDao.save(MovementRequestDto.toDto(gameId, source, target));
    }

    public List<Movement> loadMovements(final Long gameId) {
        return movementDao.findMovementsById(gameId);
    }
}
