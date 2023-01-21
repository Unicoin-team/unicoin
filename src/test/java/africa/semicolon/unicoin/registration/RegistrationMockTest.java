package africa.semicolon.unicoin.registration;

import africa.semicolon.unicoin.mockUtils.MockUtils;
import africa.semicolon.unicoin.registration.dtos.RegistrationRequest;
import africa.semicolon.unicoin.user.User;
import africa.semicolon.unicoin.user.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static africa.semicolon.unicoin.mockUtils.MockUtils.emailSenderMock;
import static africa.semicolon.unicoin.mockUtils.MockUtils.userRepositoryMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class RegistrationMockTest {
    private final UserService userServiceMock = spy(MockUtils.userService());
    private final RegistrationService registrationService = new RegistrationService(
            userServiceMock,
            emailSenderMock,
            MockUtils.confirmationTokenServiceMock(),
            userRepositoryMock
    );

    @Test
    public void testRegister() throws MessagingException{
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Jonathan",
                        "Martins",
                "adulojujames",
                "part1234"
        );
         doReturn("94b199ea-614e-4a16-9f50-f94f5611bae9")
                 .when(userServiceMock).createAccount(any(User.class));

         assertEquals(registrationService.register(registrationRequest), "94b199ea-614e-4a16-9f50-f94f5611bae9");
    }

    @Test
    public void testResendToken() throws MessagingException {
        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase("12374@gmail.com");
        doReturn("85656674-1488-4d64-aca6-e78ff6d757fc")
                .when(userServiceMock).generateToken(any(String.class));
        assertEquals("Token sent!!!", registrationService.resendToken("12374@gmail.com"));
    }
}
