package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardFactory {

    private static final List<Function<PieceColor, Piece>> PIECES_ARRANGEMENT = List.of(
            (pieceColor) -> Piece.of(PieceType.ROOK, pieceColor),
            (pieceColor) -> Piece.of(PieceType.KNIGHT, pieceColor),
            (pieceColor) -> Piece.of(PieceType.BISHOP, pieceColor),
            (pieceColor) -> Piece.of(PieceType.QUEEN, pieceColor),
            (pieceColor) -> Piece.of(PieceType.KING, pieceColor),
            (pieceColor) -> Piece.of(PieceType.BISHOP, pieceColor),
            (pieceColor) -> Piece.of(PieceType.KNIGHT, pieceColor),
            (pieceColor) -> Piece.of(PieceType.ROOK, pieceColor)
    );

    private BoardFactory() {
    }

    public static Board createBoard() {
        final Map<Square, Piece> board = new HashMap<>();
        board.putAll(createPiecesWithoutPawn(Rank.EIGHT, PieceColor.BLACK));
        board.putAll(createPieces(Rank.SEVEN, PieceType.PAWN, PieceColor.BLACK));
        board.putAll(createPieces(Rank.SIX, PieceType.EMPTY, PieceColor.NONE));
        board.putAll(createPieces(Rank.FIVE, PieceType.EMPTY, PieceColor.NONE));
        board.putAll(createPieces(Rank.FOUR, PieceType.EMPTY, PieceColor.NONE));
        board.putAll(createPieces(Rank.THREE, PieceType.EMPTY, PieceColor.NONE));
        board.putAll(createPieces(Rank.TWO, PieceType.PAWN, PieceColor.WHITE));
        board.putAll(createPiecesWithoutPawn(Rank.ONE, PieceColor.WHITE));

        return new Board(board);
    }

    private static Map<Square, Piece> createPiecesWithoutPawn(final Rank rank, final PieceColor pieceColor) {
        return IntStream.range(0, PIECES_ARRANGEMENT.size())
                .boxed()
                .collect(Collectors.toMap(
                        index -> new Square(File.values()[index], rank),
                        index -> PIECES_ARRANGEMENT.get(index).apply(pieceColor)
                ));
    }

    private static Map<Square, Piece> createPieces(final Rank rank, final PieceType pieceType, final PieceColor pieceColor) {
        return Arrays.stream(File.values())
                .collect(Collectors.toMap(
                        file -> new Square(file, rank),
                        file -> Piece.of(pieceType, pieceColor)
                ));
    }
}
