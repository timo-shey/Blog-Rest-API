package fashionblogrestapi.controller;

import fashionblogrestapi.pojo.LoginDto;
import fashionblogrestapi.pojo.LoginResponse;
import fashionblogrestapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginDto userDto){
        LoginResponse user = userService.loginUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
