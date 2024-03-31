package chess.dao;

import chess.connection.DBConnectionUtil;
import chess.domain.square.Square;
import chess.dto.Movement;
import chess.dto.MovementResponseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Movement> findMovementsById(final Long gameId) {
        final String query = "select * from movement where chess_game_id = ?";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Movement> movements = new ArrayList<>();
            while (resultSet.next()) {
                Movement movement = MovementResponseDto.toEntity(
                        MovementResponseDto.toDto(
                                resultSet.getLong("id"),
                                resultSet.getLong("chess_game_id"),
                                resultSet.getString("source"),
                                resultSet.getString("target")));
                movements.add(movement);
            }
            return movements;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
