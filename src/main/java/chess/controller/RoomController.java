package chess.controller;

import chess.domain.room.Room;
import chess.service.RoomService;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.outputview.RoomOutputView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class RoomController {

    private final InputView inputView;
    private final RoomOutputView outputView;
    private final RoomService roomService;
    private final ChessGameController chessGameController;
    private final Map<CommandType, RoomCommand> commands;

    public RoomController(final InputView inputView, final RoomOutputView outputView,
                          final RoomService roomService, final ChessGameController chessGameController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.roomService = roomService;
        this.chessGameController = chessGameController;
        this.commands = new EnumMap<>(CommandType.class);
        commands.put(CommandType.SHOW, (commandInput) -> showRooms());
        commands.put(CommandType.ENTER, this::enterRooms);
        commands.put(CommandType.CREATE, this::createRoom);
        commands.put(CommandType.END, ((commandInput) -> {
        }));
    }

    public void run() {
        outputView.printStartMessage();

        CommandType command = CommandType.NONE;
        while (command != CommandType.END) {
            command = requestUntilValid(this::requestCommand);
        }
    }

    private CommandType requestCommand() {
        outputView.printCommandMessage();
        final String commandInput = requestUntilValid(inputView::readCommand);
        final CommandType command = CommandType.findByValue(commandInput);
        validateCommand(command);
        commands.get(command).execute(commandInput);
        return command;
    }

    private void validateCommand(final CommandType commandType) {
        if (!commands.containsKey(commandType)) {
            throw new IllegalArgumentException("현재 입력 불가능한 명령어입니다.");
        }
    }

    private void showRooms() {
        final List<Room> rooms = roomService.findAllRooms();
        outputView.printRooms(rooms);
    }

    private void enterRooms(final String commandInput) {
        final List<String> splitCommand = List.of(commandInput.split(" "));
        final Long roomId = Long.parseLong(splitCommand.get(1));
        Room room = roomService.findRoomById(roomId).get();
        chessGameController.run(room);
    }

    private void createRoom(final String commandInput) {
        final List<String> splitCommand = List.of(commandInput.split(" "));
        final String name = splitCommand.get(1);
        final Long roomId = roomService.createRoom(name);
        outputView.printSaveRoomMessage(roomId, name);
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
