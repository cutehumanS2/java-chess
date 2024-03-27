package chess.domain.square;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class SquareTest {

    @DisplayName("출발지에서 목적지까지 수직으로 이동하면 True를 리턴한다.")
    @ParameterizedTest
    @CsvSource({"ONE", "EIGHT"})
    void returnTrueWhenMoveVertical(final Rank rank) {
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.e, rank);

        final boolean actual = source.isVertical(target);

        assertThat(actual).isTrue();
    }

    @DisplayName("출발지에서 목적지까지 수평으로 이동하면 True를 리턴한다.")
    @ParameterizedTest
    @CsvSource({"a", "h"})
    void returnTrueWhenMoveHorizontal(final File file) {
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(file, Rank.FOUR);

        final boolean actual = source.isHorizontal(target);

        assertThat(actual).isTrue();
    }

    @Nested
    class isDiagonalTest {

        @DisplayName("출발지에서 목적지까지 대각선으로 이동하면 True를 리턴한다.")
        @ParameterizedTest
        @CsvSource({"a, EIGHT", "h, SEVEN", "b, ONE", "h, ONE"})
        void returnTrueWhenMoveDiagonal(final File file, final Rank rank) {
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(file, rank);

            final boolean actual = source.isDiagonal(target);

            assertThat(actual).isTrue();
        }

        @DisplayName("출발지에서 목적지까지 대각선으로 이동하지 않으면 False를 리턴한다.")
        @Test
        void returnFalseWhenNotMoveDiagonal() {
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.e, Rank.FIVE);

            final boolean actual = source.isDiagonal(target);

            assertThat(actual).isFalse();
        }
    }

    @DisplayName("출발지의 파일과 목적지의 파일 사이의 거리를 구한다.")
    @Test
    void calculateFileDistanceFromSourceToTarget() {
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.h, Rank.FOUR);

        int actual = source.calculateFileDistance(target);

        assertThat(actual).isEqualTo(3);
    }

    @DisplayName("출발지의 랭크와 목적지의 랭크 사이의 거리를 구한다.")
    @Test
    void calculateRankDistanceFromSourceToTarget() {
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.e, Rank.SEVEN);

        int actual = source.calculateRankDistance(target);

        assertThat(actual).isEqualTo(3);
    }

    @DisplayName("목적지의 방향이 위이면 -1, 아래이면 1을 리턴한다.")
    @ParameterizedTest
    @CsvSource({"SIX, -1", "TWO, 1"})
    void returnDirectionToTarget(final Rank targetRank, final int direction) {
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.e, targetRank);

        int actual = source.calculateRankDirection(target);

        assertThat(actual).isEqualTo(direction);
    }

    @DisplayName("흰색 폰의 초기 랭크인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"TWO, true", "THREE, false"})
    void checkIfInitialRankOfWhitePawn(final Rank rank, final boolean expected) {
        final Square square = new Square(File.e, rank);

        boolean actual = square.isWhitePawnInitialRank();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("검정색 폰의 초기 랭크인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"SEVEN, true", "SIX, false"})
    void checkIfInitialRankOfBlackPawn(final Rank rank, final boolean expected) {
        final Square square = new Square(File.e, rank);

        boolean actual = square.isBlackPawnInitialRank();

        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    class findPathTest {

        @DisplayName("출발지에서 목적지까지 직선으로 이동하는 경우의 경로를 생성한다.")
        @Test
        void createPathWhenStraight() {
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.a, Rank.FOUR);

            final List<Square> actual = source.findPath(target);

            assertThat(actual).containsExactlyInAnyOrder(
                    new Square(File.b, Rank.FOUR),
                    new Square(File.c, Rank.FOUR),
                    new Square(File.d, Rank.FOUR));
        }

        @DisplayName("출발지에서 목적지까지 대각선으로 이동하는 경우의 경로를 생성한다.")
        @Test
        void createPathWhenDiagonal() {
            final Square source = new Square(File.e, Rank.FOUR);
            final Square target = new Square(File.a, Rank.EIGHT);

            final List<Square> actual = source.findPath(target);

            assertThat(actual).containsExactlyInAnyOrder(
                    new Square(File.d, Rank.FIVE),
                    new Square(File.c, Rank.SIX),
                    new Square(File.b, Rank.SEVEN));
        }

        @DisplayName("출발지에서 목적지까지 직선이나 대각선으로 이동하지 않는 경우 경로가 생성되지 않는다.")
        @Test
        void notCreatePathWhenNotStraightAndDiagonal() {
            final Square source = new Square(File.e, Rank.TWO);
            final Square target = new Square(File.d, Rank.FIVE);

            final List<Square> actual = source.findPath(target);

            assertThat(actual).isEmpty();
        }
    }
}
