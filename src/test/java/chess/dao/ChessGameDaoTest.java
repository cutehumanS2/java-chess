package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.game.GameStatus;
import chess.domain.piece.PieceColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ChessGameDaoTest {

    private final ChessGameRepository repository = new FakeChessGameDao();

    @DisplayName("현재 게임 상태를 저장한다.")
    @Test
    void saveGameStatus() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);

        Long actual = repository.save(gameStatus);

        assertThat(actual).isEqualTo(1L);
    }

    @DisplayName("게임 아이디로 게임 상태를 조회한다.")
    @Test
    void findGameStatusByGameId() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);
        final Long gameId = repository.save(gameStatus);

        Optional<GameStatus> actual = repository.findGameStatusById(gameId);

        assertThat(actual.get().getTurn()).isEqualTo(PieceColor.WHITE);
    }

    @DisplayName("게임 아이디에 해당하는 게임 상태가 없는 경우 빈 옵셔널을 리턴한다.")
    @Test
    void returnEmptyIfGameStatusByGameIdIsNotExist() {
        final Long gameId = 1L;

        Optional<GameStatus> actual = repository.findGameStatusById(gameId);

        assertThat(actual).isEmpty();
    }

    @DisplayName("게임 아이디에 해당하는 게임 상태를 업데이트한다.")
    @Test
    void updateGameStatusByGameId() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);
        final Long gameId = repository.save(gameStatus);

        repository.update(gameId, new GameStatus(PieceColor.BLACK));
        final Optional<GameStatus> actual = repository.findGameStatusById(gameId);

        assertThat(actual.get().getTurn()).isEqualTo(PieceColor.BLACK);
    }
}
