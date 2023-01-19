package africa.semicolon.unicoin.registration;

import africa.semicolon.unicoin.mockUtils.MockUtils;
import africa.semicolon.unicoin.registration.dtos.RegistrationRequest;
import africa.semicolon.unicoin.user.User;
import africa.semicolon.unicoin.user.UserService;
import africa.semicolon.unicoin.user.UserServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

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

}
