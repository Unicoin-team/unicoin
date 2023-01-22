package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.registration.dtos.PasswordRequest;

public interface UserService {
    public String createAccount(User user);
    void enableUser(String email);
    String generateToken(String email);

    User getUserByEmailAddress(String email);

    String deleteAccountByEmail(String email, PasswordRequest passwordRequest);
}
