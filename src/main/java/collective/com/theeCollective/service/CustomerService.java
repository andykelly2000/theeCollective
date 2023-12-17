package collective.com.theeCollective.service;

import collective.com.theeCollective.dto.ArticleDto;
import collective.com.theeCollective.dto.CustomerDto;
import collective.com.theeCollective.model.Article;
import collective.com.theeCollective.model.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> findAllCustomers();
    Customer saveCustomer(Customer customer);

    CustomerDto findById(long customerId);

    void updateCustomer(CustomerDto customer);

    void delete(Long customerId);

    List<CustomerDto> searchUser(String query);

    Customer loginUser(String username);
}
