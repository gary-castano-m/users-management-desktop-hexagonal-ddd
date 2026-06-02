package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto;

public record LoginRestRequest(
        String email,
        String password) {}