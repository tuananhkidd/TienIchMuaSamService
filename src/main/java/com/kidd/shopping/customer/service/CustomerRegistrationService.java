package com.kidd.shopping.customer.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Transaction;
import com.google.firebase.cloud.FirestoreClient;
import com.kidd.shopping.auth.entity.User;
import com.kidd.shopping.auth.repository.UserRepository;
import com.kidd.shopping.base.response.*;
import com.kidd.shopping.customer.entity.Customer;
import com.kidd.shopping.customer.entity.request.CustomerRegisterRequest;
import com.kidd.shopping.customer.repository.CustomerRepository;
import com.kidd.shopping.firebase.chat.ChatRoomUsersInfo;
import com.kidd.shopping.firebase.chat.ChatUser;
import com.kidd.shopping.firebase.chat.TypingState;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
                                     WebRequest webRequest) throws ExecutionException, InterruptedException {
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


            //create user in firestore
            ChatUser userChat = new ChatUser(user.getId(), customer.getEmail(), customer.getFirstName(), customer.getLastName(),customer.getAvatarUrl());
            FirestoreClient.getFirestore().collection(Constant.USERS_COLLECTION)
                    .document(user.getId())
                    .set(userChat);
            //create chat room with admin
            User admin = userRepository.findFirstByRole(Constant.ADMIN);
            ChatUser adminChat = new ChatUser(admin.getId(), admin.getUsername(), "Admin","","");
            createChatRoomIfNotExist(user.getId(),admin.getId(),userChat,adminChat);

            //send email
//            String appUrl = webRequest.getContextPath();
//            applicationEventPublisher.publishEvent(new EmailVerificationEvent(user, webRequest.getLocale(), appUrl));

            return new OkResponse(customer);
        }
        return new ConflictResponse(ResponseConstant.Vi.EMAIL_EXIST);
    }

    public void createChatRoomIfNotExist(String userID,
                                         String adminID,
                                         ChatUser user,
                                         ChatUser admin) throws ExecutionException, InterruptedException {
        CollectionReference chatRoomsRef = FirestoreClient.getFirestore()
                .collection(Constant.CHAT_ROOMS);
        ApiFuture<QuerySnapshot> result = chatRoomsRef
                .whereEqualTo("usersInfo."+userID, userID)
                .whereEqualTo("usersInfo."+adminID, adminID)
                .get();
        DocumentReference newChatRoomRef = chatRoomsRef.document();
        if (result.get().getDocuments().size() == 0) {
            FirestoreClient.getFirestore().runTransaction((Transaction.Function<Void>) transaction -> {
                Map<String,ChatUser> chatUsers = new HashMap<>();
                chatUsers.put(userID,user);
                chatUsers.put(adminID,admin);
                ChatRoomUsersInfo chatRoomInfo = new ChatRoomUsersInfo(chatUsers);
                transaction.set(newChatRoomRef, chatRoomInfo);

                CollectionReference typingStatesRef = newChatRoomRef.collection(ChatRoomUsersInfo.TYPING_STATES);
                transaction.set(typingStatesRef.document(userID), new TypingState());
                transaction.set(typingStatesRef.document(adminID), new TypingState());
                return null;
            });
        }
    }
}
