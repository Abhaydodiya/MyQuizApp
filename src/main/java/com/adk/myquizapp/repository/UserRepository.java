package com.adk.myquizapp.repository;

import com.adk.myquizapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    public User getUserByEmail(String email);

    public List<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    public List<User> findByEmailAndPassword(String email, String password);

    public List<User> findByRole(String role);



}
