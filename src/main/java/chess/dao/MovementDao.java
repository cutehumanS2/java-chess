package chess.dao;

import chess.connection.DBConnectionUtil;
import chess.domain.square.Square;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MovementDao implements MovementRepository {

    @Override
    public Long save(final Long gameId, final Square source, final Square target) {
        final String query = "insert into movement (chess_game_id, source, target) values(?, ?, ?)";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, source.file().name() + source.rank().getIndex());
            preparedStatement.setString(3, target.file().name() + target.rank().getIndex());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
