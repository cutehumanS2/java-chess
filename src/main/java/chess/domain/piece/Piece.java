package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.square.Square;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Piece {

    private static final Map<String, Piece> POOL;

    static {
        POOL = PieceType.valuesWithoutEmpty()
                .stream()
                .flatMap(type -> PieceColor.valuesWithoutNone()
                        .stream()
                        .map(color -> new Piece(type, color)))
                .collect(Collectors.toMap(it -> toKey(it.type, it.color), Function.identity()));
        POOL.put(toKey(PieceType.EMPTY, PieceColor.NONE), new Piece(PieceType.EMPTY, PieceColor.NONE));
    }

    private final PieceType type;
    private final PieceColor color;

    private Piece(final PieceType type, final PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public static Piece of(final PieceType type, final PieceColor color) {
        return POOL.get(toKey(type, color));
    }

    private static String toKey(final PieceType type, final PieceColor color) {
        return color.name() + type.name();
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
