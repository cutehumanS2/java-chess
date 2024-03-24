package chess.domain.square;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

@DisplayName("랭크")
class RankTest {

    @DisplayName("좌표가 숫자가 아닐 경우 예외가 발생한다.")
    @Test
    void occurExceptionIfRankNotNumeric() {
        assertThatCode(() -> Rank.findByValue("삼"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("좌표가 범위를 벗어날 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "9"})
    void occurExceptionIfRankIsOutOfRange(String rank) {
        assertThatCode(() -> Rank.findByValue(rank))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 랭크인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"ONE, true", "TWO, false"})
    void checkIsSameFile(Rank other, boolean expected) {
        Rank rank = Rank.ONE;

        boolean actual = rank.isSameRank(other);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("두 랭크 간 거리를 구한다.")
    @Test
    void calculateDistanceBetweenTwoRanks() {
        Rank rank = Rank.ONE;
        Rank other = Rank.EIGHT;

        int actual = rank.calculateDistance(other);

        assertThat(actual).isEqualTo(7);
    }

    @DisplayName("두 랭크 간 거리를 이용해 이동 방향을 구한다.")
    @ParameterizedTest
    @CsvSource({"FOUR, 1", "FIVE, 0", "SIX, -1"})
    void calculateDirectionUsingDistanceBetweenTwoRanks(Rank other, int expected) {
        Rank rank = Rank.FIVE;

        int actual = rank.calculateDirection(other);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("두 랭크 간 경로를 찾는다.")
    @Test
    void findPathBetweenTwoRanks() {
        Rank rank = Rank.ONE;
        Rank other = Rank.FOUR;

        List<Rank> actual = rank.findRankPath(other);

        assertThat(actual).containsExactlyInAnyOrder(Rank.TWO, Rank.THREE);
    }
}
