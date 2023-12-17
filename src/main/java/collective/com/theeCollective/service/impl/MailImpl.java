package collective.com.theeCollective.service.impl;

import collective.com.theeCollective.service.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailImpl implements Mail {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void Registration(String toEmail, String Subject, String Body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("andyirimbere@gmail.com");
        message.setTo(toEmail);
        message.setText(Body);
        message.setSubject(Subject);

        mailSender.send(message);

        System.out.println("new registration");
    }
}
