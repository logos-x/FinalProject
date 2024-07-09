package com.example.EcommerceLaptopShop.validator;

import com.example.EcommerceLaptopShop.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository == null)
            return true;
        return userRepository.findByUserName(username) == null;
    }
}
