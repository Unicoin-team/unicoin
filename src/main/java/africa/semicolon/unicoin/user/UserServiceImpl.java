package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.exception.GenericHandler;
import africa.semicolon.unicoin.registration.dtos.PasswordRequest;
import africa.semicolon.unicoin.registration.token.ConfirmationToken;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

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
    public User getUserByEmailAddress(String email) {
        return userRepository.findByEmailAddressIgnoreCase(email).orElseThrow(() -> new RegistrationException("User with " + email + " does not exist"));
    }

    @Override
    public String deleteAccountByEmail(String email, PasswordRequest passwordRequest) {
        String token = UUID.randomUUID().toString();
        String tokenEncrypt = passwordEncoder.encode(token);
        User user = getUserByEmailAddress(email);
        if(!user.getPassword().equals(passwordRequest.getPassword())) throw new GenericHandlerException("Invalid password");
        String userEmail = user.getEmailAddress();
        String deletedEmail = "Deleted" +  userEmail + tokenEncrypt;
        user.setEmailAddress(deletedEmail);
        userRepository.save(user);
        return "Account delete successfully";
    }
}
