package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class HotelResource {
    private static HotelResource hotelResource;
    private HotelResource(){};
    public static HotelResource getInstance() {
        if (Objects.isNull(hotelResource)) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    public void CreateCustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reservationARoom(customer, room, checkInDate, checkOutDate);
    }
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }
    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }
}
