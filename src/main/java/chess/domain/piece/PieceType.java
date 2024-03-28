package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.piece.strategy.BishopMoveStrategy;
import chess.domain.piece.strategy.EmptyStrategy;
import chess.domain.piece.strategy.KingMoveStrategy;
import chess.domain.piece.strategy.KnightStrategy;
import chess.domain.piece.strategy.MoveStrategy;
import chess.domain.piece.strategy.PawnMoveStrategy;
import chess.domain.piece.strategy.QueenMoveStrategy;
import chess.domain.piece.strategy.RookMoveStrategy;
import chess.domain.square.Square;

public enum PieceType {

    KING(new KingMoveStrategy(), 0),
    QUEEN(new QueenMoveStrategy(), 9),
    ROOK(new RookMoveStrategy(), 5),
    BISHOP(new BishopMoveStrategy(), 3),
    KNIGHT(new KnightStrategy(), 2.5),
    PAWN(new PawnMoveStrategy(), 1),
    EMPTY(new EmptyStrategy(), 0),
    ;

    private final MoveStrategy moveStrategy;
    private final double score;

    PieceType(MoveStrategy moveStrategy, double score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public boolean canMove(final Board board, final Square source, final Square target) {
        return moveStrategy.canMove(board, source, target);
    }

    public double getScore() {
        return score;
    }
}
