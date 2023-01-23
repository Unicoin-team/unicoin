package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.registration.dtos.*;
import jakarta.mail.MessagingException;


public interface UserService {
    public String createAccount(User user);
    void enableUser(String email);
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    String generateToken(String email);

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;

    String confirmResetPasswordToken(ConfirmTokenRequest confirmTokenRequest);
    User getUserByEmailAddress(String email);

    String login(LoginRequest loginRequest);

    String deleteAccountByEmail(String email, PasswordRequest passwordRequest);
}
