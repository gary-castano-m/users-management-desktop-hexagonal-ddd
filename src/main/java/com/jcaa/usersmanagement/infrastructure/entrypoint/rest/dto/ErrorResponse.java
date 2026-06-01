package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto;

public record ErrorResponse(
        String error,
        String message) {}
