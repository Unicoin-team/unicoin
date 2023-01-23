package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.email.EmailSender;
import africa.semicolon.unicoin.exception.GenericHandler;
import africa.semicolon.unicoin.registration.dtos.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dtos.ForgotPasswordRequest;
import africa.semicolon.unicoin.registration.dtos.ResetPasswordRequest;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordToken;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordTokenService;
import africa.semicolon.unicoin.registration.token.ConfirmationToken;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    private EmailSender emailSender;


    @Override
    public String createAccount(User user) {
        User savedUser = userRepository.save(user);
        return generateToken(savedUser);
    }

    private String generateToken(User savedUser) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                savedUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    @Override
    public void enableUser(String email) {
        userRepository.enable(email);
    }

    @Override
    public String generateToken(String email) {
        var foundUser = userRepository.findByEmailAddressIgnoreCase(email)
                .orElseThrow(()-> new GenericHandler(String.format("%s does not exist in User Service!!", email)));
        return generateToken(foundUser);
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        User forgotUser = userRepository.findByEmailAddressIgnoreCase(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new IllegalStateException("This email is not registered"));
        String token = UUID.randomUUID().toString();

        ResetPasswordToken resetPasswordToken =
                new ResetPasswordToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), forgotUser);
        resetPasswordTokenService.saveResetPasswordToken(resetPasswordToken);
        emailSender.sendEmail(forgotPasswordRequest.getEmail(), token);

        return "We have sent a reset password link to your email. Please check.";
    }

    public String confirmResetPasswordToken(ConfirmTokenRequest confirmTokenRequest) {
        ResetPasswordToken resetPasswordToken =
                resetPasswordTokenService.getResetPasswordToken(confirmTokenRequest.getToken())
                        .orElseThrow(() -> new RuntimeException("Invalid token | Token doesn't exist"));

        if(resetPasswordToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token has expired");

        if(resetPasswordToken.getConfirmedAt() != null)
            throw new RuntimeException("Token has been used");

//        resetPasswordTokenService.setConfirmedAt(confirmTokenRequest.getToken());
        return "Confirmed";
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByEmailAddressIgnoreCase(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new IllegalStateException("This email cannot be found"));
        user.setPassword(resetPasswordRequest.getNewPassword());
        userRepository.save(user);
        return "Your password has been changed successfully";
    }


}
