package com.example.springliquidbase.infrastructure.repository.usersessionrepository;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.usersession.UserSessionModel;
import com.example.springliquidbase.JwtGenerator;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class UserSessionRepository {

    public UserSessionRepository(DbModel db, JwtGenerator jwtService) {
        this.db = db;
        this.jwtService = jwtService;
    }

    DbModel db;
    JwtGenerator jwtService;

    private UserSessionModel getModel(UserSessionEntity e) {
        if (e == null) return null;
        var model = new UserSessionModel();
        model.setSession_id(e.getSession_id());
        model.setAccess_token(e.getAccess_token());
        model.setAccess_token_expired(e.getAccess_token_expired());
        model.setRefresh_token(e.getRefresh_token());
        model.setRefresh_token_expired(e.getRefresh_token_expired());
        model.setUser_id(e.getUser_id());
        model.setCreated(e.getCreated());
        return model;
    }

    private UserSessionEntity getEntity(UserModel model) {
        var entity = new UserSessionEntity();
        entity.setSession_id(UUID.randomUUID());
        entity.setAccess_token(jwtService.generateAccessToken(model, entity.getSession_id()));
        entity.setAccess_token_expired(LocalDateTime.now().plusMinutes(2));
        entity.setRefresh_token(jwtService.generateRefreshToken(model, entity.getSession_id()));
        entity.setRefresh_token_expired(LocalDateTime.now().plusDays(1));
        entity.setUser_id(model.getId());
        entity.setCreated(LocalDateTime.now());
        return entity;
    }

    public UserSessionModel createSession(UserModel userModel) {
        var userSessionEntity = getEntity(userModel);
        db.getDb().insert(userSessionEntity);
        return getModel(userSessionEntity);
    }

    public boolean deleteSession(String access_token) {
        var delete = db.getDb().find(UserSessionEntity.class)
                .where().eq(UserSessionEntity.ACCESS_TOKEN, access_token).delete();
        return delete == 1;
    }
}
