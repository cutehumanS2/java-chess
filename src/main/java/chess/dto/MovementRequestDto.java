package chess.dto;

import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;

public record MovementRequestDto(
        Long roomId, String sourceFile, String sourceRank, String targetFile, String targetRank) {

    public static MovementRequestDto toDto(Long roomId, Square source, Square target) {
        return new MovementRequestDto(roomId,
                convertToFileString(source.file()), converToRankString(source.rank()),
                convertToFileString(target.file()), converToRankString(target.rank()));
    }

    private static String convertToFileString(final File file) {
        return file.name();
    }

    private static String converToRankString(final Rank rank) {
        return rank.name();
    }
}
