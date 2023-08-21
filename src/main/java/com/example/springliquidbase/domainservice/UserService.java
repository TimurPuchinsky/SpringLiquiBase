package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.*;
import com.example.springliquidbase.domain.user.UserAuthenticateModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.infrastructure.repository.userrepository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public PageResultModel getAll(UserPageModel userPageModel) {
        return userRepository.getPage(userPageModel);
    }

    public UserModel getUserEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public SuccessResultModel authenticateUser(UserAuthenticateModel user) {
        var findUser = userRepository.authentication(user);
        if (findUser == null) {
            return new SuccessResultModel(false);
        }
        return new SuccessResultModel(true);
    }

    public GuidResultModel addUser(UserCreateModel userModel) {
        var user = userRepository.addNewUser(userModel);
        return new GuidResultModel(user);
    }

    public StringResultModel updatePassword(String email, String password) {
        var find = getUserEmail(email);
        if (find == null) {
            return new StringResultModel("NullException", "почта не нашлась");
        }
        return new StringResultModel(userRepository.changeUserPassword(find, password));
    }

    public StringResultModel updateLogin(String email, String login) {
        var find = getUserEmail(email);
        if (find == null) {
            return new StringResultModel("NullException", "почта не нашлась");
        }
        return new StringResultModel(userRepository.changeUserLogin(find, login));
    }
}
