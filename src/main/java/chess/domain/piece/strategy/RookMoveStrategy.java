package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class RookMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Board board, final Square source, final Square target) {
        return source.isVertical(target) || source.isHorizontal(target);
    }
}
