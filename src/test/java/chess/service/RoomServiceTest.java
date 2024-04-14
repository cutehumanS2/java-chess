package chess.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dao.RoomDaoTestImpl;
import chess.domain.room.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class RoomServiceTest {

    private final RoomDaoTestImpl roomDao = new RoomDaoTestImpl();
    private final RoomService roomService = new RoomService(roomDao);

    @AfterEach
    public void tearDown() {
        roomDao.truncate();
    }

    @Test
    @DisplayName("방을 생성한다.")
    void createRoom() {
        final String name = "고수만";

        final Long actual = roomService.createRoom(name);

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("방 목록을 조회한다.")
    void findAll() {
        final List<String> names = List.of("고수만", "길드원구함");
        for (final String name : names) {
            roomDao.save(new Room(name));
        }

        final List<Room> actual = roomService.findAllRooms();

        assertAll(
                () -> assertThat(actual.get(0).getName()).isEqualTo(names.get(0)),
                () -> assertThat(actual.get(1).getName()).isEqualTo(names.get(1))
        );
    }

    @Test
    @DisplayName("방 아이디로 방을 조회한다.")
    void findById() {
        final List<String> names = List.of("고수만", "길드원구함");
        for (final String name : names) {
            roomDao.save(new Room(name));
        }

        final Room actual = roomService.findRoomById(1L).get();

        assertThat(actual.getName()).isEqualTo(names.get(0));
    }

    @Test
    @DisplayName("없는 방을 조회하면 예외가 발생한다.")
    void throwExceptionIfRoomNotFound() {
        final List<String> names = List.of("고수만", "길드원구함");
        for (final String name : names) {
            roomDao.save(new Room(name));
        }

        assertThatThrownBy(() -> roomService.findRoomById(3L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
