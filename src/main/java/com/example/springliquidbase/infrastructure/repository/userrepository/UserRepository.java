package com.example.springliquidbase.infrastructure.repository.userrepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.user.UserAuthenticateModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
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

    public UserModel getUserByEmail(String email) {
        var user = db.getDb().find(UserEntity.class).where().eq(UserEntity.EMAIL, email).findOne();
        return getModel(user);
    }

    public UserModel authentication(UserAuthenticateModel userModel) {
        var userEntity = db.getDb().find(UserEntity.class).where().eq(UserEntity.LOGIN, userModel.getLogin())
                .and().eq(UserEntity.PASSWORD, userModel.getPassword()).findOne();
        return getModel(userEntity);
    }

    public UUID addNewUser(UserCreateModel userModel) {
        var user = getEntity(userModel);
        db.getDb().insert(user);
        return user.getId();
    }

    public String changeUserPassword(UserModel userModel, String password) {
         db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.EMAIL, userModel.getEmail())
                .asUpdate()
                .set(UserEntity.PASSWORD, password)
                .set(UserEntity.CHANGED, LocalDateTime.now())
                .update();
        return password;
    }

    public String changeUserLogin(UserModel userModel, String login) {
        db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.EMAIL, userModel.getEmail())
                .asUpdate()
                .set(UserEntity.LOGIN, login)
                .set(UserEntity.CHANGED, LocalDateTime.now())
                .update();
        return login;
    }
}
