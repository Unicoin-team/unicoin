package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.registration.dtos.PasswordRequest;
import africa.semicolon.unicoin.registration.dtos.ResetPasswordRequest;

public interface UserService {
    public String createAccount(User user);
    void enableUser(String email);

    String resetPassword(ResetPasswordRequest resetPasswordRequest);

    String generateToken(String email);

    User getUserByEmailAddress(String email);

    String deleteAccountByEmail(String email, PasswordRequest passwordRequest);
}
