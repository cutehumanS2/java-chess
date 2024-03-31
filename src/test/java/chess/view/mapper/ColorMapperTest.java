package chess.view.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.PieceColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ColorMapperTest {

    @DisplayName("기물의 색상에 해당하는 출력명을 리턴한다.")
    @ParameterizedTest
    @CsvSource({"BLACK, 검은색", "WHITE, 흰색", "NONE, 무승부"})
    void returnViewNameOfPieceColor(final PieceColor color, final String viewName) {
        final String actual = ColorMapper.findNameByColor(color);

        assertThat(actual).isEqualTo(viewName);
    }

    @DisplayName("기물의 출력명에 해당하는 색상을 리턴한다.")
    @ParameterizedTest
    @CsvSource({"BLACK, 검은색", "WHITE, 흰색", "NONE, 무승부"})
    void returnPieceColorOfViewName(final PieceColor color, final String viewName) {
        final PieceColor actual = ColorMapper.findColorByName(viewName);

        assertThat(actual).isEqualTo(color);
    }
}
