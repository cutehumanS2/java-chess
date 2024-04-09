package chess.domain.piece;

import java.util.Arrays;
import java.util.List;

public enum PieceColor {

    BLACK,
    WHITE,
    NONE,
    ;

    public static List<PieceColor> valuesWithoutNone() {
        return Arrays.stream(PieceColor.values())
                .filter(color -> color != PieceColor.NONE)
                .toList();
    }

    public PieceColor next() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
