package model;

public class Room implements IRoom {
    protected String roomNumber;
    protected Double price;
    protected RoomType roomType;
    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }
    public String getRoomNumber() {
        return this.roomNumber;
    }
    public Double getRoomPrice() {
        return this.price;
    }
    public RoomType getRoomType() {
        return this.roomType;
    }
    public Boolean isFree() {
        return price == 0;
    }
    public String toString() {
        return "Room Number: " + roomNumber + "\n" +
                "Price:" + price + "\n" +
                "Room Type:" + roomType + "\n" + "--------------------" + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return this.roomNumber.equals(room.roomNumber);
    }

}
