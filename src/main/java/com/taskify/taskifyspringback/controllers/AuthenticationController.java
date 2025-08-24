package com.taskify.taskifyspringback.controllers;

import com.taskify.taskifyspringback.models.controller.AuthData;
import com.taskify.taskifyspringback.models.controller.Response;
import com.taskify.taskifyspringback.models.dto.SignInForm;
import com.taskify.taskifyspringback.models.dto.SignUpForm;
import com.taskify.taskifyspringback.models.dto.user.AuthenticationUserDto;
import com.taskify.taskifyspringback.models.entities.Role;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;
import com.taskify.taskifyspringback.services.RoleService;
import com.taskify.taskifyspringback.services.UserService;
import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.Optional;

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
    public Response signIn(@RequestBody @Valid SignInForm signInForm) {

        String email = signInForm.getEmail();
        String password = signInForm.getPassword();
        String token = userService.verify(email, password);

        return new Response(200, new AuthData(userService.findByEmail(email), token), "");
    }

    @PostMapping("/sign_up")
    public Response signUp(@RequestBody @Valid SignUpForm signUpForm) {

        Role role = roleService.findByName(Role.MEMBER_ROLE);

        String email = signUpForm.getEmail();
        String password = signUpForm.getPassword();

        User user = new User();
        user.setRole(role);
        user.setFullName(signUpForm.getFullName());
        user.setEmail(email);
        user.setPassword(password);
        user.setPermissions(new HashSet<>(role.getPermissions()));

        userService.create(user);
        String token = userService.verify(email, password);

        return new Response(200, new AuthData(Optional.of(user), token), "");
    }

    @GetMapping("/authenticate")
    public Response authenticate(@AuthenticationPrincipal TaskifyUserDetails userDetails) {
        return new Response(200, AuthenticationUserDto.fromEntity(userDetails.user()), "");
    }
}
