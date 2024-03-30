package chess.domain.game;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.square.Square;

import java.util.Map;

public class ChessStatus {

    private final Board board;
    private PieceColor turn;

    public ChessStatus(final PieceColor turn) {
        this.board = BoardFactory.createBoard();
        this.turn = turn;
    }

    public void move(final Square source, final Square target) {
        board.move(source, target, turn);
        this.turn = turn.next();
    }

    public Map<Square, Piece> getPieces() {
        return board.getPieces();
    }
}
