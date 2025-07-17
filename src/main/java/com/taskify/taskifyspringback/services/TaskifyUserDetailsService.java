package com.taskify.taskifyspringback.services;

import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;
import com.taskify.taskifyspringback.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskifyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public TaskifyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TaskifyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new TaskifyUserDetails(user);
    }
}
