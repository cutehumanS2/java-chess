package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

class GameResultTest {

    @Nested
    class calculateTotalScoreTest {

        @DisplayName("각 진영마다 현재 보드에 남아 있는 말에 대한 점수를 구한다.")
        @ParameterizedTest
        @CsvSource({"WHITE, 20.5", "BLACK, 19"})
        void calculateScore(final PieceColor color, final double expected) {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.QUEEN, PieceColor.WHITE),
                    new Square(File.c, Rank.THREE), new Piece(PieceType.ROOK, PieceColor.WHITE),
                    new Square(File.b, Rank.THREE), new Piece(PieceType.BISHOP, PieceColor.WHITE),
                    new Square(File.a, Rank.THREE), new Piece(PieceType.KNIGHT, PieceColor.WHITE),
                    new Square(File.d, Rank.THREE), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.QUEEN, PieceColor.BLACK),
                    new Square(File.c, Rank.FIVE), new Piece(PieceType.ROOK, PieceColor.BLACK),
                    new Square(File.b, Rank.FIVE), new Piece(PieceType.BISHOP, PieceColor.BLACK),
                    new Square(File.d, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.f, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK)
            ));

            final double actual = gameResult.calculateTotalScore(color);

            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("같은 세로줄에 같은 색의 폰이 있는 경우, 각 폰의 점수를 0.5로 계산한다.")
        @Test
        void calculateScoreWhenPawnCountIsMoreThanOneOnSameFile() {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.e, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE)
            ));

            final double actual = gameResult.calculateTotalScore(PieceColor.WHITE);

            assertThat(actual).isEqualTo(1);
        }

        @DisplayName("세로줄 당 폰이 하나씩만 있는 경우, 각 폰의 점수를 1로 계산한다.")
        @Test
        void calculateScoreWhenPawnCountIsOneOnSameFile() {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.d, Rank.FOUR), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE)
            ));

            final double actual = gameResult.calculateTotalScore(PieceColor.WHITE);

            assertThat(actual).isEqualTo(2);
        }
    }

    @Nested
    class findWinnerTest {

        @DisplayName("한쪽 진영의 킹이 잡혀서 게임이 종료된 경우, 이긴 진영을 구한다.")
        @ParameterizedTest
        @CsvSource({"WHITE", "BLACK"})
        void findFinalWinnerTeam(final PieceColor kingColor) {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.d, Rank.FOUR), new Piece(PieceType.KING, kingColor),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK)
            ));

            final PieceColor actual = gameResult.findWinnerTeam();

            assertThat(actual).isEqualTo(kingColor);
        }

        @DisplayName("현재 이기고 있는 진영을 구한다.")
        @ParameterizedTest
        @CsvSource({"QUEEN, WHITE, WHITE", "QUEEN, BLACK, BLACK", "EMPTY, NONE, NONE"})
        void findCurrentWinnerTeam(final PieceType type, final PieceColor color, final PieceColor expected) {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.d, Rank.FOUR), new Piece(type, color),
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.e, Rank.SIX), new Piece(PieceType.KING, PieceColor.BLACK),
                    new Square(File.f, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.f, Rank.SIX), new Piece(PieceType.KING, PieceColor.WHITE)
            ));

            final PieceColor actual = gameResult.findWinnerTeam();

            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("양쪽 진영 모두 킹이 없는 경우, 예외가 발생한다.")
        @Test
        void occurExceptionWhenNoKingOnGameStatus() {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK)
            ));

            assertThatThrownBy(gameResult::findWinnerTeam)
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    class GameOverTest {

        @DisplayName("현재 보드 위 킹의 개수가 초기 킹 개수보다 적으면 게임이 종료된다.")
        @Test
        void gameOverWhenCurrentKingCountIsNotSameInitialKingCount() {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.BLACK),
                    new Square(File.f, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.f, Rank.SIX), new Piece(PieceType.KING, PieceColor.WHITE)
            ));

            boolean actual = gameResult.isGameOver();

            assertThat(actual).isTrue();
        }

        @DisplayName("현재 보드 위 킹의 개수가 초기 킹 개수와 같으면 게임이 종료되지 않는다.")
        @Test
        void notGameOverWhenCurrentKingCountIsSameInitialKingCount() {
            final GameResult gameResult = new GameResult(Map.of(
                    new Square(File.e, Rank.FIVE), new Piece(PieceType.KING, PieceColor.BLACK),
                    new Square(File.f, Rank.FIVE), new Piece(PieceType.PAWN, PieceColor.WHITE),
                    new Square(File.f, Rank.SIX), new Piece(PieceType.KING, PieceColor.WHITE)
            ));

            boolean actual = gameResult.isGameOver();

            assertThat(actual).isFalse();
        }
    }
}
