package org.example.ems.service;

import org.example.ems.dto.RegisterRequest;
import org.example.ems.dto.UserDto;
import org.example.ems.model.Role;
import org.example.ems.model.User;
import org.example.ems.repository.RoleRepository;
import org.example.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.findByusername(request.userName()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Role role = roleRepository.findByName(resolveRoleName(request.role()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User(
                request.userName(),
                passwordEncoder.encode(request.password()),
                role
        );
        userRepository.save(user);

        return user;
    }

    public Optional<User> findByName(String username) {
        return userRepository.findByusername(username);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), getRoleName(user)))
                .toList();
    }

    private String resolveRoleName(String requestedRole) {
        if (requestedRole == null || requestedRole.isBlank()) {
            return "READ";
        }

        return requestedRole.trim().toUpperCase();
    }

    private String getRoleName(User user) {
        return user.getRole() == null ? null : user.getRole().getName();
    }
}
