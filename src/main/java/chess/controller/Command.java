package chess.controller;

import chess.domain.game.GameResult;
import chess.domain.game.GameStatus;

@FunctionalInterface
public interface Command {

    void execute(final GameStatus gameStatus, final GameResult gameResult, final String commandInput);
}
