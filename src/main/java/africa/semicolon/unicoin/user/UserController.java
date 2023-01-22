package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.Utils.ApiResponse;
import africa.semicolon.unicoin.registration.dtos.PasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @DeleteMapping("delete/{email}")
    public ResponseEntity<?> deleteToken(@PathVariable String email, @RequestBody PasswordRequest passwordRequest, HttpServletRequest httpServletRequest){
        String confirmToken = userService.deleteAccountByEmail(email, passwordRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK)
                .data(confirmToken)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
