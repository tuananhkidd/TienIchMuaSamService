package com.kidd.shopping.auth.repository;

import com.kidd.shopping.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,String> {
    @Query("select u.id from User u where u.username = ?1")
    String getDataIDWithUsername(String userEmail);

    @Query("select u.actived from User u where u.username = ?1 and u.role = ?2")
    boolean isAccountActivated(String username, String role);

    User findByUsername(String username);
    User findFirstByRole(String role);



}
