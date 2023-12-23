package com.bmerouane.controller;

import com.bmerouane.dto.CustomerRegistrationRequest;
import com.bmerouane.dto.CustomerUpdateRequest;
import com.bmerouane.model.customer.Customer;
import com.bmerouane.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/{id}")
    public Customer getCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomer(id);
    }

    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomerById(id);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable("id") Integer id, @RequestBody CustomerUpdateRequest request) {
        customerService.updateCustomer(id, request);
    }

}
