package chess.service;

import chess.dao.ChessGameRepository;
import chess.domain.game.GameStatus;

import java.util.Optional;

public class ChessGameService {

    private final ChessGameRepository gameRepository;

    public ChessGameService(ChessGameRepository gameRepository) {
        this.gameRepository = gameRepository;
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
}
