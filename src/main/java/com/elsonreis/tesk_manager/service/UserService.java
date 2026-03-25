package com.elsonreis.tesk_manager.service;

import com.elsonreis.tesk_manager.dto.request.UserRequest;
import com.elsonreis.tesk_manager.dto.response.UserResponse;
import com.elsonreis.tesk_manager.entity.User;
import com.elsonreis.tesk_manager.mapper.UserMapper;
import com.elsonreis.tesk_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final UserRepository repository;

    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest request) {

        if(repository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email já cadastrado");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = repository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    public List<UserResponse> findAll() {

        List<User> users = repository.findAll();

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserResponse findById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return UserMapper.toDTO(user);
    }

    public UserResponse updateUser(Long id, UserRequest request) {

        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!existingUser.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email já cadastrado");
        }

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setPassword(request.getPassword());

        User updatedUser = repository.save(existingUser);

        return UserMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        repository.deleteById(id);
    }
}
