package com.akshai.Ecommerce.Service;

import com.akshai.Ecommerce.Exception.UserAlreadyExistsException;
import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Repositories.LocalUserRepository;
import com.akshai.Ecommerce.api.Models.RegistrationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {
    @Autowired
    private LocalUserRepository userRepo;

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
        //TODO:encryption required
        user.setPassword(registeruser.getPassword());
        return userRepo.save(user);
    }
}
