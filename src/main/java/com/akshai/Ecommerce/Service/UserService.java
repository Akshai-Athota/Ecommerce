package com.akshai.Ecommerce.Service;

import com.akshai.Ecommerce.Exception.UserAlreadyExistsException;
import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Repositories.LocalUserRepository;
import com.akshai.Ecommerce.api.Models.LoginBody;
import com.akshai.Ecommerce.api.Models.RegistrationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private LocalUserRepository userRepo;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JWTService jwtService;

    public LocalUser registerUserDetails(RegistrationBody registeruser) throws UserAlreadyExistsException {
        if(userRepo.findByUsernameIgnoreCase(registeruser.getUsername()).isPresent() ||
                userRepo.findByEmailIgnoreCase(registeruser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        LocalUser user=new LocalUser();
        user.setUsername(registeruser.getUsername());
        user.setEmail(registeruser.getEmail());
        user.setFirstName(registeruser.getFirstname());
        user.setLastName(registeruser.getLastname());
        user.setPassword(encryptionService.encryptPassword(registeruser.getPassword()));
        return userRepo.save(user);
    }

    public String authenticateUser(LoginBody loginbody){
        Optional<LocalUser> opUser=userRepo.findByUsernameIgnoreCase(loginbody.getUsername());
        if(opUser.isPresent()){
            LocalUser user=opUser.get();
            if(encryptionService.verifyPassword(loginbody.getPassword(), user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
