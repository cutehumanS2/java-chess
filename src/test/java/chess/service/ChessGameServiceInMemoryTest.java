package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dao.inmemorytest.FakeMovementDao;
import chess.dao.MovementDao;
import chess.domain.game.GameStatus;
import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ChessGameServiceInMemoryTest {

    private final MovementDao movementDao = new FakeMovementDao();
    private final ChessGameService service = new ChessGameService(movementDao);


    @DisplayName("기물의 움직임을 저장한다.")
    @Test
    void saveMovement() {
        final Long roomId = 1L;
        final Square source = new Square(File.b, Rank.TWO);
        final Square target = new Square(File.b, Rank.THREE);

        final Long actual = service.saveMovement(roomId, source, target);

        assertThat(actual).isEqualTo(1L);
    }

    @DisplayName("게임 아이디에 해당하는 기물의 움직임들을 조회한다.")
    @Test
    void findMovementsByGameId() {
        final Long roomId = 1L;
        movementDao.save(MovementRequestDto.toDto(roomId, new Square(File.b, Rank.TWO), new Square(File.b, Rank.THREE)));
        movementDao.save(MovementRequestDto.toDto(roomId, new Square(File.b, Rank.SEVEN), new Square(File.b, Rank.SIX)));

        final List<Movement> actual = service.loadMovements(roomId);

        assertThat(actual).hasSize(2);
    }
}
