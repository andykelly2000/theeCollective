package collective.com.theeCollective.service;

import jakarta.servlet.Registration;

public interface Mail {
    public void Registration(String toEmail, String Subject, String Body);
}
