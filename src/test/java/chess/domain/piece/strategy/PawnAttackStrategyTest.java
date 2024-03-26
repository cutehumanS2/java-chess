package chess.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PawnAttackStrategyTest {

    @DisplayName("공격을 할 수 있으면 True를 리턴한다.")
    @Test
    void returnTrueWhenCanAttack() {
        final MoveStrategy moveStrategy = new PawnAttackStrategy();
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = new Square(File.f, Rank.FIVE);

        boolean actual = moveStrategy.canMove(source, target);

        assertThat(actual).isTrue();
    }

    @DisplayName("공격을 할 수 없으면 False를 리턴한다.")
    @Test
    void returnFalseWhenCannotAttack() {
        final MoveStrategy moveStrategy = new PawnAttackStrategy();
        final Square source = new Square(File.e, Rank.FOUR);
        final Square target = null; // TODO: null x

        boolean actual = moveStrategy.canMove(source, target);

        assertThat(actual).isFalse();
    }
}
