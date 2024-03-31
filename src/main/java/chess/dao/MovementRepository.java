package chess.dao;

import chess.domain.square.Square;

public interface MovementRepository {

    Long save(final Long gameId, final Square source, final Square target);
}
