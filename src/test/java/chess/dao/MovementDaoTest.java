package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementDaoTest {

    private final MovementRepository repository = new FakeMovementDao();

    @DisplayName("기물의 움직임을 저장한다.")
    @Test
    void saveMovement() {
        final Long gameId = 1L;
        final Square source = new Square(File.b, Rank.TWO);
        final Square target = new Square(File.b, Rank.THREE);

        final Long actual = repository.save(gameId, source, target);

        assertThat(actual).isEqualTo(1L);
    }
}
