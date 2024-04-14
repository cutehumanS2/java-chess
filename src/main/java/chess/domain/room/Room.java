package chess.domain.room;

public class Room {

    private final long id;
    private final String name;

    public Room(final String name) {
        this(0, name);
    }

    public Room(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
