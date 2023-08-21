package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.*;
import com.example.springliquidbase.domain.user.UserAuthenticateModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.domainservice.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping("/register")
    private GuidResultModel register(UserCreateModel userModel) {
        return userService.addUser(userModel);
    }

    @PostMapping("/changeLogin")
    private StringResultModel changeLogin(@RequestParam String email, @RequestParam String newLogin) {
        return userService.updateLogin(email, newLogin);
    }

    @PostMapping("/changePassword")
    private StringResultModel changePassword(@RequestParam String email, @RequestParam String password) {
        return userService.updatePassword(email, password);
    }

    @GetMapping("/getPage")
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
    public SuccessResultModel login(UserAuthenticateModel user) {
        return userService.authenticateUser(user);
    }

    @GetMapping("/logout")
    public String logout() {
        return "выход";
    }
}
