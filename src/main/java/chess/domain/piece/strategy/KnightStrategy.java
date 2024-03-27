package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class KnightStrategy implements MoveStrategy {

    private static final int ONE_STEP = 1;
    private static final int TWO_STEP = 2;

    @Override
    public boolean canMove(final Board board, final Square source, final Square target) {
        if (isFileOneStep(source, target) && isRankTwoStep(source, target)) {
            return true;
        }
        return isFileTwoStep(source, target) && isRankOneStep(source, target);
    }

    private boolean isFileOneStep(final Square source, final Square target) {
        return source.calculateFileDistance(target) == ONE_STEP;
    }

    private boolean isFileTwoStep(final Square source, final Square target) {
        return source.calculateFileDistance(target) == TWO_STEP;
    }

    private boolean isRankOneStep(final Square source, final Square target) {
        return source.calculateRankDistance(target) == ONE_STEP;
    }

    private boolean isRankTwoStep(final Square source, final Square target) {
        return source.calculateRankDistance(target) == TWO_STEP;
    }
}
