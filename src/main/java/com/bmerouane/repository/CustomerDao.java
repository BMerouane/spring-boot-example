package com.bmerouane.repository;

import com.bmerouane.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existPersonWithEmail(String email);

    void deleteCustomerById(Integer id);

    boolean existPersonWithId(Integer id);

    void updateCustomer(Customer customer);
}
