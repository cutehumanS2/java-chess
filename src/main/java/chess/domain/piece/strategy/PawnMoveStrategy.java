package chess.domain.piece.strategy;

import chess.domain.board.Board;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.square.Square;

public class PawnMoveStrategy implements MoveStrategy {

    private static final int FIRST_STEP_LIMIT = 2;
    private static final int NORMAL_STEP_LIMIT = 1;
    private static final int ATTACK_STEP_LIMIT = 1;

    @Override
    public boolean canMove(final Board board, final Square source, final Square target) {
        return isOnlyForward(board, source, target) || isAttack(board, source, target);
    }

    private boolean isOnlyForward(final Board board, final Square source, final Square target) {
        if (isBackward(board, source, target) || source.isDiagonal(target)) {
            return false;
        }
        if (isFirstMove(source)) {
            return isWithinFirstStepLimit(source, target) && source.isVertical(target) && isEmptySquare(board, target);
        }
        return isNormalStepLimit(source, target) && source.isVertical(target) && isEmptySquare(board, target);
    }

    private boolean isBackward(final Board board, final Square source, final Square target) {
        Piece sourcePiece = board.findPieceBySquare(source);

        if (sourcePiece.isSameColor(PieceColor.BLACK)) {
            return source.calculateRankDirection(target) < 0;
        }
        return source.calculateRankDirection(target) > 0;
    }

    private boolean isFirstMove(final Square source) {
        return source.isWhitePawnInitialRank() || source.isBlackPawnInitialRank();
    }

    private boolean isWithinFirstStepLimit(final Square source, final Square target) {
        return source.calculateRankDistance(target) <= FIRST_STEP_LIMIT;
    }

    private boolean isNormalStepLimit(final Square source, final Square target) {
        return source.calculateRankDistance(target) == NORMAL_STEP_LIMIT;
    }

    private boolean isEmptySquare(final Board board, final Square target) {
        final Piece targetPiece = board.findPieceBySquare(target);
        return !targetPiece.isNotEmpty();
    }

    private boolean isAttack(final Board board, final Square source, final Square target) {
        if (canAttack(board, target)) {
            return source.isDiagonal(target) && isAttackStep(source, target);
        }
        return false;
    }

    private boolean canAttack(final Board board, final Square target) {
        return board.findPieceBySquare(target).isNotEmpty();
    }

    private boolean isAttackStep(final Square source, final Square target) {
        return source.calculateFileDistance(target) == ATTACK_STEP_LIMIT;
    }
}
