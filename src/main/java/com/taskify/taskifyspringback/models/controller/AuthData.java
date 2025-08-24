package com.taskify.taskifyspringback.models.controller;


import java.util.Optional;

import com.taskify.taskifyspringback.models.entities.User;

public record AuthData(Optional<User> user, String token) {
}
