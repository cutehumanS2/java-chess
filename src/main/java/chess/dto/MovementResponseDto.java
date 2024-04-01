package chess.dto;

import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;

public record MovementResponseDto(
        Long id, Long gameId, String sourceFile, String sourceRank, String targetFile, String targetRank) {

    public static Movement toMovement(final MovementResponseDto responseDto) {
        return new Movement(convertToSquare(responseDto.sourceFile, responseDto.sourceRank),
                convertToSquare(responseDto.targetFile, responseDto.targetRank));
    }

    private static Square convertToSquare(final String squareFile, final String sourceRank) {
        final File file = File.findByValue(squareFile);
        final Rank rank = Rank.valueOf(sourceRank);
        return new Square(file, rank);
    }
}
