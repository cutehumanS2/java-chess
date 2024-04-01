package chess.dao;

import chess.dto.Movement;
import chess.dto.MovementRequestDto;

import java.util.List;

public interface MovementDao {

    Long save(final MovementRequestDto requestDto);

    List<Movement> findMovementsById(final Long gameId);
}
