package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.square.File;
import chess.domain.square.Square;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private static final String ERROR_SAME_SQUARE = "기물의 출발지와 목적지는 달라야 합니다.";
    private static final String ERROR_NOT_EXIST_PIECE = "해당 위치에 기물이 존재하지 않습니다.";
    private static final String ERROR_MOVE_NOT_AVAILABLE = "해당 위치로 기물을 이동할 수 없습니다.";
    private static final String ERROR_IS_NOT_TURN = "본인 팀의 기물만 이동할 수 있습니다.";

    private final Map<Square, Piece> pieces;

    public Board(final Map<Square, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final Square source, final Square target, final PieceColor turn) {
        validateIsSameSquare(source, target);
        validateIsNonExistentPiece(source);
        validateIsNotTurn(source, turn);
        validateCannotMove(source, target);
        validateNotExistObstacleOnPath(source, target);

        replacePieceSquare(source, target);
    }

    private void validateIsSameSquare(final Square source, final Square target) {
        if (source.equals(target)) {
            throw new IllegalArgumentException(ERROR_SAME_SQUARE);
        }
    }

    private void validateIsNonExistentPiece(final Square square) {
        if (!pieces.containsKey(square)) {
            throw new IllegalArgumentException(ERROR_NOT_EXIST_PIECE);
        }
    }

    public Piece findPieceBySquare(final Square square) {
        return pieces.getOrDefault(square, new Piece(PieceType.EMPTY, PieceColor.NONE));
    }

    private void validateIsNotTurn(final Square source, final PieceColor turn) {
        final Piece sourcePiece = findPieceBySquare(source);
        if (!sourcePiece.isSameColor(turn)) {
            throw new IllegalArgumentException(ERROR_IS_NOT_TURN);
        }
    }

    private void validateCannotMove(final Square source, final Square target) {
        final Piece sourcePiece = findPieceBySquare(source);
        if (!sourcePiece.canMove(this, source, target)) {
            throw new IllegalArgumentException(ERROR_MOVE_NOT_AVAILABLE);
        }
    }

    private void validateNotExistObstacleOnPath(final Square source, final Square target) {
        final List<Square> path = source.findPath(target);
        for (Square square : path) {
            checkIsNotEmpty(square);
        }
    }

    private void checkIsNotEmpty(final Square square) {
        if (findPieceBySquare(square).isNotEmpty()) {
            throw new IllegalArgumentException(ERROR_MOVE_NOT_AVAILABLE);
        }
    }

    private void replacePieceSquare(final Square source, final Square target) {
        final Piece sourcePiece = findPieceBySquare(source);
        pieces.replace(source, new Piece(PieceType.EMPTY, PieceColor.NONE));
        pieces.replace(target, sourcePiece);
    }

    public double calculateTotalScore(final PieceColor color) {
        return calculateScoreWithoutPawn(color) + calculatePawnScore(color);
    }

    public double calculateScoreWithoutPawn(final PieceColor color) {
        return pieces.values().stream()
                .filter(piece -> !piece.isSameType(PieceType.PAWN))
                .filter(piece -> piece.isSameColor(color))
                .mapToDouble(piece -> piece.getType().getScore())
                .sum();
    }

    public double calculatePawnScore(final PieceColor pawnColor) {
        Map<File, List<Piece>> pawnsByFile = findPawnsByFile(pawnColor);
        return pawnsByFile.values().stream()
                .mapToDouble((pawns) -> calculatePawnScoreByCount(pawns.size(), getPawn(pawns)))
                .sum();
    }

    private Map<File, List<Piece>> findPawnsByFile(final PieceColor pawnColor) {
        return pieces.entrySet().stream()
                .filter(piece -> piece.getValue().isSameType(PieceType.PAWN))
                .filter(piece -> piece.getValue().isSameColor(pawnColor))
                .collect(Collectors.groupingBy(piece -> piece.getKey().file(),
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    private double calculatePawnScoreByCount(final long count, final Piece pawn) {
        double pawnBasicScore = pawn.getScore();
        if (count > 1) {
            return (pawnBasicScore * count) / 2;
        }
        return pawnBasicScore * count;
    }

    private Piece getPawn(List<Piece> pawns) {
        return pawns.get(0);
    }

    public PieceColor findFinalWinnerTeam() {
        return pieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("현재 보드에 남아 있는 킹이 없습니다."))
                .getColor();
    }

    public Map<Square, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
