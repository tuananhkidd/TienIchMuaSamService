package com.kidd.shopping.base;

import com.kidd.shopping.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseCustomerController extends BaseController {
    @Autowired
    private UserRepository userRepository;

    protected String getAuthenticatedCustomerID() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userRepository.getDataIDWithUsername(userEmail);
    }
}
