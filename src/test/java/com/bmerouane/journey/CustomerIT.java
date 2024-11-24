package com.bmerouane.journey;

import com.bmerouane.customer.dto.CustomerRegistrationRequest;
import com.bmerouane.customer.dto.CustomerUpdateRequest;
import com.bmerouane.customer.model.Customer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerIT {

    private static final Random RANDOM = new Random();
    private static final String CUSTOMER_URI = "/api/v1/customers";

    @Autowired
    private WebTestClient webTestClient;

    private CustomerRegistrationRequest createRandomCustomerRequest() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.name().lastName() + "-" + UUID.randomUUID() + "@foobar.com";
        int age = RANDOM.nextInt(1, 100);
        return new CustomerRegistrationRequest(name, email, age);
    }

    private int registerCustomer(CustomerRegistrationRequest request) {
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        return allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(request.email()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
    }

    @Test
    void canRegisterCustomer() {
        CustomerRegistrationRequest request = createRandomCustomerRequest();
        int id = registerCustomer(request);

        Customer expectedCustomer = new Customer(id, request.name(), request.email(), request.age());

        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .consumeWith(response -> {
                    Customer customer = response.getResponseBody();
                    assertThat(customer).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedCustomer);
                });
    }

    @Test
    void canDeleteCustomer() {
        CustomerRegistrationRequest request = createRandomCustomerRequest();
        int id = registerCustomer(request);

        webTestClient.delete()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        CustomerRegistrationRequest request = createRandomCustomerRequest();
        int id = registerCustomer(request);

        String newName = request.name() + " updated";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newName, request.email(), request.age() + 1);

        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(id, newName, request.email(), request.age() + 1);
        assertThat(updatedCustomer).isEqualTo(expected);
    }
}