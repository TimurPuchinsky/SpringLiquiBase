package com.example.springliquidbase.infrastructure.repository.userrepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.security.AuthenticationResponseModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.UserPageModel;
//import com.example.springliquidbase.jwt.JwtService;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import com.example.springliquidbase.jwt.JwtService;
import io.ebean.ExpressionList;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    public UserRepository(DbModel db) {
        this.db = db;
    }

    DbModel db;
    AuthenticationManager authenticationManager;
    JwtService jwtService;

    private UserModel getModel(UserEntity e) {
        if (e == null) return null;
        var model = new UserModel();
        model.setId(e.getId());
        model.setLogin(e.getLogin());
        model.setPassword(e.getPassword());
        model.setEmail(e.getEmail());
        model.setSurname(e.getSurname());
        model.setName(e.getName());
        model.setFather(e.getFather());
        model.setPhone(e.getPhone());
        model.setRole(e.getRole());
        model.setCreated(e.getCreated());
        model.setChanged(e.getChanged());
        return model;
    }

    private UserEntity getEntity(UserCreateModel model) {
        var entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setLogin(model.getLogin());
        entity.setPassword(model.getPassword());
        entity.setEmail(model.getEmail());
        entity.setSurname(model.getSurname());
        entity.setName(model.getName());
        entity.setFather(model.getFather());
        entity.setPhone(model.getPhone());
        entity.setRole("ROLE_USER");
        entity.setCreated(LocalDateTime.now());
        entity.setChanged(LocalDateTime.now());
        return entity;
    }

    public PageResultModel getPage(UserPageModel model) {
        var exp = db.getDb().find(UserEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).where();

        exp = applyFilters(exp, model);

        var pagedList = exp.findPagedList();
        List<UserModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());
        return new PageResultModel(pagedList.getTotalCount(), models);
    }

    private ExpressionList<UserEntity> applyFilters(ExpressionList<UserEntity> exp, UserPageModel model) {
        if (StringUtils.isNotBlank(model.getNameFilter())) {
            exp = exp.ilike(UserEntity.NAME, "%" + model.getNameFilter() + "%");
        }
        return exp;
    }

    public UserModel getUserByLogin(String login) {
        var user = db.getDb().find(UserEntity.class).where().eq(UserEntity.LOGIN, login).findOne();
        return getModel(user);
    }

    public AuthenticationResponseModel authentication(String login, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password));

            var user = getUserByLogin(login);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponseModel.builder()
                    .token(jwtToken)
                    .build();
            //return getUserByLogin(login).getLogin();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверный логин или пароль");
        }
    }

    public void addNewUser(UserCreateModel userModel) {
        var user = getEntity(userModel);
        db.getDb().insert(user);
    }

    public boolean changeUserDetails(UserCreateModel userModel) {
        var user = db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.EMAIL, userModel.getEmail()).or()
                .eq(UserEntity.PHONE, userModel.getPhone());
        return false;
    }

//    public boolean logout(HttpServletRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        request.getSession().invalidate();
//        return
//    }
}
