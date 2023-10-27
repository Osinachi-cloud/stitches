package com.stitches.utils;

import jakarta.mail.internet.MimeMessage;
import org.h2.command.dml.Help;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Service
public class Helper {


    private final JavaMailSender mailSender;

    public Helper(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

//    public boolean sendMail(String email, String data,String subject, String EmailtemplateName) throws IOException, TemplateException {
//
////        Template template = configuration.getTemplate("email.ftl");
////        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<>());
//
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_NO,
//                    StandardCharsets.UTF_8.name());
//            helper.setTo(email);
//            helper.setFrom("noreply@subsidian.net");
//            helper.setSubject(subject);
//            helper.setText(data, false);
//            mailSender.send(message);
//            return true;
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//
//    }

    public boolean sendMail(String email, String data,String subject) throws IOException {


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_NO,
                    StandardCharsets.UTF_8.name());
            helper.setTo(email);
            helper.setFrom("ogbodouchennatest@gmail.com");
            helper.setSubject(subject);
            helper.setText(data, false);
            mailSender.send(message);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(999999);
        return String.format("%06d", number);

    }

}


