package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dao.ChessGameRepository;
import chess.dao.FakeChessGameDao;
import chess.dao.FakeMovementDao;
import chess.dao.MovementRepository;
import chess.domain.game.GameStatus;
import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameServiceTest {

    private final ChessGameRepository gameRepository = new FakeChessGameDao();
    private final MovementRepository movementRepository = new FakeMovementDao();
    private final ChessGameService service = new ChessGameService(gameRepository, movementRepository);

    @DisplayName("현재 게임에 대한 정보가 없으면, 현재 게임 정보를 저장한다.")
    @Test
    void saveCurrentGameStatusIfNotPresent() {
        final GameStatus currentStatus = new GameStatus(PieceColor.WHITE);

        final Long actual = service.saveCurrentTurn(currentStatus);

        assertThat(actual).isEqualTo(1L);
    }

    @DisplayName("현재 게임에 대한 정보가 있으면, 현재 게임 정보를 업데이트한다.")
    @Test
    void updateCurrentGameStatusIfPresent() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);
        gameRepository.save(gameStatus);

        final GameStatus currentStatus = new GameStatus(PieceColor.BLACK);
        final Long actualId = service.saveCurrentTurn(currentStatus);
        final GameStatus actualStatus = gameRepository.findGameStatusById(actualId).get();

        assertThat(actualStatus.getTurn()).isEqualTo(PieceColor.BLACK);
    }

    @DisplayName("기물의 움직임을 저장한다.")
    @Test
    void saveMovement() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);
        final Long gameId = gameRepository.save(gameStatus);
        final Square source = new Square(File.b, Rank.TWO);
        final Square target = new Square(File.b, Rank.THREE);

        final Long actual = service.saveMovement(gameId, source, target);

        assertThat(actual).isEqualTo(1L);
    }
}
