package chess.repository;

import chess.dto.Movement;
import chess.dto.MovementRequestDto;

import java.util.List;

public interface MovementRepository {

    Long save(final MovementRequestDto requestDto);

    List<Movement> findMovementsById(final Long gameId);
}
