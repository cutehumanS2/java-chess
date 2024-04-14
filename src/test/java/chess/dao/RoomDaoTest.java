package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.room.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class RoomDaoTest {

    private final RoomDaoTestImpl roomDao = new RoomDaoTestImpl();

    @AfterEach
    public void tearDown() {
        roomDao.truncate();
    }

    @Test
    @DisplayName("방을 생성한다.")
    void crateRoom() {
        final Room room = new Room("고수만");

        final Long actual = roomDao.save(room);

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("방 목록을 조회한다.")
    void findAll() {
        final List<String> names = List.of("고수만", "길드원구함");
        for (final String name : names) {
            roomDao.save(new Room(name));
        }

        final List<Room> actual = roomDao.findAll();

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

        final Room actual = roomDao.findById(1L).get();

        assertThat(actual.getName()).isEqualTo(names.get(0));
    }
}
