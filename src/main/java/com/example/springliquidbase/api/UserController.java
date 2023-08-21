package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.security.AuthenticationResponseModel;
import com.example.springliquidbase.domain.user.UserCreateModel;
import com.example.springliquidbase.domain.user.UserPageModel;
import com.example.springliquidbase.domainservice.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@PermitAll
@RequestMapping("/user")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping("/register")
    private AuthenticationResponseModel register(UserCreateModel userModel) {
        return userService.addUser(userModel);
    }

    @PostMapping("/change")
    private AuthenticationResponseModel change(UserCreateModel userModel) {
        return userService.updateUser(userModel);
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

    @GetMapping("/authenticate")
    public AuthenticationResponseModel authenticate(String login, String password) {
        return userService.authenticateUser(login, password);
    }

//    @GetMapping("/logout")
//    public SuccessResultModel logout(HttpServletRequest request) {
//        return userService.logoutUser(request);
//    }
}
