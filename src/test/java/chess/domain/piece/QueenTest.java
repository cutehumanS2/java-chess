package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Board;
import chess.domain.PieceColor;
import chess.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

@DisplayName("퀸")
class QueenTest {

    @ParameterizedTest
    @ValueSource(strings = {"a8", "c8", "e8", "h6", "h1", "c1", "a4", "a6"})
    @DisplayName("에 대한 이동 루트가 상하좌우 대각선 중 하나인지 판단한다.")
    void canMove(String target) {
        Board board = new Board(Map.of());
        Queen queen = new Queen(PieceColor.BLACK);

        boolean actual = queen.canMove(Position.from("c6"), Position.from(target), board);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("목적지로 향하는 경로에 다른 기물이 존재하면 이동할 수 없다.")
    void cannotMove() {
        Board board = new Board(Map.of(Position.from("d5"), new Rook(PieceColor.BLACK)));
        Queen queen = new Queen(PieceColor.BLACK);

        boolean actual = queen.canMove(Position.from("c6"), Position.from("h1"), board);

        assertThat(actual).isFalse();
    }
}
