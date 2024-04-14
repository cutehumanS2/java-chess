package chess.view.outputview;

import chess.domain.room.Room;

import java.util.List;

public class RoomOutputView {

    public void printStartMessage() {
        System.out.println("> 체스 게임을 시작합니다.");
    }

    public void printCommandMessage() {
        System.out.println("> 입장 가능한 방 조회 : show");
        System.out.println("> 방 입장 : enter 방 번호 - 예. enter 1");
        System.out.println("> 방 생성 : create 방 이름(최대 50자, 띄어쓰기, 특수문자 불가능) - 예. create 고수만");
        System.out.println("> 게임 종료 : end");
    }

    public void printRooms(final List<Room> rooms) {
        System.out.println("> 입장 가능한 방 목록입니다.");
        if (rooms.isEmpty()) {
            System.out.println("--입장 가능한 방이 없습니다.--");
            return;
        }
        for (final Room room : rooms) {
            System.out.printf("%d. %s%n", room.getId(), room.getName());
        }
    }

    public void printSaveRoomMessage(final Long roomId, final String name) {
        System.out.printf("> %d번 [%s] 방이 생성되었습니다.%n", roomId, name);
    }

    public void printErrorMessage(final String message) {
        System.out.println("> " + message);
    }
}
