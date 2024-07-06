package com.example.ecommercelaptopshop.library.service;

import com.example.ecommercelaptopshop.library.entity.Admin;
import com.example.ecommercelaptopshop.library.repository.AdminRepository;
import com.example.ecommercelaptopshop.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;


    public Admin save(Admin adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setEmail(adminDto.getEmail());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
