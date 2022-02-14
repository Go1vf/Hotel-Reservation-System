package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNum, RoomType roomType) {
        super(roomNum, 0.0, roomType);
    }
    public String toString() {
        return "This is a free room.\n" + super.toString();
    }
}
