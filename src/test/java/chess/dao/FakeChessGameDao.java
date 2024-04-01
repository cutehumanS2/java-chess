package chess.dao;

import chess.domain.game.GameStatus;
import chess.view.mapper.ColorMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeChessGameDao implements ChessGameDao {

    private final Map<Long, String> gameStatuses;

    public FakeChessGameDao() {
        this.gameStatuses = new HashMap<>();
    }

    @Override
    public Long save(final GameStatus gameStatus) {
        gameStatuses.put(1L, ColorMapper.findNameByColor(gameStatus.getTurn()));
        return (long) gameStatuses.size();
    }

    @Override
    public Optional<GameStatus> findGameStatusById(final Long gameId) {
        if (gameStatuses.containsKey(gameId)) {
            return Optional.of(new GameStatus(ColorMapper.findColorByName(gameStatuses.get(gameId))));
        }
        return Optional.empty();
    }

    @Override
    public void update(final Long gameId, final GameStatus gameStatus) {
        gameStatuses.replace(gameId, ColorMapper.findNameByColor(gameStatus.getTurn()));
    }
}
