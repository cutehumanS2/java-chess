package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class Piece {

    private final PieceType type;
    private final PieceColor color;

    public Piece(final PieceType type, final PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public boolean canMove(final Board board, final Square source, final Square target) {
        return type.canMove(board, source, target);
    }

    public boolean isSameColor(final PieceColor other) {
        return color == other;
    }

    public boolean isNotEmpty() {
        return type != PieceType.EMPTY;
    }

    public boolean isSameType(final PieceType other) {
        return type == other;
    }

    public double getScore() {
        return type.getScore();
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }
}
