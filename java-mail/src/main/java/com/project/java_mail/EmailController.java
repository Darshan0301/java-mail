package com.project.java_mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {
    private final JavaMailSender javaMailSender;
    public EmailController(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @RequestMapping("/send-mail")
    public String sendMail(){
        try {
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom("darshanlakadecdac@gmail.com");
            simpleMailMessage.setTo("darshanlakdepc03@gmail.com");
            simpleMailMessage.setSubject("Mail from Me");
            simpleMailMessage.setText("When will TCS send joining letter ?");
            javaMailSender.send(simpleMailMessage);


        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Success";
    }

    @RequestMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(){
        try {

            MimeMessage mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom("darshanlakadecdac@gmail.com");
            mimeMessageHelper.setTo("darshanlakdepc03@gmail.com");
            mimeMessageHelper.setSubject("Mail from Me with Attachment");
            mimeMessageHelper.setText("Mail with Attachment");
            mimeMessageHelper.addAttachment(
                    "Attachment",
                    new File("\\templates\\daniel-olah.jpg")
            );
            javaMailSender.send(mimeMessage);


        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Success";
    }

    @RequestMapping("/send-html-mail")
    public String sendMailWithHtml(){
        try {

            MimeMessage mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom("darshanlakadecdac@gmail.com");
            mimeMessageHelper.setTo("darshanlakdepc03@gmail.com");
            mimeMessageHelper.setSubject("Mail from Me with Attachment");
            try(InputStream inputStream= Objects.requireNonNull(EmailController.class.getResourceAsStream(
                    "/templates/email-content.html"))){
                mimeMessageHelper.setText(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),true);
                mimeMessageHelper.addInline("logo.png",
                        new File("src/main/resources/templates/darshanlakade.png"));

            }
            mimeMessageHelper.addAttachment(
                    "Attachment",
                    new File("src/main/resources/templates/daniel-olah.jpg")
            );
            javaMailSender.send(mimeMessage);


        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Success HTML";
    }
}
