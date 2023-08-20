package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domainservice.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping(name = "/users")
    private Collection<UserModel> users(){
        return userService.getAll();
    }

    @PostMapping(name = "/register")
    public String register(@RequestBody UserModel userModel) {
        return userService.register(userModel);
    }

    @PostMapping(name = "/authenticate")
    public String authenticate(@RequestParam String login, @RequestParam String password) {
        return userService.authenticate(login, password);
    }
}
