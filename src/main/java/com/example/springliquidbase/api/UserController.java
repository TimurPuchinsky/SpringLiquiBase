package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.LoginResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.user.UserAuthenticateModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.domainservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@PermitAll
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PermitAll
    @PostMapping("/register")
    @Operation(summary = "регистрация")
    private GuidResultModel register(UserCreateModel userModel) {
        return userService.addUser(userModel);
    }

    @PostMapping("/changeLogin")
    @Operation(summary = "поменять логин")
    private GuidResultModel changeLogin(@RequestParam UUID id, @RequestParam String newLogin) {
        return userService.updateLogin(id, newLogin);
    }

    @PostMapping("/changePassword")
    @Operation(summary = "поменять пароль")
    private GuidResultModel changePassword(@RequestParam UUID id, @RequestParam String password) {
        return userService.updatePassword(id, password);
    }

    @PermitAll
    @PostMapping("/getPage")
    public PageResultModel getPage(UserPageModel userPageModel){
        return userService.getAll(userPageModel);
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/login")
    public LoginResultModel login(UserAuthenticateModel user) {
        return userService.authenticateUser(user);
    }

    @PermitAll
    @PostMapping("/logout")
    public String logout(@RequestParam String access_token) {
        return userService.logout(access_token);
    }

    @PutMapping("/archive")
    @Operation(summary = "архивирование/разархивирование")
    public SuccessResultModel archive(@RequestParam UUID id) {
        return userService.archiveUser(id);
    }
}
