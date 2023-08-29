package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.usersession.UserSessionModel;
import com.example.springliquidbase.infrastructure.repository.usersessionrepository.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;
    private final UserService userService;

    public UserSessionModel getSession(UserModel userModel) {
        return userSessionRepository.createSession(userModel);
    }

    public SuccessResultModel removeSession(UUID session) {
        var delete = userSessionRepository.deleteSession(session);
        if (!delete) {
            return new SuccessResultModel("Error", "Сессия не удалилась");
        } else {
            return new SuccessResultModel(true);
        }
    }
}
