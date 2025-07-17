package com.taskify.taskifyspringback.models.controller;


import com.taskify.taskifyspringback.models.entities.User;

public record AuthData(User user, String token) {
}
