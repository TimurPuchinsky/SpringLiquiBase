package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.usersession.UserSessionModel;
import com.example.springliquidbase.infrastructure.repository.usersessionrepository.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public UserSessionModel getSession(UserModel userModel) {
        return userSessionRepository.createSession(userModel);
    }
}
