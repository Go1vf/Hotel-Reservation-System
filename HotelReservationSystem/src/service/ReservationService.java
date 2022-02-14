package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService;
    private ReservationService(){}
    public static ReservationService getInstance() {
        if (Objects.isNull(reservationService)) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }
    private Collection<IRoom> listOfRooms = new HashSet<>();
    private Collection<Reservation> listOfReservations = new ArrayList<>();
    public void addRoom(IRoom room) {
        listOfRooms.add(room);
    }
    public IRoom getARoom(String roomId) {
        for (IRoom room: listOfRooms) {
            if (roomId.equals(room.getRoomNumber())) {
                return room;
            }
        }
        return null;
    }
    public Reservation reservationARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        System.out.println("Room" + room.getRoomNumber() + " is reserved.");
        listOfReservations.add(reservation);
        return reservation;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        // first assume all rooms are available initially
        Collection<IRoom> availableRooms = listOfRooms;
        // if there is no reservation, then all rooms are available by default
        if (listOfReservations.size() == 0) {
            return availableRooms;
        }
        for (Reservation reservation : listOfReservations) {
            if (checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckOutDate())
                || (checkInDate.before(reservation.getCheckInDate()) && checkOutDate.after(reservation.getCheckInDate()))) {
                    availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }
    public Collection<Reservation> getCustomersReservation(Customer customer){
        Collection<Reservation> customerReservation = new ArrayList<>();
        for (Reservation reservation: listOfReservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservation.add(reservation);
            }
        }
        return customerReservation;
    }
    public void printAllReservation(){
        System.out.println("Reservation list");
        for (Reservation reservation: listOfReservations) {
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> getRooms() {
        return listOfRooms;
    }
}
