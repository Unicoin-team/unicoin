package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.registration.dtos.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dtos.ForgotPasswordRequest;
import africa.semicolon.unicoin.registration.dtos.ResetPasswordRequest;
import jakarta.mail.MessagingException;

public interface UserService {
    public String createAccount(User user);
    void enableUser(String email);
    String generateToken(String email);

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;

    String resetPassword(ResetPasswordRequest resetPasswordRequest);

    String confirmResetPasswordToken(ConfirmTokenRequest confirmTokenRequest);
}
