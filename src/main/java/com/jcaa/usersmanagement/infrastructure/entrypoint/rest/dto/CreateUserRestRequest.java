package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto;

public record CreateUserRestRequest(
        String id,
        String name,
        String email,
        String password,
        String role) {}