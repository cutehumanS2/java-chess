package chess.controller;

import chess.domain.game.GameStatus;
import chess.domain.game.GameResult;
import chess.domain.piece.PieceColor;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.service.ChessGameService;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ChessGame {

    private static final String MOVE_COMMAND_DELIMITER = " ";
    private static final String FILE_RANK_DELIMITER = "";
    private static final int SOURCE_SQUARE_INDEX = 1;
    private static final int TARGET_SQUARE_INDEX = 2;
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;

    private final ChessGameService gameService;
    private final Map<CommandType, Command> commands;

    public ChessGame(final ChessGameService gameService) {
        this.gameService = gameService;
        this.commands = new EnumMap<>(CommandType.class);
        commands.put(CommandType.MOVE, (gameStatus, gameResult, commandInput) -> move(gameStatus, commandInput));
        commands.put(CommandType.STATUS, (gameStatus, gameResult, commandInput) -> status(gameResult));
        commands.put(CommandType.END, ((gameStatus, gameResult, commandInput) -> end(gameResult)));
    }

    public void run() {
        OutputView.printStartMessage();

        final CommandType commandType = requestUntilValid(this::requestStartCommand);
        if (commandType == CommandType.END) {
            return;
        }
        startGame();
    }

    private CommandType requestStartCommand() {
        final String commandInput = requestUntilValid(InputView::readCommand);
        final CommandType commandType = requestUntilValid(() -> CommandType.findByValue(commandInput));
        validateIsNotStartAndEnd(commandType);
        return commandType;
    }

    private void validateIsNotStartAndEnd(final CommandType commandType) {
        if (commandType == CommandType.MOVE || commandType == CommandType.STATUS) {
            throw new IllegalArgumentException("게임을 먼저 시작해 주세요.");
        }
    }

    private void startGame() {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);
        recoveryGame(gameStatus);
        final GameResult gameResult = new GameResult(gameStatus.getPieces());
        OutputView.printCurrentTurn(gameStatus.getTurn());
        OutputView.printBoard(gameStatus.getPieces());
        playGameUntilEnd(gameStatus, gameResult);
    }

    private void recoveryGame(final GameStatus gameStatus) {
        List<Movement> movements = gameService.loadMovements(1L);
        movements.forEach(movement -> gameStatus.move(movement.source(), movement.target()));
    }

    private void playGameUntilEnd(final GameStatus gameStatus, final GameResult gameResult) {
        CommandType command = CommandType.NONE;
        while (command != CommandType.END) {
            command = requestUntilValid(() -> playGame(gameStatus, gameResult));
            command = isGameOver(gameResult, command);
            OutputView.printCurrentTurn(gameStatus.getTurn());
            OutputView.printBoard(gameStatus.getPieces());
        }
    }

    private CommandType playGame(final GameStatus gameStatus, final GameResult gameResult) {
        String commandInput = requestUntilValid(InputView::readCommand);
        CommandType commandType = requestCommand(commandInput);
        commands.get(commandType).execute(gameStatus, gameResult, commandInput);
        return commandType;
    }

    private CommandType requestCommand(final String input) {
        final CommandType commandType = CommandType.findByValue(input);
        validateIsNotStart(commandType);
        return commandType;
    }

    private void validateIsNotStart(final CommandType commandType) {
        if (commandType == CommandType.START) {
            throw new IllegalArgumentException("이미 게임이 시작되었습니다.");
        }
    }

    private void move(final GameStatus gameStatus, final String commandInput) {
        final List<String> splitCommand = List.of(commandInput.split(MOVE_COMMAND_DELIMITER));
        final Square source = createSquare(splitCommand.get(SOURCE_SQUARE_INDEX));
        final Square target = createSquare(splitCommand.get(TARGET_SQUARE_INDEX));
        gameStatus.move(source, target);

        final Long gameId = gameService.upsertCurrentTurn(gameStatus);
        gameService.saveMovement(gameId, source, target);
    }

    private Square createSquare(final String commandInput) {
        final List<String> commandToken = List.of(commandInput.split(FILE_RANK_DELIMITER));
        final File file = File.findByValue(commandToken.get(FILE_INDEX));
        final Rank rank = Rank.findByValue(commandToken.get(RANK_INDEX));
        return new Square(file, rank);
    }

    private void end(final GameResult gameResult) {
        OutputView.printFinalGameResult(gameResult.findWinnerTeam(), gameResult.calculateTotalScore(PieceColor.WHITE),
                gameResult.calculateTotalScore(PieceColor.BLACK));
    }

    private CommandType isGameOver(final GameResult gameResult, final CommandType command) {
        if (gameResult.isGameOver()) {
            // TODO: 킹 잡혔음 출력
            return CommandType.END;
        }
        return command;
    }

    private void status(final GameResult gameResult) {
        OutputView.printGameResult(gameResult.findWinnerTeam(), gameResult.calculateTotalScore(PieceColor.WHITE),
                gameResult.calculateTotalScore(PieceColor.BLACK));
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
