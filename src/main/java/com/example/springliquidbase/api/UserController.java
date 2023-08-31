package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.LoginResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.user.UserAuthenticateModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.domainservice.CommonUtils;
import com.example.springliquidbase.domainservice.ExportService;
import com.example.springliquidbase.domainservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@PermitAll
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping("/register")
    @Operation(summary = "регистрация")
    public GuidResultModel register(@RequestBody UserCreateModel userModel) {
        return userService.addUser(userModel);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/changeLogin")
    @Operation(summary = "поменять логин")
    public GuidResultModel changeLogin(@RequestParam UUID id, @RequestParam String newLogin) {
        return userService.updateLogin(id, newLogin);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/changePassword")
    @Operation(summary = "поменять пароль")
    public GuidResultModel changePassword(@RequestParam UUID id, @RequestParam String password) {
        return userService.updatePassword(id, password);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/getPage")
    public PageResultModel getPage(@RequestBody UserPageModel userPageModel){
        return userService.getAll(userPageModel);
    }

    @PermitAll
    @PostMapping("/login")
    public LoginResultModel login(@RequestBody UserAuthenticateModel user) {
        return userService.authenticateUser(user);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public SuccessResultModel logout() {
        return userService.logout(CommonUtils.getSessionId());
    }

    @PermitAll
    @GetMapping("/greeting")
    public String greeting() {
        return "hello, " + CommonUtils.getSubject();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/archive")
    @Operation(summary = "архивирование/разархивирование")
    public SuccessResultModel archive() {
        return userService.archiveUser(CommonUtils.getUserId());
    }
}
