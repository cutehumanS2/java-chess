package chess.dao;

import chess.domain.room.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {

    Long save(final Room room);

    List<Room> findAll();

    Optional<Room> findById(final Long roomId);
}
