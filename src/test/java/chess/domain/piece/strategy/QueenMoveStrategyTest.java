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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

class QueenMoveStrategyTest {

    @DisplayName("퀸이 이동할 수 있으면 True를 리턴한다.")
    @ParameterizedTest
    @CsvSource({"e, SIX", "e, ONE", "h, FOUR", "a, FOUR", "a, EIGHT", "h, SEVEN", "h, ONE", "b, ONE"})
    void returnTrueIfQueenCanMove(final File file, final Rank rank) {
        final MoveStrategy moveStrategy = new QueenMoveStrategy();
        final Board board = new Board(Map.of(
                new Square(File.e, Rank.FOUR), new Piece(PieceType.BISHOP, PieceColor.WHITE),
                new Square(File.e, Rank.SIX), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.e, Rank.ONE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.h, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.a, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.a, Rank.EIGHT), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.h, Rank.SEVEN), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.h, Rank.ONE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.b, Rank.ONE), new Piece(PieceType.EMPTY, PieceColor.NONE)
        ));
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(file, rank);

        boolean actual = moveStrategy.canMove(board, source, target);

        assertThat(actual).isTrue();
    }

    @DisplayName("퀸이 이동할 수 없으면 False를 리턴한다.")
    @Test
    void returnFalseIfQueenCannotMove() {
        final MoveStrategy moveStrategy = new QueenMoveStrategy();
        final Board board = new Board(Map.of(
                new Square(File.e, Rank.FOUR), new Piece(PieceType.BISHOP, PieceColor.WHITE),
                new Square(File.g, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE)
        ));
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.g, Rank.FIVE);

        boolean actual = moveStrategy.canMove(board, source, target);

        assertThat(actual).isFalse();
    }
}
