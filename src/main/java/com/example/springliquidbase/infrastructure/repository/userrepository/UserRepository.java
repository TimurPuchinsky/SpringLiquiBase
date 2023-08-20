package com.example.springliquidbase.infrastructure.repository.userrepository;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    public UserRepository(DbModel db) {
        this.db = db;
    }

    DbModel db;
    AuthenticationManager authenticationManager;

    private UserModel getModel(UserEntity entity) {
        if (entity == null) return null;
        var model = new UserModel();
        model.setId(entity.getId());
        model.setLogin(entity.getLogin());
        model.setPassword(entity.getPassword());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setRole(entity.getRole());
        model.setCreated(entity.getCreated());
        return model;
    }

    public Collection<UserModel> getAllUsers() {
        var userlist = db.getDb().find(UserEntity.class).findList();
        return userlist.stream().map(this::getModel).collect(Collectors.toList());
    }

    public UserModel getUserByLogin(String login) {
        var user = db.getDb().find(UserEntity.class).where().eq(UserEntity.LOGIN, login).findOne();
        return getModel(user);
    }

    public String authenticate(String login, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        password
                )
        );
        return getUserByLogin(login).getLogin();
    }

    public String register(UserModel userModel) {
        return null;
    }
}
