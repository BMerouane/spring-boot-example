package com.bmerouane.customer.repository;

import com.bmerouane.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);

    void deleteCustomerById(Integer id);

    boolean existsCustomerById(Integer id);

    void updateCustomer(Customer customer);
}
