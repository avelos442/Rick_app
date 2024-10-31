package com.example.domain.usecases;

import com.example.domain.repository.UserServiceInterface;

public class UserService implements UserServiceInterface {
    @Override
    public String generateRandomUsername() {
        int randomNumber = 100000 + (int) (Math.random() * 900000); // Случайное число от 100000 до 999999
        return String.valueOf(randomNumber);
    }
}

