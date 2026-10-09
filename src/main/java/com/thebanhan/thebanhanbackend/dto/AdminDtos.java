package com.thebanhan.thebanhanbackend.dto;

public final class AdminDtos {

    private AdminDtos() {
    }

    public record LoginRequest(String id, String password) {
    }

    public record LoginResponse(boolean success, String id) {
    }
}
