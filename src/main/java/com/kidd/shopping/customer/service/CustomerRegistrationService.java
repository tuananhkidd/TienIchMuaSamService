package com.kidd.shopping.customer.service;

import com.kidd.shopping.auth.entity.User;
import com.kidd.shopping.auth.repository.UserRepository;
import com.kidd.shopping.base.response.*;
import com.kidd.shopping.customer.entity.Customer;
import com.kidd.shopping.customer.entity.request.CustomerRegisterRequest;
import com.kidd.shopping.customer.repository.CustomerRepository;
import com.kidd.shopping.utils.Constant;
import com.kidd.shopping.utils.EmailValidator;
import com.kidd.shopping.utils.PasswordValidator;
import com.kidd.shopping.utils.ResponseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

@Service
public class CustomerRegistrationService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response createNewStudent(CustomerRegisterRequest customerRegisterRequest,
                                     WebRequest webRequest) {
        if (!EmailValidator.isEmailValid(customerRegisterRequest.getUsername())) {
            return new BadRequestResponse(ResponseConstant.ErrorMessage.INVALID_EMAIL);
        }
        if (!PasswordValidator.isPasswordValid(customerRegisterRequest.getPassword())) {
            return new ForbiddenResponse(ResponseConstant.ErrorMessage.PASSWORD_TOO_SHORT);
        }

        User userFound = userRepository.findByUsername(customerRegisterRequest.getUsername());
        if (userFound == null) {
            User user = new User();
            user.setUsername(customerRegisterRequest.getUsername());
            user.setRole(Constant.CUSTOMER);
            user.setPassword(bCryptPasswordEncoder.encode(customerRegisterRequest.getPassword()));
            user.setAccountType(Constant.EMAIL);
            userRepository.save(user);

            Customer customer = new Customer(customerRegisterRequest);
            customer.setUser(user);
            customerRepository.save(customer);

            //send email
//            String appUrl = webRequest.getContextPath();
//            applicationEventPublisher.publishEvent(new EmailVerificationEvent(user, webRequest.getLocale(), appUrl));

            return new OkResponse(customer);
        }
        return new ConflictResponse(ResponseConstant.Vi.EMAIL_EXIST);
    }
}
