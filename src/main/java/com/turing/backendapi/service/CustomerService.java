package com.turing.backendapi.service;

import com.turing.backendapi.authentication.AuthUserDetails;
import com.turing.backendapi.authentication.InvalidJwtAuthenticationException;
import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.repository.CustomerRepository;
import com.turing.backendapi.repository.entity.CustomerEntity;
import com.turing.backendapi.service.converter.CustomerConverter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    log.info("loadUserByUsername(username: {})", username);
    CustomerEntity byEmail = customerRepository.findByEmail(username);
    if(byEmail == null) {
      throw new InvalidJwtAuthenticationException("Invalid email");
    }

    return new AuthUserDetails(byEmail.getCustomer_id(), byEmail.getEmail(), "{noop}" + byEmail.getPassword());
  }

  public Customer getById(Integer id) {
    log.info("getById(id: {})", id);
    return toDomain(customerRepository.findById(id).orElse(null));
  }

  @Transactional
  public Customer save(Customer newCustomer) {
    log.info("save()");
    CustomerEntity savedEntity = customerRepository.save(toDb(newCustomer));

    return toDomain(savedEntity);
  }
}
