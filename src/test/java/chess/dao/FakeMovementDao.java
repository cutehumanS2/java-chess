package chess.dao;

import chess.dto.Movement;
import chess.dto.MovementRequestDto;
import chess.dto.MovementResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeMovementDao implements MovementDao {

    private final Map<Long, List<Movement>> movements;
    private Long id = 1L;

    public FakeMovementDao() {
        this.movements = new HashMap<>();
    }

    @Override
    public Long save(final MovementRequestDto requestDto) {
        final Long gameId = requestDto.gameId();
        final MovementResponseDto responseDto = new MovementResponseDto(id, requestDto.gameId(),
                requestDto.sourceFile(), requestDto.sourceRank(), requestDto.targetFile(), requestDto.targetRank());
        movements.putIfAbsent(gameId, new ArrayList<>());
        movements.get(gameId).add(MovementResponseDto.toMovement(responseDto));
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
