package pl.marcinkielbik.car_rental_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcinkielbik.car_rental_backend.model.Rental;
import pl.marcinkielbik.car_rental_backend.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    // Pobranie wszystkich wypożyczeń (admin)
    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    // Pobranie wypożyczeń danego użytkownika
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getRentalsByUser(userId));
    }

    // Utworzenie nowego wypożyczenia (klient)
    @PostMapping
    public ResponseEntity<Rental> createRental(
            @RequestParam Long userId,
            @RequestParam Long carId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        Rental rental = rentalService.createRental(
                userId,
                carId,
                java.time.LocalDate.parse(startDate),
                java.time.LocalDate.parse(endDate)
        );
        return ResponseEntity.ok(rental);
    }

    // Zatwierdzenie wypożyczenia (admin)
    @PutMapping("/{rentalId}/approve")
    public ResponseEntity<Rental> approveRental(@PathVariable Long rentalId) {
        return ResponseEntity.ok(rentalService.approveRental(rentalId));
    }

    // Odrzucenie wypożyczenia (admin)
    @PutMapping("/{rentalId}/reject")
    public ResponseEntity<Rental> rejectRental(@PathVariable Long rentalId) {
        return ResponseEntity.ok(rentalService.rejectRental(rentalId));
    }


}
