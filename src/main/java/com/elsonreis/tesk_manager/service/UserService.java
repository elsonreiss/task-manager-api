package com.elsonreis.tesk_manager.service;

import com.elsonreis.tesk_manager.dto.request.UserRequest;
import com.elsonreis.tesk_manager.dto.response.UserResponse;
import com.elsonreis.tesk_manager.entity.User;
import com.elsonreis.tesk_manager.mapper.UserMapper;
import com.elsonreis.tesk_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse createUser(UserRequest request) {

        // Verifica se o email já está registrado
        if(repository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email já cadastrado");
        }

        // Converte DTO para entidade
        User user = UserMapper.toEntity(request);

        // Persiste no banco de dados
        User savedUser = repository.save(user);

        // Retorna DTO com dados salvos
        return UserMapper.toDTO(savedUser);
    }

    public List<UserResponse> findAll() {
        // Busca todos os usuários do banco
        List<User> users = repository.findAll();

        // Converte lista de entidades para lista de DTOs
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserResponse findById(Long id) {
        // Busca o usuário pelo ID
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Converte entidade para DTO e retorna
        return UserMapper.toDTO(user);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        // Busca o usuário existente
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o email foi alterado e se já existe outro usuário com o mesmo email
        if (!existingUser.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email já cadastrado");
        }

        // Atualiza os dados do usuário
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setPassword(request.getPassword());

        // Salva as alterações no banco de dados
        User updatedUser = repository.save(existingUser);

        // Converte entidade atualizada para DTO e retorna
        return UserMapper.toDTO(updatedUser);
    }

    publiv void deleteUser(Long id) {
        // Verifica se o usuário existe antes de tentar deletar
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Deleta o usuário pelo ID
        repository.deleteById(id);
    }
}
