package collective.com.theeCollective.service.impl;

import collective.com.theeCollective.dto.CustomerDto;
import collective.com.theeCollective.model.Article;
import collective.com.theeCollective.model.Author;
import collective.com.theeCollective.model.Customer;
import collective.com.theeCollective.repository.CustomerRepository;
import collective.com.theeCollective.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map((customer) -> mapToCustomerDto(customer)).collect(Collectors.toList());
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDto findById(long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        return mapToCustomerDto(customer);
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {
        Customer customer = mapToCustomer(customerDto);
        customerRepository.save(customer);
    }

    @Override
    public void delete(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDto> searchUser(String query) {
        List<Customer> customers = customerRepository.searchCustomer(query);
        return customers.stream().map(customer -> mapToCustomerDto(customer)).collect(Collectors.toList());
    }

    @Override
    public Customer loginUser(String username) {
        Customer customer = new Customer();
        customer = customerRepository.findByUsername(username);
        return customer;
    }


    private CustomerDto mapToCustomerDto(Customer customer){
        CustomerDto customerDto = CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .names(customer.getNames())
                .email(customer.getEmail())
                .username(customer.getUsername())
                .age(customer.getAge())
                .password(customer.getPassword())
                .build();
        return customerDto;
    }

    private Customer mapToCustomer(CustomerDto customerDto){
        Customer customer = Customer.builder()
                .customerId(customerDto.getCustomerId())
                .names(customerDto.getNames())
                .email(customerDto.getEmail())
                .username(customerDto.getUsername())
                .age(customerDto.getAge())
                .password(customerDto.getPassword())
                .build();
        return customer;
    }

}
