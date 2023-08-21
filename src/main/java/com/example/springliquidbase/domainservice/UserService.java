package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.infrastructure.repository.userrepository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Collection<UserModel> getAll() {
        return userRepository.getAllUsers();
    }

    public UserModel getUser(String login) {
        return  userRepository.getUserByLogin(login);
    }

    public String authenticateUser(String login, String password) {
        return userRepository.authentication(login, password);
    }

    public String register(UserModel userModel) {
        return userRepository.register(userModel);
    }
}
