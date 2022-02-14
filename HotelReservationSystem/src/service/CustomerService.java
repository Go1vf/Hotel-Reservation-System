package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerService {
    private static CustomerService customerService;
    private CustomerService(){};
    public static CustomerService getInstance() {
        if (Objects.isNull(customerService)) {
            customerService = new CustomerService();
        }
        return customerService;
    }
    Map<String, Customer> listOfCustomers = new HashMap<>();
    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        listOfCustomers.put(email, customer);
    }
    public Customer getCustomer(String customerEmail) {
        return listOfCustomers.get(customerEmail);
    }
    public Collection<Customer> getAllCustomers() {
        return listOfCustomers.values();
    }
}
