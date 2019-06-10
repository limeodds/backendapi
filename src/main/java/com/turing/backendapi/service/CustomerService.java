package com.turing.backendapi.service;

import com.turing.backendapi.authentication.AuthUserDetails;
import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.repository.CustomerRepository;
import com.turing.backendapi.repository.entity.CustomerEntity;
import com.turing.backendapi.service.converter.CustomerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.turing.backendapi.service.converter.CustomerConverter.toDb;
import static com.turing.backendapi.service.converter.CustomerConverter.toDomain;


@Service
@Transactional(readOnly = true)
public class CustomerService implements UserDetailsService {
  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer getByEmail(String email){
    return toDomain(customerRepository.findByEmail(email));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    CustomerEntity byEmail = customerRepository.findByEmail(username);
    return new AuthUserDetails(byEmail.getEmail(), "{noop}" + byEmail.getPassword());
  }

  @Transactional
  public Customer save(Customer newCustomer) {
    CustomerEntity savedEntity = customerRepository.save(toDb(newCustomer));

    return toDomain(savedEntity);
  }
}
