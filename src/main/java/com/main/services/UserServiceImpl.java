package com.main.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.entity.User;
import com.main.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository urepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // ✅ Registration Method
    public boolean registerUser(User user) {

        if (urepo.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        urepo.save(user);

        return true;
    }

   // user login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = urepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
