package menu;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class AdminMenu {
    private static AdminResource adminResource = AdminResource.getInstance();
    private static HotelResource hotelResource = HotelResource.getInstance();
    public static void start() {
        boolean keepRunning = true;
        boolean addRoom = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    System.out.println("Welcome to the Admin Menu");
                    System.out.println("---------------------------------------");
                    System.out.println("1. See all Customers");
                    System.out.println("2. See all Rooms");
                    System.out.println("3. See all Reservations");
                    System.out.println("4. Add a Room");
                    System.out.println("5. Back to the Main Menu");
                    System.out.println("---------------------------------------");
                    System.out.println("Please select a number from 1 to 5");
                    int selection = Integer.parseInt(scanner.nextLine());
                    switch (selection) {
                        case 1:
                            adminResource.getAllCustomers();
                            break;
                        case 2:
                            adminResource.getAllRooms();
                            break;
                        case 3:
                            adminResource.displayAllReservations();
                            break;
                        case 4:
                            while (addRoom) {
                                boolean roomExist = true;
                                Scanner roomNumber = null;
                                while (roomExist) {
                                    System.out.println("Enter room number");
                                    roomNumber = new Scanner(System.in);
                                    if (hotelResource.getRoom(roomNumber.nextLine())!= null) {
                                        System.out.println("This room has been added. Please enter a different room number");
                                    } else {
                                        roomExist = false;
                                    }
                                }
                                System.out.println("Enter price per night");
                                Scanner roomPrice = new Scanner(System.in);
                                RoomType roomType = null;
                                boolean invalidType = true;
                                while (invalidType) {
                                    System.out.println("Enter room type: 1 for single, 2 for double");
                                    Scanner roomTypeNum = new Scanner(System.in);
                                    if (Integer.parseInt(roomTypeNum.nextLine()) == 1) {
                                        invalidType = false;
                                        roomType = RoomType.SINGLE;
                                    } else if (Integer.parseInt(roomTypeNum.nextLine()) == 2) {
                                        invalidType = false;
                                        roomType = RoomType.DOUBLE;
                                    } else {
                                        System.out.println("Please enter a valid room type");
                                    }
                                }
                                Room room = new Room(roomNumber.nextLine(), Double.parseDouble(roomPrice.nextLine()), roomType);
                                List<IRoom> rooms = new ArrayList<>();
                                rooms.add(room);
                                adminResource.addRoom(rooms);
                                System.out.println("Do you want to add another room? y/n");
                                boolean makeRoomAgain = true;
                                while (makeRoomAgain) {
                                    Scanner userChoice = new Scanner(System.in);
                                    if (userChoice.nextLine().toLowerCase().equals("y")) {
                                        makeRoomAgain = false;
                                    } else if (userChoice.nextLine().toLowerCase().equals("n")) {
                                        makeRoomAgain = false;
                                        addRoom = false;
                                    } else {
                                        System.out.println("Please enter y or n");
                                    }
                                }
                            }
                            break;
                        case 5:
                            keepRunning = false; //Admin menu ends.
                        default:
                            System.out.println("Please select a number between 1 and 5.");
                            break;
                    }
                } catch (Exception ex) {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

}
