package chess.service;

import chess.repository.ChessGameRepository;
import chess.repository.MovementRepository;
import chess.domain.game.GameStatus;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementRequestDto;

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
            gameRepository.update(gameId, currentStatus);
            return gameId;
        }
        return gameRepository.save(currentStatus);
    }

    public Long saveMovement(final Long gameId, final Square source, final Square target) {
        return movementRepository.save(MovementRequestDto.toDto(gameId, source, target));
    }

    public List<Movement> loadMovements(final Long gameId) {
        return movementRepository.findMovementsById(gameId);
    }
}
