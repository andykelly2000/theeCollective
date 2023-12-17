package collective.com.theeCollective.repository;

import collective.com.theeCollective.model.Article;
import collective.com.theeCollective.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import collective.com.theeCollective.model.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository <Customer, Long> {
    @Query("select c from Customer c where c.username like concat('%', :query, '%') ")
    List<Customer> searchCustomer(String query);
    Customer findByUsername(String username);
}
