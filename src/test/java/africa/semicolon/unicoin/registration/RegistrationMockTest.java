package africa.semicolon.unicoin.registration;

import africa.semicolon.unicoin.exception.RegistrationException;
import africa.semicolon.unicoin.mockUtils.MockUtils;
import africa.semicolon.unicoin.registration.dtos.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dtos.RegistrationRequest;
import africa.semicolon.unicoin.registration.dtos.ResendTokenRequest;
import africa.semicolon.unicoin.registration.token.ConfirmationToken;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import africa.semicolon.unicoin.user.User;
import africa.semicolon.unicoin.user.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static africa.semicolon.unicoin.mockUtils.MockUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class RegistrationMockTest {
    private final UserService userServiceMock = spy(MockUtils.userService());
    private final ConfirmationTokenService confirmationTokenServiceMock = spy(MockUtils.confirmationTokenMock());
    private final RegistrationService registrationService = new RegistrationService(
            userServiceMock,
            emailSenderMock,
            MockUtils.confirmationTokenMock(),
            userRepositoryMock
    );

    @Test public void testRegister() throws MessagingException{
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Jonathan",
                "Martins",
                "test@testing.com.ng",
                "12345"
        );
         doReturn("94b199ea-614e-4a16-9f50-f94f5611bae9")
                 .when(userServiceMock).createAccount(any(User.class));

         assertEquals("94b199ea-614e-4a16-9f50-f94f5611bae9", registrationService.register(registrationRequest));
    }

    @Test public void testResendToken() throws MessagingException {
        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase("test@testing.com");
        doReturn("85656674-1488-4d64-aca6-e78ff6d757fc")
                .when(userServiceMock).generateToken(any(String.class));
        //        assertEquals("Token sent!!!", registrationService.resendToken("12374@gmail.com"));
        ResendTokenRequest tokenRequest = new ResendTokenRequest();
        tokenRequest.setEmailAddress("test@testing.com");
        assertEquals("Token sent!!!", registrationService.resendToken(tokenRequest));

    }

    @Test void testConfirmToken(){
        ConfirmTokenRequest tokenRequest = new ConfirmTokenRequest();
        tokenRequest.setEmail("test@testing.com");
        tokenRequest.setToken("85656674-1488-4d64-aca6-e78ff6d757fc");

        Optional<ConfirmationToken> confirmationToken = Optional.of(new ConfirmationToken());
        confirmationToken.get().setToken(tokenRequest.getToken());
        confirmationToken.get().setCreatedAt(LocalDateTime.now());
        confirmationToken.get().setExpiredAt(LocalDateTime.now().plusMinutes(10));
        doReturn(confirmationToken).when(tokenRepositoryMock).findByToken(tokenRequest.getToken());

        assertEquals("confirmed", registrationService.confirmToken(tokenRequest));
    }

    @Test void testConfirmTokenThrowsException(){
        ConfirmTokenRequest tokenRequest = new ConfirmTokenRequest();
        tokenRequest.setEmail("test@testing.com");
        tokenRequest.setToken("85656674-1488-4d64-aca6-e78ff6d757fc");

        Optional<ConfirmationToken> confirmationToken = Optional.of(new ConfirmationToken());
        confirmationToken.get().setToken(tokenRequest.getToken());
        confirmationToken.get().setCreatedAt(LocalDateTime.now());
        confirmationToken.get().setExpiredAt(LocalDateTime.now().plusSeconds(0));
        doReturn(confirmationToken).when(tokenRepositoryMock).findByToken(tokenRequest.getToken());

        assertThrows(RegistrationException.class, ()-> registrationService.confirmToken(tokenRequest));
    }
}
