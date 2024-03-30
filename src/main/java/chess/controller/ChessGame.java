package chess.controller;

import chess.domain.game.ChessStatus;
import chess.domain.game.GameResult;
import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ChessGame {

    private static final String MOVE_COMMAND_DELIMITER = " ";
    private static final String FILE_RANK_DELIMITER = "";
    private static final int SOURCE_SQUARE_INDEX = 1;
    private static final int TARGET_SQUARE_INDEX = 2;
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;

    public void run() {
        OutputView.printStartMessage();

        final Command command = requestUntilValid(this::requestStartCommand);
        if (command == Command.END) {
            return;
        }
        startGame();
    }

    private Command requestStartCommand() {
        final String commandInput = requestUntilValid(InputView::readCommand);
        final Command command = requestUntilValid(() -> Command.findByValue(commandInput));
        validateIsNotStartAndEnd(command);
        return command;
    }

    private void validateIsNotStartAndEnd(final Command command) {
        if (command == Command.MOVE || command == Command.STATUS) {
            throw new IllegalArgumentException("게임을 먼저 시작해 주세요.");
        }
    }

    private void startGame() {
        final ChessStatus game = new ChessStatus(PieceColor.WHITE);
        final GameResult gameResult = new GameResult(game.getPieces());
        OutputView.printBoard(game.getPieces());
        playGameUntilEnd(game, gameResult);
    }

    private void playGameUntilEnd(final ChessStatus game, final GameResult gameResult) {
        while (requestUntilValid(() -> playGame(game, gameResult) != Command.END)) {
            OutputView.printBoard(game.getPieces());
        }

        OutputView.printFinalGameResult(gameResult.findWinnerTeam(), gameResult.calculateTotalScore(PieceColor.WHITE),
                gameResult.calculateTotalScore(PieceColor.BLACK));
    }

    private Command playGame(final ChessStatus game, final GameResult gameResult) {
        String commandInput = requestUntilValid(InputView::readCommand);
        Command command = requestCommand(commandInput);

        if (command == Command.STATUS) {
            OutputView.printGameResult(gameResult.findWinnerTeam(), gameResult.calculateTotalScore(PieceColor.WHITE),
                    gameResult.calculateTotalScore(PieceColor.BLACK));
        }
        if (command == Command.MOVE) {
            command = move(game, gameResult, commandInput);
        }
        return command;
    }

    private Command requestCommand(final String input) {
        final Command command = Command.findByValue(input);
        validateIsNotStart(command);
        return command;
    }

    private void validateIsNotStart(final Command command) {
        if (command == Command.START) {
            throw new IllegalArgumentException("이미 게임이 시작되었습니다.");
        }
    }

    private Command move(final ChessStatus game, final GameResult gameResult, final String commandInput) {
        final List<String> splitCommand = List.of(commandInput.split(MOVE_COMMAND_DELIMITER));
        final Square source = createSquare(splitCommand.get(SOURCE_SQUARE_INDEX));
        final Square target = createSquare(splitCommand.get(TARGET_SQUARE_INDEX));
        game.move(source, target);

        if (gameResult.isGameOver()) {
            return Command.END;
        }
        return Command.MOVE;
    }

    private Square createSquare(final String commandInput) {
        final List<String> commandToken = List.of(commandInput.split(FILE_RANK_DELIMITER));
        final File file = File.findByValue(commandToken.get(FILE_INDEX));
        final Rank rank = Rank.findByValue(commandToken.get(RANK_INDEX));
        return new Square(file, rank);
    }

    private <T> T requestUntilValid(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryGet(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryGet(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
