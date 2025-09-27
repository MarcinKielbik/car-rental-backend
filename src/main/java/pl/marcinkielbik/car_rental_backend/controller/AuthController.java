package pl.marcinkielbik.car_rental_backend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcinkielbik.car_rental_backend.dto.LoginRequest;
import pl.marcinkielbik.car_rental_backend.dto.RegisterRequest;
import pl.marcinkielbik.car_rental_backend.model.User;
import pl.marcinkielbik.car_rental_backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPassword() == null || !userService.checkPassword(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(user);
    }

}
