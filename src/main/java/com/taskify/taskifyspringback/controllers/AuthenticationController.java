package com.taskify.taskifyspringback.controllers;

import com.taskify.taskifyspringback.models.controller.AuthData;
import com.taskify.taskifyspringback.models.dto.SignUpForm;
import com.taskify.taskifyspringback.models.entities.Role;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;
import com.taskify.taskifyspringback.services.RoleService;
import com.taskify.taskifyspringback.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final UserService userService;
    private final RoleService roleService;

    public AuthenticationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/sign_in")
    public AuthData signIn(@RequestBody @Valid SignUpForm signUpForm) {

        String email = signUpForm.getEmail();
        String password = signUpForm.getPassword();
        String token = userService.verify(email, password);

        return new AuthData(userService.findByEmail(email), token);
    }

    @PostMapping("/sign_up")
    public AuthData signUp(@RequestBody @Valid SignUpForm signUpForm) {

        Role role = roleService.findByName(Role.MEMBER_ROLE);

        String email = signUpForm.getEmail();
        String password = signUpForm.getPassword();

        User user = new User();
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);

        userService.create(user);
        String token = userService.verify(email, password);

        return new AuthData(user, token);
    }

    @GetMapping("/authenticate")
    public User authenticate(@AuthenticationPrincipal TaskifyUserDetails userDetails) {
        return userDetails.user();
    }
}
