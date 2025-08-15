package com.pettime.service;

import com.pettime.model.User;
import com.pettime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Busca usuário por ID
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Busca usuário por email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não cadastrado"));
    }

    // Verifica se email já está em uso
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}