package chess.service;

import chess.dao.RoomDao;
import chess.domain.room.Room;

import java.util.List;
import java.util.Optional;

public class RoomService {

    private final RoomDao roomDao;

    public RoomService(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Long createRoom(final String name) {
        return roomDao.save(new Room(name));
    }

    public List<Room> findAllRooms() {
        return roomDao.findAll();
    }

    public Optional<Room> findRoomById(final Long roomId) {
        Optional<Room> room = roomDao.findById(roomId);
        if (room.isEmpty()) {
            throw new IllegalArgumentException(roomId + "번 방을 찾을 수 없습니다.");
        }
        return room;
    }
}
