package com.turing.backendapi.service;

import com.turing.backendapi.authentication.AuthUserDetails;
import com.turing.backendapi.authentication.InvalidJwtAuthenticationException;
import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.repository.CustomerRepository;
import com.turing.backendapi.repository.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static com.turing.backendapi.service.converter.CustomerConverter.toDb;
import static com.turing.backendapi.service.converter.CustomerConverter.toDomain;


@Service
@Transactional(readOnly = true)
public class CustomerService implements UserDetailsService {
  private static final Random RANDOM = new SecureRandom();

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public String getNextSalt() {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);
    return bytesToHex(salt);
  }

  public String hashPassword(String password, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      return bytesToHex(md.digest((password + salt).getBytes()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Huston, we have a problem!", e);
    }
  }

  private static String bytesToHex(byte[] hashInBytes) {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < hashInBytes.length; i++) {
      sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();

  }

  public Customer getByEmail(String email){
    return toDomain(customerRepository.findByEmail(email));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    CustomerEntity byEmail = customerRepository.findByEmail(username);
    if(byEmail == null) {
      throw new InvalidJwtAuthenticationException("Invalid email");
    }

    return new AuthUserDetails(byEmail.getCustomer_id(), byEmail.getEmail(), "{noop}" + byEmail.getPasswordHash());
  }

  public Customer getById(Integer id) {
    return toDomain(customerRepository.findById(id).orElse(null));
  }

  @Transactional
  public Customer save(Customer newCustomer) {
    CustomerEntity savedEntity = customerRepository.save(toDb(newCustomer));

    return toDomain(savedEntity);
  }
}
