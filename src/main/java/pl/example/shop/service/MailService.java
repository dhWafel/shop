package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.example.shop.repository.TemplateRepository;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private TemplateRepository templateRepository;

    public void sendMail(String to, String title, String content) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setReplyTo("JavaProgramShop@gmail.com");
        helper.setFrom("JavaProgramShop@gmail.com");
        helper.setSubject(title);

        Context context= new Context();

        String process = templateEngine.process("greeting.html", context);

        helper.setText(process);


        javaMailSender.send(mail);
    }


}
