package chess.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
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

class PawnMoveStrategyTest {

    @Nested
    class BackwardTest {

        @DisplayName("검정색 폰이 아래로 이동하면 True를 리턴한다.")
        @Test
        void returnTrueWhenBlackPawnMoveDown() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.SEVEN), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.SEVEN);
            final Square target = new Square(File.e, Rank.FIVE);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("검정색 폰이 위로 이동하면 False를 리턴한다.")
        @Test
        void returnFalseWhenBlackPawnMoveUp() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.SEVEN), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.e, Rank.EIGHT), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.SEVEN);
            final Square target = new Square(File.e, Rank.EIGHT);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isFalse();
        }

        @DisplayName("흰색 폰이 위로 이동하면 True를 리턴한다.")
        @Test
        void returnTrueWhenWhitePawnMoveUp() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.TWO), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.TWO);
            final Square target = new Square(File.e, Rank.FOUR);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("흰색 폰이 아래로 이동하면 False를 리턴한다.")
        @Test
        void returnFalseWhenWhitePawnMoveDown() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.TWO), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.ONE), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.TWO);
            final Square target = new Square(File.e, Rank.ONE);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class FirstMoveTest {

        @DisplayName("첫 번째 이동 시, 수직으로 최대 두 칸까지 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"TWO, e, THREE", "TWO, e, FOUR", "SEVEN, e, SIX", "SEVEN, e, FIVE"})
        void returnTrueIfPawnMoveVerticalUpToTwoStepWhenFirstMove(
                final Rank sourceRank, final File file, final Rank targetRank) {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.TWO), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.THREE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.SEVEN), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, sourceRank);
            final Square target = new Square(file, targetRank);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isTrue();
        }
    }

    @Nested
    class NormalMoveTest {

        @DisplayName("두 번째 이동부터는 수직으로 한 칸씩 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"THREE, e, FOUR", "SIX, e, FIVE"})
        void returnTrueIfCanMoveVerticalOnlyOneStepAfterFirstMove(
                final Rank sourceRank, final File file, final Rank targetRank) {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.THREE), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.SIX), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, sourceRank);
            final Square target = new Square(file, targetRank);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("두 번째 이동부터 수직으로 두 칸 이상 이동하면 False를 리턴한다.")
        @Test
        void returnFalseIfExceedStepLimitAfterFirstMove() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.SIX), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.e, Rank.SIX);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isFalse();
        }

        @DisplayName("수직으로 이동하지 않으면 False를 리턴한다.")
        @Test
        void returnFalseWhenNotVertical() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.SIX), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.e, Rank.SIX);

            final boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class AttackTest {

        @DisplayName("공격을 할 수 있으면 True를 리턴한다.")
        @Test
        void returnTrueWhenCanAttack() {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.f, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.f, Rank.FIVE);

            boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("공격을 할 수 없으면 False를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"e, FIVE", "d, SIX"})
        void returnFalseWhenCannotAttack(final File file, final Rank rank) {
            final MoveStrategy moveStrategy = new PawnMoveStrategy();
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.ROOK, PieceColor.BLACK),
                    new Square(File.d, Rank.SIX), new Piece(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);

            boolean actual = moveStrategy.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }
}
