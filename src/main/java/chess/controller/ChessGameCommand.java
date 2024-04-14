package chess.controller;

import chess.domain.game.GameResult;
import chess.domain.game.GameStatus;

@FunctionalInterface
public interface ChessGameCommand {

    void execute(final Long roomId, final GameStatus gameStatus, final GameResult gameResult, final String commandInput);
}
