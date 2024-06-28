package com.akshai.Ecommerce.api.Controllers.auth;

import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Service.UserService;
import com.akshai.Ecommerce.api.Models.LoginBody;
import com.akshai.Ecommerce.api.Models.LoginResponse;
import com.akshai.Ecommerce.api.Models.RegistrationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userservice;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registeruser){
        try {
            userservice.registerUserDetails(registeruser);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenicateUser(@RequestBody LoginBody loginbody){
        String jwt=userservice.authenticateUser(loginbody);
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginResponse loginresponse=new LoginResponse();
        loginresponse.setJwt(jwt);
        return ResponseEntity.ok(loginresponse);
    }

    @GetMapping("/me")
    public LocalUser getLoaclUser(@AuthenticationPrincipal LocalUser user){
        return user;
    }
}
