package chess.connection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DBConnectionUtilTest {

    @DisplayName("DB 접속을 확인한다.")
    @Test
    void connection() {
        Connection connection = DBConnectionUtil.getConnection();

        assertThat(connection).isNotNull();
    }
}
