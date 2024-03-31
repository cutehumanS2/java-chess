package chess.dao;

import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeMovementDao implements MovementRepository {

    private final Map<Long, List<Movement>> movements;
    private Long id = 1L;

    public FakeMovementDao() {
        this.movements = new HashMap<>();
    }

    @Override
    public Long save(final Long gameId, final Square source, final Square target) {
        final String sourceString = source.file().name() + source.rank().getIndex();
        final String targetString = target.file().name() + target.rank().getIndex();
        movements.putIfAbsent(gameId, new ArrayList<>());
        movements.get(gameId).add(MovementResponseDto.toEntity(new MovementResponseDto(id, gameId, sourceString, targetString)));
        return id++;
    }

    @Override
    public List<Movement> findMovementsById(Long gameId) {
        return movements.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getKey(), gameId))
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }
}
