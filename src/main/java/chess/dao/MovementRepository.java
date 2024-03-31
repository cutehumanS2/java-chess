package chess.dao;

import chess.domain.square.Square;
import chess.dto.Movement;

import java.util.List;

public interface MovementRepository {

    Long save(final Long gameId, final Square source, final Square target);

    List<Movement> findMovementsById(final Long gameId);
}
