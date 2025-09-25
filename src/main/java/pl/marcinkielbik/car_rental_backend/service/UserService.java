package pl.marcinkielbik.car_rental_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.marcinkielbik.car_rental_backend.model.Role;
import pl.marcinkielbik.car_rental_backend.model.User;
import pl.marcinkielbik.car_rental_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Rejestracja nowego użytkownika
    public User registerUser(String firstName, String lastName, String email, String rawPassword) {
        // Sprawdzenie czy użytkownik już istnieje
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Użytkownik z tym e-mailem już istnieje");
        }

        // Utworzenie nowego użytkownika
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRole(Role.CLIENT); // domyślna rola

        return userRepository.save(newUser);
    }

    // Pobranie użytkownika po emailu
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Aktualizacja danych użytkownika (bez zmiany roli)
    public User updateUser(Long userId, String firstName, String lastName, String rawPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (rawPassword != null && !rawPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        return userRepository.save(user);
    }
}
