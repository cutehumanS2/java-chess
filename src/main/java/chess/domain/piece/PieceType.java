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

    KING(new KingMoveStrategy()),
    QUEEN(new QueenMoveStrategy()),
    ROOK(new RookMoveStrategy()),
    BISHOP(new BishopMoveStrategy()),
    KNIGHT(new KnightStrategy()),
    PAWN(new PawnMoveStrategy()),
    EMPTY(new EmptyStrategy()),
    ;

    private final MoveStrategy moveStrategy;

    PieceType(final MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public boolean canMove(final Board board, final Square source, final Square target) {
        return moveStrategy.canMove(board, source, target);
    }
}
