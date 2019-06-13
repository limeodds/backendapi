package com.turing.backendapi.controller;

import com.turing.backendapi.authentication.AuthUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public interface Authentication {
  default AuthUserDetails getPrincipal() {
    return (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
