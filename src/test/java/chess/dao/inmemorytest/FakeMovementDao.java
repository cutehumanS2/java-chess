package chess.dao.inmemorytest;

import chess.dao.MovementDao;
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
        final Long roomId = requestDto.roomId();
        final MovementResponseDto responseDto = new MovementResponseDto(id, requestDto.roomId(),
                requestDto.sourceFile(), requestDto.sourceRank(), requestDto.targetFile(), requestDto.targetRank());
        movements.putIfAbsent(roomId, new ArrayList<>());
        movements.get(roomId).add(MovementResponseDto.toMovement(responseDto));
        return id++;
    }

    @Override
    public List<Movement> findMovementsById(Long roomId) {
        return movements.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getKey(), roomId))
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }
}
