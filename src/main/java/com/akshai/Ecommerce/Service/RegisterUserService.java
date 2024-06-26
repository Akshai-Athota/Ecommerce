package com.akshai.Ecommerce.Service;

import com.akshai.Ecommerce.Exception.UserAlreadyExistsException;
import com.akshai.Ecommerce.Models.ProductUser;
import com.akshai.Ecommerce.Repositories.ProductUserRepository;
import com.akshai.Ecommerce.api.Models.RegistrationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterUserService {
    @Autowired
    private ProductUserRepository userRepo;

    public ProductUser registerUserDetails(RegistrationBody registeruser) throws UserAlreadyExistsException {
        if(userRepo.findByUsernameIgnoreCase(registeruser.getUsername()).isPresent() ||
                userRepo.findByEmailIgnoreCase(registeruser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        ProductUser user=new ProductUser();
        user.setUsername(registeruser.getUsername());
        user.setEmail(registeruser.getEmail());
        user.setFirstname(registeruser.getFirstname());
        user.setLastname(registeruser.getLastname());
        //TODO:encryption required
        user.setPassword(registeruser.getPassword());
        return userRepo.save(user);
    }
}
