package com.raimi_dikamona.visorpost.repositories;

import com.raimi_dikamona.visorpost.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFirstName(String firstName);
}
