package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.square.Square;

public interface MoveStrategy {

    boolean canMove(final Board board, final Square source, final Square target);
}
