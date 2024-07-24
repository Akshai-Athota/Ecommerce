package com.akshai.Ecommerce.api.Controllers.auth;

import com.akshai.Ecommerce.Exception.EmailFailureException;
import com.akshai.Ecommerce.Exception.EmailNotFoundException;
import com.akshai.Ecommerce.Exception.UserAlreadyExistsException;
import com.akshai.Ecommerce.Exception.UserNotVerifiedException;
import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Service.UserService;
import com.akshai.Ecommerce.api.Models.LoginBody;
import com.akshai.Ecommerce.api.Models.LoginResponse;
import com.akshai.Ecommerce.api.Models.PasswordResetBody;
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
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenicateUser(@RequestBody LoginBody loginBody){
        String jwt = null;
        try {
            jwt = userservice.loginUser(loginBody);
        } catch (UserNotVerifiedException ex) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (ex.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (userservice.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam String email){
        try{
        userservice.forgotPassword(email);
        return ResponseEntity.ok().build();
        }catch (EmailNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetBody body){
        userservice.resetPassword(body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public LocalUser getLoaclUser(@AuthenticationPrincipal LocalUser user){
        return user;
    }
}
