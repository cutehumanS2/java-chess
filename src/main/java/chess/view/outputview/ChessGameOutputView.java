package chess.view.outputview;

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

public class ChessGameOutputView {

    private static final int BOARD_SIZE = 8;

    public void printEnterMessage(final Long roomId, final String name) {
        System.out.printf("> %d번 [%s] 방에 입장하였습니다.%n", roomId, name);
    }

    public void printCommandMessage() {
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println("> 게임 결과 : status");
    }

    public void printBoard(final Map<Square, Piece> board) {
        for (int i = BOARD_SIZE; i > 0; i--) {
            System.out.println(String.join("", createPiecesLine(board, Rank.findByIndex(i))));
        }
        System.out.println();
    }

    private List<String> createPiecesLine(final Map<Square, Piece> board, final Rank rank) {
        return Arrays.stream(File.values())
                .map(file -> board.get(new Square(file, rank)))
                .map(piece -> PieceMapper.findNameByTypeAndColor(piece.getType(), piece.getColor()))
                .toList();
    }

    public void printCurrentTurn(final PieceColor turn) {
        System.out.printf("%n> " + ColorMapper.findNameByColor(turn) + " 진영의 턴입니다.%n");
    }

    public void printGameResult(
            final PieceColor winnerTeamColor, final double whiteTeamScore, final double blackTeamScore) {
        printScoreByColor(PieceColor.WHITE, whiteTeamScore);
        printScoreByColor(PieceColor.BLACK, blackTeamScore);
        printWinnerTeam(winnerTeamColor);
    }

    private void printScoreByColor(final PieceColor teamColor, final double teamScore) {
        System.out.println("> " + ColorMapper.findNameByColor(teamColor) + " 진영 점수 : " + teamScore);
    }

    private void printWinnerTeam(final PieceColor teamColor) {
        System.out.println("> 승리 진영 : " + ColorMapper.findNameByColor(teamColor));
    }

    public void printFinalGameResult(
            final PieceColor winnerTeamColor, final double whiteTeamScore, final double blackTeamScore) {
        printGameResult(winnerTeamColor, whiteTeamScore, blackTeamScore);
        printFinalWinnerTeam(winnerTeamColor);
    }

    private void printFinalWinnerTeam(final PieceColor teamColor) {
        System.out.printf("> 최종 승리 진영은 %s 입니다.%n%n", ColorMapper.findNameByColor(teamColor));
    }

    public void printErrorMessage(final String message) {
        System.out.println("> " + message);
    }
}
