package africa.semicolon.unicoin.user;

public interface UserService {
    public String createAccount(User user);
    void enableUser(String email);
    String generateToken(String email);
}
