package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.models.User;
import com.raimi_dikamona.visorpost.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<User> getUserByEmail(String email){
        return repository.findByEmail(email);
    }

    public Optional<User> getUserByFirstname(String firstname){
        return repository.findByFirstName(firstname);
    }
}
