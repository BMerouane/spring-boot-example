package com.bmerouane.repository.jdbc;

import com.bmerouane.model.customer.Customer;
import com.bmerouane.repository.CustomerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerJDBCDataAccessService.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT * FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        LOGGER.info("Update result = {}", result);
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        LOGGER.info("Update result = {}", result);
    }

    @Override
    public boolean existPersonWithId(Integer id) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getName() != null) {
            String sql = """
                    UPDATE customer
                    SET name = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, customer.getName(), customer.getId());
            LOGGER.info("Update customer name result = {}", result);
        }
        if (customer.getAge() != null) {
            String sql = """
                    UPDATE customer
                    SET age = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, customer.getAge(), customer.getId());
            LOGGER.info("Update customer age result = {}", result);
        }
        if (customer.getEmail() != null) {
            String sql = """
                    UPDATE customer
                    SET email = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, customer.getEmail(), customer.getId());
            LOGGER.info("Update customer email result = {}", result);
        }
    }

}
