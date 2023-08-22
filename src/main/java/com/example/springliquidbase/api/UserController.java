package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.*;
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
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

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

    @PostMapping("/getPage")
    private PageResultModel getPage(UserPageModel userPageModel){
        return userService.getAll(userPageModel);
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PermitAll
//    @PostMapping("/register")
//    public String register(@RequestBody UserModel userModel) {
//        return userService.register(userModel);
//    }

    @GetMapping("/login")
    public LoginResultModel login(UserAuthenticateModel user) {
        return userService.authenticateUser(user);
    }

    @GetMapping("/logout")
    public String logout() {
        return "выход";
    }

    @PutMapping("/archive")
    @Operation(summary = "архивирование/разархивирование")
    public SuccessResultModel archive(@RequestParam UUID id) {
        return userService.archiveUser(id);
    }
}
