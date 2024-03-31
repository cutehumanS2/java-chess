package chess.service;

import chess.dao.ChessGameRepository;
import chess.dao.MovementRepository;
import chess.domain.game.GameStatus;
import chess.domain.square.Square;
import chess.dto.Movement;

import java.util.List;
import java.util.Optional;

public class ChessGameService {

    private final ChessGameRepository gameRepository;
    private final MovementRepository movementRepository;

    public ChessGameService(final ChessGameRepository gameRepository, final MovementRepository movementRepository) {
        this.gameRepository = gameRepository;
        this.movementRepository = movementRepository;
    }

    public Long saveCurrentTurn(final GameStatus currentStatus) {
        final Long gameId = 1L;
        final Optional<GameStatus> gameStatus = gameRepository.findGameStatusById(gameId);

        if (gameStatus.isPresent()) {
            System.out.println(currentStatus.getTurn());
            gameRepository.update(gameId, currentStatus);
            return gameId;
        }
        return gameRepository.save(currentStatus);
    }

    public Long saveMovement(final Long gameId, final Square source, final Square target) {
        return movementRepository.save(gameId, source, target);
    }

    public List<Movement> loadMovements(final Long gameId) {
        return movementRepository.findMovementsById(gameId);
    }
}
