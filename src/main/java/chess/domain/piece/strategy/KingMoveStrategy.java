package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class KingMoveStrategy implements MoveStrategy {

    private static final int STEP_LIMIT = 1;

    @Override
    public boolean canMove(final Board board, final Square source, final Square target) {
        return (isStraight(source, target) || source.isDiagonal(target)) && isWithinOneStep(source, target);
    }

    private boolean isStraight(final Square source, final Square target) {
        return source.isVertical(target) || source.isHorizontal(target);
    }

    private boolean isWithinOneStep(final Square source, final Square target) {
        return source.calculateFileDistance(target) <= STEP_LIMIT
                && source.calculateRankDistance(target) <= STEP_LIMIT;
    }
}
