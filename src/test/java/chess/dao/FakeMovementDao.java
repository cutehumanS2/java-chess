package chess.dao;

import chess.domain.square.Square;
import chess.dto.MovementResponseDto;

import java.util.ArrayList;
import java.util.List;

public class FakeMovementDao implements MovementRepository {

    private final List<MovementResponseDto> movements;
    private Long id = 1L;

    public FakeMovementDao() {
        this.movements = new ArrayList<>();
    }

    @Override
    public Long save(final Long gameId, final Square source, final Square target) {
        final String sourceString = source.file().name() + source.rank().getIndex();
        final String targetString = target.file().name() + target.rank().getIndex();
        movements.add(new MovementResponseDto(id, gameId, sourceString, targetString));
        return id++;
    }
}
