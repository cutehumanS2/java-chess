package chess.view.mapper;

import chess.domain.piece.PieceColor;

import java.util.Arrays;

public enum ColorMapper {

    BLACK(PieceColor.BLACK, "검은색"),
    WHITE(PieceColor.WHITE, "흰색"),
    NONE(PieceColor.NONE, "무승부"),
    ;

    private final PieceColor color;
    private final String name;

    ColorMapper(PieceColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public static String findNameByColor(final PieceColor pieceColor) {
        return Arrays.stream(values())
                .filter(colorMapper -> colorMapper.color == pieceColor)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("매칭되는 이름이 없습니다."))
                .name;
    }

    public static PieceColor findColorByName(final String name) {
        return Arrays.stream(values())
                .filter(colorMapper -> colorMapper.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("매칭되는 색상이 없습니다."))
                .color;
    }
}
