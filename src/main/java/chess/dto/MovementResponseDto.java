package chess.dto;

import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;

import java.util.List;

public record MovementResponseDto(Long id, Long gameId, String source, String target) {

    public static Movement toEntity(final MovementResponseDto responseDto) {
        return new Movement(createSquare(responseDto.source()), createSquare(responseDto.target()));
    }

    private static Square createSquare(final String square) {
        final List<String> commandToken = List.of(square.split(""));
        final File file = File.findByValue(commandToken.get(0));
        final Rank rank = Rank.findByValue(commandToken.get(1));
        return new Square(file, rank);
    }
}
