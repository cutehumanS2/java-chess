package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

class PieceTest {

    @DisplayName("출발지에서 목적지로 이동할 수 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({"e, FIVE, true", "f, SIX, false"})
    void checkCanMoveFromSourceToTarget(final File targetFile, final Rank targetRank, final boolean expected) {
        final Board board = new Board(Map.of(
                new Square(File.e, Rank.FOUR), new Piece(PieceType.QUEEN, PieceColor.WHITE),
                new Square(File.e, Rank.FIVE), new Piece(PieceType.EMPTY, PieceColor.NONE),
                new Square(File.f, Rank.SIX), new Piece(PieceType.EMPTY, PieceColor.NONE)
        ));
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(targetFile, targetRank);
        final Piece piece = new Piece(PieceType.QUEEN, PieceColor.WHITE);

        final boolean actual = piece.canMove(board, source, target);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("두 기물이 같은 색상인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"WHITE, true", "BLACK, false"})
    void returnTrueWhenPiecesAreSameColor(final PieceColor other, final boolean expected) {
        final Piece piece = new Piece(PieceType.ROOK, PieceColor.WHITE);

        final boolean actual = piece.isSameColor(other);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("기물의 색상이 검정인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"BLACK, true", "WHITE, false"})
    void checkPieceColorIsBlack(final PieceColor other, final boolean expected) {
        final Piece piece = new Piece(PieceType.ROOK, other);

        final boolean actual = piece.isSameColor(PieceColor.BLACK);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("기물의 타입이 EMPTY가 아닌지 확인한다.")
    @ParameterizedTest
    @CsvSource({"PAWN, true", "EMPTY, false"})
    void checkPieceTypeIsEmpty(final PieceType type, final boolean expected) {
        final Piece piece = new Piece(type, PieceColor.NONE);

        final boolean actual = piece.isNotEmpty();

        assertThat(actual).isEqualTo(expected);
    }

}

