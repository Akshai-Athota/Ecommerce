package com.akshai.Ecommerce.api.Controllers;

import com.akshai.Ecommerce.Service.RegisterUserService;
import com.akshai.Ecommerce.api.Models.RegistrationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserRegistrationController{
    @Autowired
    private RegisterUserService registerservice;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registeruser){
        try {
            registerservice.registerUserDetails(registeruser);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
