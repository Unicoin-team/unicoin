package africa.semicolon.unicoin.mockUtils;

import africa.semicolon.unicoin.email.EmailSender;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordTokenService;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenRepository;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import africa.semicolon.unicoin.user.UserRepository;
import africa.semicolon.unicoin.user.UserService;
import africa.semicolon.unicoin.user.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

public class MockUtils {
    public static final UserRepository userRepositoryMock = mock(UserRepository.class);
    public static final EmailSender emailSenderMock = mock(EmailSender.class);
    public static final PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
    public static final ResetPasswordTokenService resetPasswordServiceMock = mock(ResetPasswordTokenService.class);
    public static final ConfirmationTokenRepository tokenRepositoryMock = mock(ConfirmationTokenRepository.class);

    public static UserService userService(){
        return new UserServiceImpl(userRepositoryMock,
                confirmationTokenMock(),
                resetPasswordServiceMock,
                emailSenderMock,
                passwordEncoderMock
                );
    }

    public static ConfirmationTokenService confirmationTokenMock() {
        return new ConfirmationTokenService(tokenRepositoryMock);
    }

}
