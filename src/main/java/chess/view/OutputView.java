package chess.view;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.view.mapper.ColorMapper;
import chess.view.mapper.PieceMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final int BOARD_SIZE = 8;
    private static final String TITLE_START = "> 체스 게임을 시작합니다.%n" +
            "> 게임 시작 : start%n" +
            "> 게임 종료 : end%n" +
            "> 게임 이동 : move source위치 target위치 - 예. move b2 b3%n" +
            "> 게임 결과 : status%n";

    public static void printStartMessage() {
        System.out.printf(TITLE_START);
    }

    public static void printBoard(final Map<Square, Piece> board) {
        for (int i = BOARD_SIZE; i > 0; i--) {
            System.out.println(String.join("", createPiecesLine(board, Rank.findByIndex(i))));
        }
        System.out.println();
    }

    private static List<String> createPiecesLine(final Map<Square, Piece> board, final Rank rank) {
        return Arrays.stream(File.values())
                .map(file -> board.get(new Square(file, rank)))
                .map(piece -> PieceMapper.findNameByTypeAndColor(piece.getType(), piece.getColor()))
                .toList();
    }

    public static void printGameResult(
            final PieceColor winnerTeamColor, final double whiteTeamScore, final double blackTeamScore) {
        printScoreByColor(PieceColor.WHITE, whiteTeamScore);
        printScoreByColor(PieceColor.BLACK, blackTeamScore);
        printWinnerTeam(winnerTeamColor);
    }

    private static void printScoreByColor(final PieceColor teamColor, final double teamScore) {
        System.out.println("> " + ColorMapper.findNameByColor(teamColor) + " 진영 점수 : " + teamScore);
    }

    private static void printWinnerTeam(final PieceColor teamColor) {
        System.out.println("> 승리 진영 : " + ColorMapper.findNameByColor(teamColor) + System.lineSeparator());
    }

    public static void printFinalGameResult(
            final PieceColor winnerTeamColor, final double whiteTeamScore, final double blackTeamScore) {
        printGameOverMessage();
        printGameResult(winnerTeamColor, whiteTeamScore, blackTeamScore);
        printFinalWinnerTeam(winnerTeamColor);
    }

    private static void printGameOverMessage() {
        System.out.println("> 킹이 잡혀 게임을 종료합니다.");
    }

    private static void printFinalWinnerTeam(final PieceColor teamColor) {
        System.out.println("> 최종 승리 진영은 " + ColorMapper.findNameByColor(teamColor) + " 진영입니다.");
    }

    public static void printErrorMessage(final String message) {
        System.out.println("> " + message);
    }
}
