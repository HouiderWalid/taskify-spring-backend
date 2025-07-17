package com.taskify.taskifyspringback.controllers;

import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public User Welcome(@AuthenticationPrincipal TaskifyUserDetails userDetails){
        return userDetails.user();
    }
}
