package com.akshai.Ecommerce.Service;

import com.akshai.Ecommerce.Exception.EmailFailureException;
import com.akshai.Ecommerce.Exception.UserAlreadyExistsException;
import com.akshai.Ecommerce.Exception.UserNotVerifiedException;
import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Models.VerificationToken;
import com.akshai.Ecommerce.Repositories.LocalUserRepository;
import com.akshai.Ecommerce.Repositories.VerificationTokenRepository;
import com.akshai.Ecommerce.api.Models.LoginBody;
import com.akshai.Ecommerce.api.Models.RegistrationBody;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private LocalUserRepository userRepo;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    public LocalUser registerUserDetails(RegistrationBody registeruser) throws UserAlreadyExistsException, EmailFailureException {
        if(userRepo.findByUsernameIgnoreCase(registeruser.getUsername()).isPresent() ||
                userRepo.findByEmailIgnoreCase(registeruser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        LocalUser user=new LocalUser();
        user.setUsername(registeruser.getUsername());
        user.setEmail(registeruser.getEmail());
        user.setFirstName(registeruser.getFirstName());
        user.setLastName(registeruser.getLastName());
        user.setPassword(encryptionService.encryptPassword(registeruser.getPassword()));
        VerificationToken token=createVerifactionToken(user);
        emailService.sendVerificationEmail(token);
        verificationTokenRepository.save(token);
        return userRepo.save(user);
    }

    public VerificationToken createVerifactionToken(LocalUser user){
        VerificationToken token=new VerificationToken();
        token.setToken(jwtService.generateVerificationTokenJWT(user));
        token.setCreatedtime(new Timestamp(System.currentTimeMillis()));
        token.setUser(user);
        user.getVerificationTokens().add(token);
        return token;
    }

    public String loginUser(LoginBody loginbody) throws EmailFailureException, UserNotVerifiedException {
        Optional<LocalUser> opUser=userRepo.findByUsernameIgnoreCase(loginbody.getUsername());
        if(opUser.isPresent()){
            LocalUser user=opUser.get();
            if(encryptionService.verifyPassword(loginbody.getPassword(), user.getPassword())){
                if(user.getEmailVerified()){
                    return jwtService.generateJWT(user);
                }
                else{
                    List<VerificationToken> tokenList = verificationTokenRepository.findAll();
                    boolean resend = tokenList.size() ==0 || tokenList.get(0).getCreatedtime().before(new Timestamp(60*60*1000));
                    if(resend){
                        VerificationToken newtoken= createVerifactionToken(user);
                        emailService.sendVerificationEmail(newtoken);
                        verificationTokenRepository.save(newtoken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if(opToken.isPresent()){
            VerificationToken curToken=opToken.get();
            LocalUser user=curToken.getUser();
            if(!user.getEmailVerified()){
                user.setEmailVerified(true);
                userRepo.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
