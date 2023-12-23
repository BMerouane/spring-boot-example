package com.bmerouane;

import com.bmerouane.model.customer.Customer;
import com.bmerouane.repository.jpa.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var faker = new Faker();
            var name = faker.name();
            var firstName = name.firstName();
            var lastName = name.lastName();
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com",
                    faker.number().numberBetween(18, 100)
            );

            customerRepository.save(customer);
        };
    }
}
