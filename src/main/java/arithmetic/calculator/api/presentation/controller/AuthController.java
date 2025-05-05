package arithmetic.calculator.api.presentation.controller;

import arithmetic.calculator.api.presentation.dto.RegisterUserDTO;
import arithmetic.calculator.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping(path = "register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO request) {
        return ResponseEntity.ok(this.userService.registerNewUser(request));
    }

    @PostMapping(path = "login")
    public ResponseEntity<String> loginUser() {
        return ResponseEntity.ok("User logged");
    }
}
