package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class QueenMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Board board, final Square source, final Square target) {
        return isStraight(source, target) || source.isDiagonal(target);
    }

    private boolean isStraight(final Square source, final Square target) {
        return source.isVertical(target) || source.isHorizontal(target);
    }
}
