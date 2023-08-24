package com.bmerouane.customer.vanilla;

import com.bmerouane.customer.Customer;
import com.bmerouane.customer.CustomerDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("vanilla")
public class CustomerListDataAccessService implements CustomerDao {
    // Db
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@gmail.com",
                21
        );
        customers.add(alex);
        Customer jamila = new Customer(
                2,
                "Jamila",
                "jamila@gmail.com",
                19
        );
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        return customers.stream().anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public boolean existPersonWithId(Integer id) {
        return customers.stream().anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }
}
