package com.enrtreprise.api.repository;
import com.enrtreprise.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
