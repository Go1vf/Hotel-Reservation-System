package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static HotelResource hotelResource = HotelResource.getInstance();
    private static AdminResource adminResource = AdminResource.getInstance();
    public static void start() {
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    System.out.println("Welcome to the Hotel Reservation System");
                    System.out.println("---------------------------------------");
                    System.out.println("1. Find and reserve a room");
                    System.out.println("2. See my reservations");
                    System.out.println("3. Create an account");
                    System.out.println("4. Admin");
                    System.out.println("5. Exit");
                    System.out.println("---------------------------------------");
                    System.out.println("Please select a number for the menu option");
                    int selection = Integer.parseInt(scanner.nextLine());
                    switch (selection) {
                        case 1:
                            System.out.println("Enter check-in date: mm/dd/yyyy");
                            Scanner checkInDate = new Scanner(System.in);
                            System.out.println("Enter check-out date: mm/dd/yyyy");
                            Scanner checkOutDate = new Scanner(System.in);
                            Date checkIn = new SimpleDateFormat("dd-MM-yyyy").parse(checkInDate.nextLine());
                            Date checkOut = new SimpleDateFormat("dd-MM-yyyy").parse(checkOutDate.nextLine());
                            Collection<IRoom> listOfRooms = hotelResource.findARoom(checkIn, checkOut);
                            if (listOfRooms.size() == 0) { // no available rooms
                                System.out.println("This are no available rooms within this period of time");
                            } else {
                                System.out.println(listOfRooms);
                                System.out.println("Would you like to book a room? y/n");
                                Scanner userChoice = new Scanner(System.in);
                                boolean book = true;
                                while (book) {
                                    if (userChoice.nextLine().equals("y")) {
                                        System.out.println("Do you have an account? y/n");
                                        boolean createAccount = true;
                                        while (createAccount) {
                                            Scanner hasAccount = new Scanner(System.in);
                                            if (hasAccount.nextLine().equals("y")) {
                                                System.out.println("Enter your email:");
                                                Scanner email = new Scanner(System.in);
                                                System.out.println("What room would you like to reserve? Enter room number:");
                                                Scanner roomNum = new Scanner(System.in);
                                                IRoom room = hotelResource.getRoom(roomNum.nextLine());
                                                Reservation reservation = hotelResource.bookARoom(email.nextLine(), room, checkIn, checkOut);
                                                System.out.println(reservation);
                                            } else if (hasAccount.nextLine().equals("n")) {
                                                createAccount();
                                            } else {
                                                System.out.println("Please enter y or n");
                                            }
                                        }
                                    } else if (userChoice.nextLine().equals("n")) {
                                        book = false;
                                    } else {
                                        System.out.println("Please enter y or n");
                                    }
                                }
                            }
                            break;
                        case 2:
                            Scanner input = new Scanner(System.in);
                            System.out.println("Would you like to see your current reservation(s)?");
                            System.out.println("Please enter your registered email address.");
                            String email = input.nextLine();
                            Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
                            for (Reservation reservation : reservations) {
                                System.out.println(reservation);
                            }
                            break;
                        case 3:
                        {
                            try {
                                createAccount();
                            } catch (Exception e) {
                                System.out.println(e.getLocalizedMessage());
                            } finally {
                                MainMenu.start();
                            }
                        }
                        case 4:
                            System.out.println("Entering Admin Menu");
                            AdminMenu.start();
                            break;
                        case 5:
                            System.out.println("Exiting main menu");
                            System.exit(1);
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

    public static void createAccount() {
        Scanner userEmail = null;
        boolean emailExit = true;
        while (emailExit) {
            System.out.println("Enter Email in format: name@domain.com");
            userEmail = new Scanner(System.in);
            if (hotelResource.getCustomer(userEmail.nextLine()) != null) {
                System.out.println("This email has been registered. Please enter a different email number");
            } else {
                emailExit = false;
            }
        }

        Scanner userInput = new Scanner(System.in);
        System.out.println("--------------------------------------------------");
        System.out.println("Enter First Name: ");
        String firstName = userInput.next();
        System.out.println("Enter Last Name: ");
        String lastName = userInput.next();
        hotelResource.CreateCustomer(userEmail.nextLine(), firstName, lastName);
        System.out.println("Account successfully registered");
    }
    public static void main(String[] args) {
        MainMenu.start();
    }
}
