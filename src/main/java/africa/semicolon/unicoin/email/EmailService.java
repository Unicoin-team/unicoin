package africa.semicolon.unicoin.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class EmailService implements EmailSender{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String to, String email) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "UTF-8");
        mimeMessageHelper.setSubject("Confirmed your email");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom("dej@gmail.com");
        mimeMessageHelper.setText(email, true);
    }
}
