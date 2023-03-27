package chess.domain.state;

import chess.domain.board.Board;
import chess.domain.board.Position;
import chess.view.Command;

public class BlackTurn implements State {

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public State progress(Command command, Board board) {
        if (command.isStart()) {
            throw new IllegalArgumentException("게임이 진행중 입니다.");
        }
        if (command.isMove()) {
            checkIsBlack(board, command.getCurrentPosition());
            board.movePiece(command.getCurrentPosition(), command.getTargetPosition());
            return new WhiteTurn();
        }
        return new End();
    }

    private void checkIsBlack(Board board, Position position) {
        if (!board.getBoard().get(position).isBlackTeam()) {
            throw new IllegalArgumentException("블랙의 차례입니다.");
        }
    }

}
