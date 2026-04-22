package org.example.car.service;

import org.example.car.dto.auth.AuthResponse;
import org.example.car.dto.auth.LoginRequest;
import org.example.car.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}

