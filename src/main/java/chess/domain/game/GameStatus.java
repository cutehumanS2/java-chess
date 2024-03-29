package chess.domain.game;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.square.File;
import chess.domain.square.Square;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameStatus {

    private static final int INITIAL_KING_COUNT = 2;
    private static final double PENALTY_RATE = 0.5;
    private static final String ERROR_NOT_EXIST_KING = "현재 보드에 남아 있는 킹이 없습니다.";

    private final Map<Square, Piece> pieces;

    public GameStatus(final Map<Square, Piece> pieces) {
        this.pieces = pieces;
    }

    public double calculateTotalScore(final PieceColor color) {
        return calculateScoreWithoutPawn(color) + calculatePawnScore(color);
    }

    private double calculateScoreWithoutPawn(final PieceColor color) {
        return pieces.values().stream()
                .filter(piece -> !piece.isSameType(PieceType.PAWN))
                .filter(piece -> piece.isSameColor(color))
                .mapToDouble(piece -> piece.getType().getScore())
                .sum();
    }

    private double calculatePawnScore(final PieceColor pawnColor) {
        final Map<File, List<Piece>> pawnsByFile = findPawnsByFile(pawnColor);
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
        final double pawnBasicScore = pawn.getScore();
        if (count > 1) {
            return (pawnBasicScore * count) * PENALTY_RATE;
        }
        return pawnBasicScore * count;
    }

    private Piece getPawn(List<Piece> pawns) {
        return pawns.get(0);
    }

    public PieceColor findWinnerTeam() {
        if (isGameOver()) {
            return findFinalWinnerTeam();
        }
        return findCurrentWinnerTeam();
    }

    public boolean isGameOver() {
        final long kingCount = countKingOnBoard();
        return kingCount < INITIAL_KING_COUNT;
    }

    private long countKingOnBoard() {
        return pieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .count();
    }

    private PieceColor findFinalWinnerTeam() {
        return pieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(ERROR_NOT_EXIST_KING))
                .getColor();
    }

    private PieceColor findCurrentWinnerTeam() {
        final double blackTeamScore = calculateTotalScore(PieceColor.BLACK);
        final double whiteTeamScore = calculateTotalScore(PieceColor.WHITE);
        if (blackTeamScore > whiteTeamScore) {
            return PieceColor.BLACK;
        }
        if (blackTeamScore < whiteTeamScore) {
            return PieceColor.WHITE;
        }
        return PieceColor.NONE;
    }
}
