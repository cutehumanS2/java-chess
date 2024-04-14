package chess.dao;

import chess.connection.TestDBConnectionUtil;
import chess.domain.room.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoTestImpl implements RoomDao {

    @Override
    public Long save(final Room room) {
        final String query = "insert into room (name) values(?)";

        try (final Connection connection = TestDBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, room.getName());
            preparedStatement.executeUpdate();

            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findAll() {
        final String query = "select * from room";

        try (final Connection connection = TestDBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            final ResultSet resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(new Room(resultSet.getLong("id"), resultSet.getString("name")));
            }
            return rooms;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Room> findById(final Long roomId) {
        final String query = "select * from room where id = ?";

        try (final Connection connection = TestDBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Room(resultSet.getLong("id"),
                        resultSet.getString("name")));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncate() {
        try (Connection connection = TestDBConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            statement.addBatch("set foreign_key_checks = 0");
            statement.addBatch("truncate table room");
            statement.addBatch("set foreign_key_checks = 1");
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
