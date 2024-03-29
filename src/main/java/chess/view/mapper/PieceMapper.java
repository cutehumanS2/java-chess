package chess.view.mapper;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;

public enum PieceMapper {

    KING("k"),
    QUEEN("q"),
    ROOK("r"),
    BISHOP("b"),
    KNIGHT("n"),
    PAWN("p"),
    EMPTY("."),
    ;

    private final String name;

    PieceMapper(final String name) {
        this.name = name;
    }

    public static String findNameByTypeAndColor(final PieceType pieceType, final PieceColor pieceColor) {
        if (pieceColor == PieceColor.BLACK) {
            return valueOf(pieceType.name()).name.toUpperCase();
        }
        return valueOf(pieceType.name()).name;
    }
}
