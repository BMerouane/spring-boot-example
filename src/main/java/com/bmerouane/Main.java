package com.bmerouane;

import com.bmerouane.customer.Customer;
import com.bmerouane.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer alexandre = new Customer(
                    "Alexandre",
                    "alexandre@gmail.com",
                    26
            );
            Customer christopher = new Customer(
                    "Christopher",
                    "christopher@gmail.com",
                    26
            );

            List<Customer> customers = List.of(alexandre, christopher);
            customerRepository.saveAll(customers);
        };
    }
}
