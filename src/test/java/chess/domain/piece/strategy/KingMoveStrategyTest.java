package chess.domain.piece.strategy;

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

import static org.assertj.core.api.Assertions.assertThat;

class KingMoveStrategyTest {

    @DisplayName("킹이 이동할 수 있으면 True를 리턴한다.")
    @ParameterizedTest
    @CsvSource({"d, FIVE", "e, FIVE", "f, FIVE", "f, FOUR", "f, THREE", "e, THREE", "d, THREE", "d, FOUR"})
    void returnTrueIfKingCanMove(final File file, final Rank rank) {
        final MoveStrategy moveStrategy = new KingMoveStrategy();
        final Board board = new Board(Map.of(
                new Square(File.e, Rank.FOUR), new Piece(PieceType.BISHOP, PieceColor.WHITE),
                new Square(File.d, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.e, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.f, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.f, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.f, Rank.THREE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.e, Rank.THREE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.d, Rank.THREE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.d, Rank.FOUR), new Piece(PieceType.EMPTY, PieceColor.NONE)
        ));
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(file, rank);

        boolean actual = moveStrategy.canMove(board, source, target);

        assertThat(actual).isTrue();
    }

    @DisplayName("킹이 이동할 수 없으면 False를 리턴한다.")
    @Test
    void returnFalseIfKingCannotMove() {
        final MoveStrategy moveStrategy = new KingMoveStrategy();
        final Board board = new Board(Map.of(
                new Square(File.e, Rank.FOUR), new Piece(PieceType.BISHOP, PieceColor.WHITE),
                new Square(File.c, Rank.SEVEN), new Piece(PieceType.EMPTY, PieceColor.NONE)
        ));
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.c, Rank.SIX);

        boolean actual = moveStrategy.canMove(board, source, target);

        assertThat(actual).isFalse();
    }

}
