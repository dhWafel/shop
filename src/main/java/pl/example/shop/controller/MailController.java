package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.example.shop.service.MailService;

import javax.mail.MessagingException;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("api/mail")
    public void sendMail() throws MessagingException {
        mailService.sendMail("JavaProgramShop@gmail.com","tytuł", "cześć");
    }
}
