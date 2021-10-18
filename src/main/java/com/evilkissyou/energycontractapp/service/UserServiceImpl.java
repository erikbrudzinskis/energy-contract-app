package com.evilkissyou.energycontractapp.service;

import com.evilkissyou.energycontractapp.entity.User;
import com.evilkissyou.energycontractapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(int id) {
        log.info("IN UserServiceImpl getById() {}", id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id " + id + " could not be found");
        }

        return optionalUser.get();
    }

    @Override
    public User getByEmail(String email) {
        log.info("IN UserServiceImpl getByEmail() {}", email);

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User email " + email + " could not be found");
        }

        return optionalUser.get();
    }
}
