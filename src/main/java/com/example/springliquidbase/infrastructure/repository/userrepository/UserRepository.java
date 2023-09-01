package com.example.springliquidbase.infrastructure.repository.userrepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.domain.user.role.Role;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import com.example.springliquidbase.PasswordEncoder;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class UserRepository {


    public UserRepository(DbModel db, PasswordEncoder passwordEncoder) {
        this.db = db;
        this.passwordEncoder = passwordEncoder;
    }

    DbModel db;
    PasswordEncoder passwordEncoder;

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
        model.setArchived(e.getArchived());
        return model;
    }

    private UserEntity getEntity(UserCreateModel model) {
        var entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setLogin(model.getLogin());
        entity.setPassword(passwordEncoder.encode(model.getPassword()));
        entity.setEmail(model.getEmail());
        entity.setSurname(model.getSurname());
        entity.setName(model.getName());
        entity.setFather(model.getFather());
        entity.setPhone(model.getPhone());
        entity.setRole(Role.USER.name());
        entity.setCreated(LocalDateTime.now());
        entity.setChanged(LocalDateTime.now());
        entity.setArchived(LocalDateTime.now());
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
        if (StringUtils.isNotBlank(model.getLogin())) {
            exp = exp.ilike(UserEntity.LOGIN, "%" + model.getLogin() + "%");
        }
        if (StringUtils.isNotBlank(model.getPassword())) {
            exp = exp.ilike(UserEntity.PASSWORD, "%" + model.getPassword() + "%");
        }
        if (StringUtils.isNotBlank(model.getEmail())) {
            exp = exp.ilike(UserEntity.EMAIL, "%" + model.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(model.getSurname())) {
            exp = exp.ilike(UserEntity.SURNAME, "%" + model.getSurname() + "%");
        }
        if (StringUtils.isNotBlank(model.getName())) {
            exp = exp.ilike(UserEntity.NAME, "%" + model.getName() + "%");
        }
        if (StringUtils.isNotBlank(model.getFather())) {
            exp = exp.ilike(UserEntity.FATHER, "%" + model.getFather() + "%");
        }
        if (StringUtils.isNotBlank(model.getPhone())) {
            exp = exp.ilike(UserEntity.PHONE, "%" + model.getPhone() + "%");
        }
        if (StringUtils.isNotBlank(model.getRole())) {
            exp = exp.ilike(UserEntity.ROLE, "%" + model.getRole() + "%");
        }
        return exp;
    }

    public UserModel findUserById(UUID id) {
        var user = db.getDb().find(UserEntity.class).where().eq(UserEntity.ID, id).findOne();
        return getModel(user);
    }

    public UserModel findUserByLogin(String login) {
        var user = db.getDb().find(UserEntity.class).where().eq(UserEntity.LOGIN, login).findOne();
        return getModel(user);
    }

    public UserModel findUserByEmail(String email) {
        var user = db.getDb().find(UserEntity.class).where().eq(UserEntity.EMAIL, email).findOne();
        return getModel(user);
    }

    public boolean authentication(String password, String encode) {
        return passwordEncoder.matches(password, encode);
    }

    public UserModel addNewUser(UserCreateModel userModel) {
        var user = getEntity(userModel);
        db.getDb().insert(user);
        return getModel(user);
    }

    public UUID changeUserPassword(UserModel userModel, String password) {
         db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.EMAIL, userModel.getEmail())
                .asUpdate()
                .set(UserEntity.PASSWORD, passwordEncoder.encode(password))
                .set(UserEntity.CHANGED, LocalDateTime.now())
                .update();
        return userModel.getId();
    }

    public UUID changeUserLogin(UserModel userModel, String login) {
        db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.EMAIL, userModel.getEmail())
                .asUpdate()
                .set(UserEntity.LOGIN, login)
                .set(UserEntity.CHANGED, LocalDateTime.now())
                .update();
        return userModel.getId();
    }

    public UUID userArchive(UserModel userModel) {
         db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.ID, userModel.getId())
                .asUpdate()
                .set(UserEntity.ARCHIVED, LocalDateTime.now())
                .update();
         return userModel.getId();
    }

    public UUID userUnArchive(UserModel userModel) {
        db.getDb().find(UserEntity.class).where()
                .eq(UserEntity.ID, userModel.getId())
                .asUpdate()
                .set(UserEntity.ARCHIVED, null)
                .update();
        return userModel.getId();
    }

    public Map<UUID, UserModel> findUsersListById(List<UUID> authorIds) {
        return db.getDb().find(UserEntity.class)
                .where()
                .in(UserEntity.ID, authorIds)
                .query().findStream()
                .map(this::getModel)
                .collect(Collectors.toMap(UserModel::getId, Function.identity()));
    }
}
