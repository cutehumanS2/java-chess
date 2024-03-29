package chess.view.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceMapperTest {

    @DisplayName("기물의 타입과 색상에 따른 출력명을 리턴한다.")
    @ParameterizedTest
    @CsvSource({"PAWN, BLACK, P", "PAWN, WHITE, p"})
    void returnViewNameByPieceTypeAndPieceColor(final PieceType type, final PieceColor color, final String expected) {
        final String actual = PieceMapper.findNameByTypeAndColor(type, color);

        assertThat(actual).isEqualTo(expected);
    }

}
