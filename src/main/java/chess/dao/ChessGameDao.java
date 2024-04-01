package chess.dao;

import chess.domain.game.GameStatus;

import java.util.Optional;

public interface ChessGameDao {

    Long save(final GameStatus gameStatus);

    Optional<GameStatus> findGameStatusById(final Long gameId);

    void update(final Long gameId, final GameStatus gameStatus);
}
