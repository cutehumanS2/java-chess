package chess.dao;

import chess.connection.DBConnectionUtil;
import chess.dto.Movement;
import chess.dto.MovementRequestDto;
import chess.dto.MovementResponseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovementDaoImpl implements MovementDao {

    @Override
    public Long save(final MovementRequestDto requestDto) {
        final String query = "insert into movement (room_id, source_file, source_rank, target_file, target_rank) " +
                "values(?, ?, ?, ?, ?)";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, requestDto.roomId());
            preparedStatement.setString(2, requestDto.sourceFile());
            preparedStatement.setString(3, requestDto.sourceRank());
            preparedStatement.setString(4, requestDto.targetFile());
            preparedStatement.setString(5, requestDto.targetRank());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movement> findMovementsById(final Long roomId) {
        final String query = "select * from movement where room_id = ?";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Movement> movements = new ArrayList<>();
            while (resultSet.next()) {
                Movement movement = MovementResponseDto.toMovement(
                        new MovementResponseDto(
                                resultSet.getLong("id"),
                                resultSet.getLong("room_id"),
                                resultSet.getString("source_file"),
                                resultSet.getString("source_rank"),
                                resultSet.getString("target_file"),
                                resultSet.getString("target_rank")));
                movements.add(movement);
            }
            return movements;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
