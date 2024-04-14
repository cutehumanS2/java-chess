package chess.controller;

import chess.domain.game.GameStatus;
import chess.domain.game.GameResult;
import chess.domain.piece.PieceColor;
import chess.domain.room.Room;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.service.ChessGameService;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.outputview.ChessGameOutputView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ChessGameController {

    private static final String MOVE_COMMAND_DELIMITER = " ";
    private static final String FILE_RANK_DELIMITER = "";
    private static final int SOURCE_SQUARE_INDEX = 1;
    private static final int TARGET_SQUARE_INDEX = 2;
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;

    private final InputView inputView;
    private final ChessGameOutputView outputView;
    private final ChessGameService gameService;
    private final Map<CommandType, ChessGameCommand> commands;

    // TODO: 로직 보완 필요 ~ 턴 정보 복구, 보드 정보 중복 출력

    public ChessGameController(final InputView inputView, final ChessGameOutputView outputView,
                               final ChessGameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
        this.commands = new EnumMap<>(CommandType.class);
        commands.put(CommandType.MOVE, (roomId, gameStatus, gameResult, commandInput) -> move(roomId, gameStatus, commandInput));
        commands.put(CommandType.STATUS, (roomId, gameStatus, gameResult, commandInput) -> status(gameResult));
        commands.put(CommandType.END, ((roomId, gameStatus, gameResult, commandInput) -> end(gameResult)));
    }

    public void run(final Room room) {
        outputView.printEnterMessage(room.getId(), room.getName());
        outputView.printCommandMessage();

        final CommandType commandType = requestUntilValid(this::requestStartCommand);
        if (commandType == CommandType.END) {
            return;
        }
        startGame(room.getId());
    }

    private CommandType requestStartCommand() {
        final String commandInput = requestUntilValid(inputView::readCommand);
        final CommandType commandType = requestUntilValid(() -> CommandType.findByValue(commandInput));
        validateIsNotStartAndEnd(commandType);
        return commandType;
    }

    private void validateIsNotStartAndEnd(final CommandType commandType) {
        if (commandType == CommandType.MOVE || commandType == CommandType.STATUS) {
            throw new IllegalArgumentException("게임을 먼저 시작해 주세요.");
        }
    }

    private void startGame(final Long roomId) {
        final GameStatus gameStatus = new GameStatus(PieceColor.WHITE);
        recoveryGame(gameStatus, roomId);
        final GameResult gameResult = new GameResult(gameStatus.getPieces());
        outputView.printCurrentTurn(gameStatus.getTurn());
        outputView.printBoard(gameStatus.getPieces());
        playGameUntilEnd(roomId, gameStatus, gameResult);
    }

    private void recoveryGame(final GameStatus gameStatus, final Long roomId) {
        List<Movement> movements = gameService.loadMovements(roomId);
        movements.forEach(movement -> gameStatus.move(movement.source(), movement.target()));
    }

    private void playGameUntilEnd(final Long roomId, final GameStatus gameStatus, final GameResult gameResult) {
        CommandType command = CommandType.NONE;
        while (command != CommandType.END) {
            command = requestUntilValid(() -> playGame(roomId, gameStatus, gameResult));
            command = isGameOver(gameResult, command);
        }
    }

    private CommandType playGame(final Long roomId, final GameStatus gameStatus, final GameResult gameResult) {
        outputView.printCurrentTurn(gameStatus.getTurn());
        outputView.printBoard(gameStatus.getPieces());
        String commandInput = requestUntilValid(inputView::readCommand);
        CommandType commandType = requestCommand(commandInput);
        commands.get(commandType).execute(roomId, gameStatus, gameResult, commandInput);
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

    private void move(final Long roomId, final GameStatus gameStatus, final String commandInput) {
        final List<String> splitCommand = List.of(commandInput.split(MOVE_COMMAND_DELIMITER));
        final Square source = createSquare(splitCommand.get(SOURCE_SQUARE_INDEX));
        final Square target = createSquare(splitCommand.get(TARGET_SQUARE_INDEX));
        gameStatus.move(source, target);

        gameService.saveMovement(roomId, source, target);
    }

    private Square createSquare(final String commandInput) {
        final List<String> commandToken = List.of(commandInput.split(FILE_RANK_DELIMITER));
        final File file = File.findByValue(commandToken.get(FILE_INDEX));
        final Rank rank = Rank.findByValue(commandToken.get(RANK_INDEX));
        return new Square(file, rank);
    }

    private void end(final GameResult gameResult) {
        outputView.printFinalGameResult(gameResult.findWinnerTeam(), gameResult.calculateTotalScore(PieceColor.WHITE),
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
        outputView.printGameResult(gameResult.findWinnerTeam(), gameResult.calculateTotalScore(PieceColor.WHITE),
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
            outputView.printErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
