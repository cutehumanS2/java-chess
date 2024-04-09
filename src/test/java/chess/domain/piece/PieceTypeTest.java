package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

class PieceTypeTest {

    @Nested
    class KingStrategyTest {

        @DisplayName("킹이 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"d, FIVE", "e, FIVE", "f, FIVE", "f, FOUR", "f, THREE", "e, THREE", "d, THREE", "d, FOUR"})
        void returnTrueIfKingCanMove(final File file, final Rank rank) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.d, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.f, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.f, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.f, Rank.THREE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.THREE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.d, Rank.THREE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.d, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);
            final PieceType pieceType = PieceType.KING;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("킹이 이동할 수 없으면 False를 리턴한다.")
        @Test
        void returnFalseIfKingCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.c, Rank.SEVEN), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.c, Rank.SIX);
            final PieceType pieceType = PieceType.KING;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class QueenStrategyTest {

        @DisplayName("퀸이 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"e, SIX", "e, ONE", "h, FOUR", "a, FOUR", "a, EIGHT", "h, SEVEN", "h, ONE", "b, ONE"})
        void returnTrueIfQueenCanMove(final File file, final Rank rank) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.e, Rank.SIX), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.ONE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.h, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.a, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.a, Rank.EIGHT), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.h, Rank.SEVEN), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.h, Rank.ONE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.b, Rank.ONE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);
            final PieceType pieceType = PieceType.QUEEN;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("퀸이 이동할 수 없으면 False를 리턴한다.")
        @Test
        void returnFalseIfQueenCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.g, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.g, Rank.FIVE);
            final PieceType pieceType = PieceType.QUEEN;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class RookStrategyTest {

        @DisplayName("룩이 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"e, EIGHT", "e, ONE", "h, FOUR", "a, FOUR"})
        void returnTrueIfRookCanMove(final File file, final Rank rank) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.e, Rank.EIGHT), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.ONE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.h, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.a, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);
            final PieceType pieceType = PieceType.ROOK;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isTrue();
        }


        @DisplayName("룩이 이동할 수 없으면 False를 리턴한다.")
        @Test
        void returnFalseIfRookCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.h, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.h, Rank.FIVE);
            final PieceType pieceType = PieceType.ROOK;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class BishopStrategyTest {

        @DisplayName("비숍이 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"h, SEVEN", "b, ONE", "h, ONE", "a, EIGHT"})
        void returnTrueIfBishopCanMove(final File file, final Rank rank) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.a, Rank.EIGHT), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.h, Rank.SEVEN), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.b, Rank.ONE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.h, Rank.ONE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);
            final PieceType pieceType = PieceType.BISHOP;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("비숍이 이동할 수 없으면 False를 리턴한다.")
        @Test
        void returnFalseIfBishopCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.e, Rank.ONE);
            final PieceType pieceType = PieceType.BISHOP;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class KnightStrategyTest {

        @DisplayName("나이트가 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"f, SIX", "d, SIX", "c, FIVE", "c, THREE", "d, TWO", "f, TWO", "g, THREE", "g, FIVE"})
        void returnTrueIfKnightCanMove(final File file, final Rank rank) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.f, Rank.SIX), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.d, Rank.SIX), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.c, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.c, Rank.THREE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.d, Rank.TWO), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.f, Rank.TWO), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.g, Rank.THREE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.g, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);
            final PieceType pieceType = PieceType.KNIGHT;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("나이트가 이동할 수 없으면 False를 리턴한다.")
        @Test
        void returnFalseIfKnightCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.e, Rank.SIX), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.e, Rank.SIX);
            final PieceType pieceType = PieceType.KNIGHT;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    class PawnStrategyTest {

        @DisplayName("폰이 이동할 수 있으면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"e, THREE", "e, FOUR", "f, THREE"})
        void returnTrueIfPawnCanMove(final File file, final Rank rank) {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.TWO), Piece.of(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.THREE), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.e, Rank.FOUR), Piece.of(PieceType.EMPTY, PieceColor.NONE),
                    new Square(File.f, Rank.THREE), Piece.of(PieceType.PAWN, PieceColor.BLACK)
            ));
            final Square source = new Square(File.e, Rank.TWO);
            final Square target = new Square(file, rank);
            final PieceType pieceType = PieceType.PAWN;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isTrue();
        }

        @DisplayName("폰이 이동할 수 없으면 False를 리턴한다.")
        @Test
        void returnFalseIfPawnCannotMove() {
            final Board board = new Board(Map.of(
                    new Square(File.e, Rank.TWO), Piece.of(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), Piece.of(PieceType.EMPTY, PieceColor.NONE)
            ));
            final Square source = new Square(File.e, Rank.TWO);
            final Square target = new Square(File.e, Rank.FIVE);
            final PieceType pieceType = PieceType.PAWN;

            boolean actual = pieceType.canMove(board, source, target);

            assertThat(actual).isFalse();
        }
    }
}
