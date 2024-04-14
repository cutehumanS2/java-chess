package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class MovementDaoTest {

    private final MovementDaoTestImpl movementDao = new MovementDaoTestImpl();

    @AfterEach
    public void tearDown() {
        movementDao.truncate();
    }

    @DisplayName("기물의 움직임을 저장한다.")
    @Test
    void saveMovement() {
        final Long roomId = 1L;
        final Square source = new Square(File.b, Rank.TWO);
        final Square target = new Square(File.b, Rank.THREE);
        final MovementRequestDto requestDto = MovementRequestDto.toDto(roomId, source, target);
        final Long actual = movementDao.save(requestDto);

        assertThat(actual).isEqualTo(1L);
    }

    @DisplayName("방 아이디에 해당하는 기물의 움직임들을 조회한다.")
    @Test
    void findMovementsByGameId() {
        final Long roomId = 1L;
        movementDao.save(MovementRequestDto.toDto(roomId, new Square(File.b, Rank.TWO), new Square(File.b, Rank.THREE)));
        movementDao.save(MovementRequestDto.toDto(roomId, new Square(File.b, Rank.SEVEN), new Square(File.b, Rank.SIX)));

        final List<Movement> actual = movementDao.findMovementsById(roomId);

        assertThat(actual).hasSize(2);
    }
}
