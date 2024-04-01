package chess.dao;

import chess.connection.DBConnectionUtil;
import chess.domain.game.GameStatus;
import chess.view.mapper.ColorMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ChessGameDaoImpl implements ChessGameDao {

    @Override
    public Long save(final GameStatus gameStatus) {
        final String query = "insert into chess_game (turn) values(?)";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ColorMapper.findNameByColor(gameStatus.getTurn()));
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GameStatus> findGameStatusById(final Long gameId) {
        final String query = "select * from chess_game where id = ?";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return Optional.of(new GameStatus(ColorMapper.findColorByName(turn)));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long gameId, GameStatus gameStatus) {
        final String query = "update chess_game set turn = ? where id = ?";

        try (final Connection connection = DBConnectionUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ColorMapper.findNameByColor(gameStatus.getTurn()));
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
