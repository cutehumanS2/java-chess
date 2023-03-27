package chess.domain.pieces;

import chess.domain.Direction;
import chess.domain.board.Position;
import chess.domain.pieces.component.Team;
import chess.domain.pieces.component.Type;

import java.util.List;

public abstract class Piece {
    protected static final String INVALID_EMPTY_TEAM = "[ERROR] EmptyPiece 의 팀은 NEUTRALITY 여야 합니다.";
    protected static final String INVALID_NOT_EMPTY_TEAM = "[ERROR] EmptyPiece이 아니면 NEUTRALITY 을 가질 수 없습니다.";

    protected final Team team;
    protected Type type;
    protected List<Direction> directions;

    public Piece(final Team team) {
        this.team = team;
    }

    public Type getType() {
        return type;
    }

    public Team getTeam() {
        return this.team;
    }


    public void checkDirection(Direction direction) {
        if (!this.directions.contains(direction)) {
            throw new IllegalArgumentException("[ERROR] 갈 수 없는 방향입니다.");
        }
    }

    public void checkSameTeam(Piece currentPiece, Piece targetPiece) {
        if (currentPiece.getTeam() == targetPiece.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 도착지에 같은 팀이 존재합니다.");
        }
    }

    public boolean isBlackTeam(){
        return this.team == Team.BLACK;
    }

    public boolean isWhiteTeam(){
        return this.team == Team.WHITE;
    }

    abstract public void validateTeam(final Team team);

    abstract public void checkStep(Position currentPosition, Direction direction, List<Piece> pieces);

    abstract public void checkExistPiece(List<Piece> pieces);
}
