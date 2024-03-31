package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @DisplayName("기물을 이동하면 턴이 바뀐다.")
    @Test
    void changeTurnWhenMovePiece() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);

        gameStatus.move(new Square(File.e, Rank.TWO), new Square(File.e, Rank.FOUR));

        assertThat(gameStatus.getTurn()).isEqualTo(PieceColor.BLACK);
    }
}
