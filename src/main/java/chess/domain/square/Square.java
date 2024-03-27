package chess.domain.square;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record Square(File file, Rank rank) {

    public boolean isVertical(final Square target) {
        return file == target.file;
    }

    public boolean isHorizontal(final Square target) {
        return rank == target.rank;
    }

    public boolean isDiagonal(final Square target) {
        return calculateFileDistance(target) == calculateRankDistance(target);
    }

    public int calculateFileDistance(final Square target) {
        return file.calculateDistance(target.file);
    }

    public int calculateRankDistance(final Square target) {
        return rank.calculateDistance(target.rank);
    }

    public int calculateRankDirection(final Square target) {
        return rank.calculateDirection(target.rank);
    }

    public boolean isWhitePawnInitialRank() {
        return rank == Rank.TWO;
    }

    public boolean isBlackPawnInitialRank() {
        return rank == Rank.SEVEN;
    }

    public List<Square> findPath(final Square target) {
        if (!(isStraight(target) || isDiagonal(target))) {
            return List.of();
        }

        final List<File> filePath = file.findFilePath(target.file);
        final List<Rank> rankPath = rank.findRankPath(target.rank);
        return createPath(filePath, rankPath);
    }

    private boolean isStraight(final Square target) {
        return isVertical(target) || isHorizontal(target);
    }

    private List<Square> createPath(final List<File> filePath, final List<Rank> rankPath) {
        if (filePath.isEmpty()) {
            return mapToPathList(rankPath, rank -> new Square(file, rank));
        }

        if (rankPath.isEmpty()) {
            return mapToPathList(filePath, file -> new Square(file, rank));
        }

        final List<Square> path = new ArrayList<>();
        for (int i = 0; i < filePath.size(); i++) {
            path.add(new Square(filePath.get(i), rankPath.get(i)));
        }
        return path;
    }

    private <T> List<Square> mapToPathList(final List<T> list, final Function<T, Square> mapper) {
        return list.stream()
                .map(mapper)
                .toList();
    }
}
