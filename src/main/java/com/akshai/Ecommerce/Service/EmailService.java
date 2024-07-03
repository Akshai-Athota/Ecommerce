package com.akshai.Ecommerce.Service;

import com.akshai.Ecommerce.Exception.EmailFailureException;
import com.akshai.Ecommerce.Models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${email.from}")
    private String fromAddress;

    @Value("${app.frontend.url}")
    private String serverUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setSubject("Verify your email to active your account.");
        return msg;
    }

    public void sendVerificationEmail(VerificationToken token) throws EmailFailureException{
        SimpleMailMessage msg=makeMailMessage();
        msg.setTo(token.getUser().getEmail());
        msg.setText("Please follow the link below to verify your email to active your account.\n" +
                serverUrl + "/auth/verify?token=" + token.getToken());
        try {
            javaMailSender.send(msg);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }
}
