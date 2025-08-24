package com.taskify.taskifyspringback.services;

import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.repositories.UserRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    protected final AuthenticationManager authManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository, JWTService jwtService, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public User create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findAllByRoleName(String roleName) {
        return this.userRepository.findAllByRole_Name(roleName);
    }

    public String verify(String email, String password) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Bad credentials");
        }

        return jwtService.generateToken(email);
    }
}
