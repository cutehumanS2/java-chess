package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessStatusTest {

    @DisplayName("기물을 이동하면 턴이 바뀐다.")
    @Test
    void changeTurnWhenMovePiece() {
        final ChessStatus chessStatus = new ChessStatus(PieceColor.WHITE);
        chessStatus.move(new Square(File.e, Rank.TWO), new Square(File.e, Rank.FOUR));

        assertThatThrownBy(() -> chessStatus.move(new Square(File.e, Rank.FOUR), new Square(File.e, Rank.FIVE)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
