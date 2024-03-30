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
            return findNameByType(pieceType).toUpperCase();
        }
        return findNameByType(pieceType);
    }

    private static String findNameByType(final PieceType pieceType) {
        return valueOf(pieceType.name()).name;
    }
}
