package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dao.ChessGameRepository;
import chess.dao.FakeChessGameDao;
import chess.domain.game.GameStatus;
import chess.domain.piece.PieceColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameServiceTest {

    private final ChessGameRepository repository = new FakeChessGameDao();
    private final ChessGameService service = new ChessGameService(repository);

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
        repository.save(gameStatus);

        final GameStatus currentStatus = new GameStatus(PieceColor.BLACK);
        final Long actualId = service.saveCurrentTurn(currentStatus);
        final GameStatus actualStatus = repository.findGameStatusById(actualId).get();

        assertThat(actualStatus.getTurn()).isEqualTo(PieceColor.BLACK);
    }
}
