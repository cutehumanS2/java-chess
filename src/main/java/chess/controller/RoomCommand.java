package chess.controller;

@FunctionalInterface
public interface RoomCommand {

    void execute(final String commandInput);
}
