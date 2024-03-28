package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

class BoardTest {

    @Nested
    class moveExceptionTest {

        @DisplayName("기물의 출발지와 목적지가 같으면 예외가 발생한다.")
        @Test
        void occurExceptionWhenSourceAndTargetAreSameSquare() {
            final Board board = new Board(Map.of(
                    new Square(File.b, Rank.THREE), new Piece(PieceType.ROOK, PieceColor.BLACK)));
            final Square source = new Square(File.b, Rank.THREE);
            final Square target = new Square(File.b, Rank.THREE);
            final PieceColor turn = PieceColor.WHITE;

            assertThatThrownBy(() -> board.move(source, target, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("해당 위치에 기물이 존재하지 않으면 예외가 발생한다.")
        @Test
        void occurExceptionWhenNotExistPiece() {
            final Board board = new Board(Map.of());
            final Square source = new Square(File.b, Rank.THREE);
            final Square target = new Square(File.b, Rank.FOUR);
            final PieceColor turn = PieceColor.WHITE;

            assertThatThrownBy(() -> board.move(source, target, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("상대방 팀의 기물을 이동하려는 경우 예외가 발생한다.")
        @Test
        void occurExceptionWhenNotMyTurn() {
            final Board board = new Board(Map.of());
            final Square source = new Square(File.b, Rank.SEVEN);
            final Square target = new Square(File.b, Rank.SIX);
            final PieceColor turn = PieceColor.WHITE;

            assertThatThrownBy(() -> board.move(source, target, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("각 기물의 이동 방식으로 갈 수 없는 목적지인 경우 예외가 발생한다.")
        @Test
        void occurExceptionWhenCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.b, Rank.THREE), new Piece(PieceType.ROOK, PieceColor.BLACK)));
            final Square source = new Square(File.b, Rank.THREE);
            final Square target = new Square(File.b, Rank.FOUR);
            final PieceColor turn = PieceColor.WHITE;

            assertThatThrownBy(() -> board.move(source, target, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("이동 경로에 다른 기물이 있으면 예외가 발생한다.")
        @Test
        void occurExceptionWhenExistObstacleOnPath() {
            final Board board = new Board(Map.of(
                    new Square(File.b, Rank.THREE), new Piece(PieceType.ROOK, PieceColor.WHITE),
                    new Square(File.b, Rank.FOUR), new Piece(PieceType.ROOK, PieceColor.WHITE),
                    new Square(File.b, Rank.FIVE), new Piece(PieceType.ROOK, PieceColor.WHITE)
            ));
            final Square source = new Square(File.b, Rank.THREE);
            final Square target = new Square(File.b, Rank.FIVE);
            final PieceColor turn = PieceColor.WHITE;

            assertThatThrownBy(() -> board.move(source, target, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("출발지에서 목적지로 기물을 이동한다.")
    @Test
    void movePieceFromSourceToTarget() {
        final Board board = new Board(Map.of(
                new Square(File.b, Rank.TWO), new Piece(PieceType.ROOK, PieceColor.WHITE),
                new Square(File.b, Rank.THREE), new Piece(PieceType.ROOK, PieceColor.WHITE)
        ));
        final Square source = new Square(File.b, Rank.TWO);
        final Square target = new Square(File.b, Rank.THREE);
        final PieceColor turn = PieceColor.WHITE;

        board.move(source, target, turn);

        assertThat(board.getPieces()).containsKey(new Square(File.b, Rank.THREE));
    }

    @Nested
    class calculateTotalScoreTest {

        @DisplayName("각 진영마다 현재 보드에 남아 있는 말에 대한 점수를 구한다.")
        @ParameterizedTest
        @CsvSource({"WHITE, 20.5", "BLACK, 19"})
        void calculateScore(final PieceColor color, final double expected) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.QUEEN, PieceColor.WHITE),
                    new Square(File.c, Rank.THREE), new Piece(PieceType.ROOK, PieceColor.WHITE),
                    new Square(File.b, Rank.THREE), new Piece(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.a, Rank.THREE), new Piece(PieceType.KNIGHT, PieceColor.WHITE),
                    new Square(File.d, Rank.THREE), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.QUEEN, PieceColor.BLACK),
                    new Square(File.c, Rank.FIVE), new Piece(PieceType.ROOK, PieceColor.BLACK),
                    new Square(File.b, Rank.FIVE), new Piece(PieceType.BISHOP, PieceColor.BLACK),
                    new Square(File.d, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.f, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK)
            ));

            final double actual = board.calculateTotalScore(color);

            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("같은 세로줄에 같은 색의 폰이 있는 경우, 각 폰의 점수를 0.5로 계산한다.")
        @Test
        void calculateScoreWhenPawnCountIsMoreThanOneOnSameFile() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE)
            ));

            final double actual = board.calculateTotalScore(PieceColor.WHITE);

            assertThat(actual).isEqualTo(1);
        }

        @DisplayName("세로줄 당 폰이 하나씩만 있는 경우, 각 폰의 점수를 1로 계산한다.")
        @Test
        void calculateScoreWhenPawnCountIsOneOnSameFile() {
            final Board board = new Board(Map.of(
                    new Square(File.d, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE)
            ));

            final double actual = board.calculateTotalScore(PieceColor.WHITE);

            assertThat(actual).isEqualTo(2);
        }
    }
}
