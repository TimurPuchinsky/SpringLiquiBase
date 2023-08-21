package com.example.springliquidbase.infrastructure.repository.userrepository;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

    private UserModel getModel(UserEntity e) {
        if (e == null) return null;
        var model = new UserModel();
        model.setId(e.getId());
        model.setLogin(e.getLogin());
        model.setPassword(e.getPassword());
        model.setEmail(e.getEmail());
        model.setPhone(e.getPhone());
        model.setRole(e.getRole());
        model.setCreated(e.getCreated());
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

    public String authentication(String login, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login,
                            password
                    )
            );
            return getUserByLogin(login).getLogin();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверный логин или пароль");
        }
    }

    public String register(UserModel userModel) {
        return null;
    }
}
