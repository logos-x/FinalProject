package com.example.ecommercelaptopshop.library.service;

import com.example.ecommercelaptopshop.library.entity.Role;
import com.example.ecommercelaptopshop.library.entity.User;
import com.example.ecommercelaptopshop.library.repository.RoleRepository;
import com.example.ecommercelaptopshop.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void save(User user) {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUserName(user.getName());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (userId != 0 && roleId != 0)
            userRepository.addRoleToUser(userId, roleId);
    }

    public void addRoleForUser(Long userID, Long roleID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleID).orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<Role> getAllRoleOfUser(Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user != null) {
            return user.getRoles();
        }
        return null;
    }

    public User getById(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getRoles().removeIf(role -> role.getId().equals(roleId));
        userRepository.save(user);
    }

    public User createUserIfNotExists(String email, String name, String username, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);

            save(user);
        }

        return user;
    }
}
