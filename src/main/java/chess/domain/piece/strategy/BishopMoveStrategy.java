package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class BishopMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Board board, final Square source, final Square target) {
        return source.isDiagonal(target);
    }
}
