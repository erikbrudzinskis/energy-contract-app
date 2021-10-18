package com.evilkissyou.energycontractapp.security;

import com.evilkissyou.energycontractapp.entity.User;
import com.evilkissyou.energycontractapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AppUserDetailsServiceImpl implements AppUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Find user
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User email " + email + " could not be found");
        }
        User user = optionalUser.get();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                // Assign role to the user
                Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().getName())));
    }
}
