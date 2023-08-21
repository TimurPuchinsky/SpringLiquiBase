package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.security.AuthenticationResponseModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.infrastructure.repository.userrepository.UserRepository;
import com.example.springliquidbase.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public PageResultModel getAll(UserPageModel userPageModel) {
        return userRepository.getPage(userPageModel);
    }

    public UserModel getUser(String login) {
        return  userRepository.getUserByLogin(login);
    }

    public AuthenticationResponseModel authenticateUser(String login, String password) {
        return userRepository.authentication(login, password);
    }

    public AuthenticationResponseModel addUser(UserCreateModel userModel) {
        userRepository.addNewUser(userModel);
        var jwtToken = jwtService.generateToken(getUser(userModel.getLogin()));
        return AuthenticationResponseModel.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseModel updateUser(UserCreateModel userModel) {
        userRepository.changeUserDetails(userModel);
        var jwtToken = jwtService.generateToken(getUser(userModel.getLogin()));
        return  AuthenticationResponseModel.builder()
                .token(jwtToken)
                .build();
    }

//    public SuccessResultModel logoutUser(HttpServletRequest request) {
//        var logout = userRepository.logout(request);
//        return new SuccessResultModel();
//    }
}
