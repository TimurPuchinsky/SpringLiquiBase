package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.*;
import com.example.springliquidbase.domain.user.UserAuthenticateModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.infrastructure.repository.userrepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSessionService userSessionService;
    //private final AuthenticationManager authenticationManager;

    public PageResultModel getAll(UserPageModel userPageModel) {
        return userRepository.getPage(userPageModel);
    }

    public UserModel getUserById(UUID id) {
        var user = userRepository.findUserById(id);
        return user;
    }

    public UserModel getUserByLogin(String login) {
        var user = userRepository.findUserByLogin(login);
        return user;
    }

    public UserModel getUserByEmail(String email) {
        var user = userRepository.findUserByEmail(email);
        return user;
    }

    public LoginResultModel authenticateUser(UserAuthenticateModel user) {
        var findUser = userRepository.findUserByLogin(user.getLogin());
        if (findUser == null) {
            return new LoginResultModel("Error", "неправильный логин");
        }
        var password = userRepository.authentication(user.getPassword(), findUser.getPassword());
        if (!password) {
            return new LoginResultModel("Error", "неправильный пароль");
        }
//        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
//        if (auth == null) {
//            return new LoginResultModel("Error", "авторизация провалилась");
//        }
        var session = userSessionService.getSession(findUser);
        return new LoginResultModel(null, null, session.getAccess_token(), session.getRefresh_token());
    }

    public GuidResultModel addUser(UserCreateModel userModel) {
        if (userModel.getLogin().isBlank() || userModel.getPassword().isBlank() ||
                userModel.getEmail().isBlank() || userModel.getSurname().isBlank() ||
                userModel.getName().isBlank() || userModel.getFather().isBlank() ||
                userModel.getPhone().isBlank()) {
            return new GuidResultModel("Missing arguments", "не все поля заполнены");
        }
        var userLogin = getUserByLogin(userModel.getLogin());
        if (userLogin != null) {
            return new GuidResultModel("Existing value", "пользователь с таким логиным уже существует");
        }
        var userEmail = getUserByEmail(userModel.getEmail());
        if (userEmail != null) {
            return new GuidResultModel("Existing value", "пользователь с такой почтой уже существует");
        }
        if (!userModel.getEmail().contains("@")) {
            return new GuidResultModel("Incorrect value", "почта прописана не верно");
        }
        if (userModel.getPhone().length() <= 5) {
            return new GuidResultModel("Incorrect value", "номер телефона слишком короткий");
        }
        var user = userRepository.addNewUser(userModel);
        if (user == null) {
            return new GuidResultModel("NullException", "пользователь не добавлен");
        }
        return new GuidResultModel(user.getId());
    }

    public GuidResultModel updatePassword(UUID id, String password) {
        var user = getUserById(id);
        if (user == null) {
            return new GuidResultModel("NullException", "пользователь не нашелся");
        }
        return new GuidResultModel(userRepository.changeUserPassword(user, password));
    }

    public GuidResultModel updateLogin(UUID id, String login) {
        var user = getUserById(id);
        if (user == null) {
            return new GuidResultModel("NullException", "пользователь не нашелся");
        }
        var newLogin = getUserByLogin(login);
        if (newLogin != null) {
            return new GuidResultModel("Existing value", "пользователь с таким логиным уже существует");
        }
        return new GuidResultModel(userRepository.changeUserLogin(user, login));
    }

    public SuccessResultModel archiveUser(UUID id) {
        var findUser = userRepository.findUserById(id);
        if (findUser == null) {
            return new SuccessResultModel("NullException", "пользователь не нашелся");
        }
        if (findUser.getArchived() != null) {
            userRepository.userUnArchive(findUser);
            return new SuccessResultModel(false);
        } else {
            userRepository.userArchive(findUser);
            return new SuccessResultModel(true);
        }
    }

    public String logout(String access_token) {
        userSessionService.removeSession(access_token);
        return "выход";
    }
}
