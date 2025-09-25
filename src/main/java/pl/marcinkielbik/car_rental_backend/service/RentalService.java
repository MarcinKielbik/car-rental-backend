package pl.marcinkielbik.car_rental_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcinkielbik.car_rental_backend.model.Car;
import pl.marcinkielbik.car_rental_backend.model.CarStatus;
import pl.marcinkielbik.car_rental_backend.model.Rental;
import pl.marcinkielbik.car_rental_backend.model.RentalStatus;
import pl.marcinkielbik.car_rental_backend.model.User;
import pl.marcinkielbik.car_rental_backend.repository.CarRepository;
import pl.marcinkielbik.car_rental_backend.repository.RentalRepository;
import pl.marcinkielbik.car_rental_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    // Pobranie wszystkich wypożyczeń
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Pobranie wypożyczeń danego użytkownika
    public List<Rental> getRentalsByUser(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    // Utworzenie nowego wypożyczenia (status: PENDING)
    public Rental createRental(Long userId, Long carId,
                               java.time.LocalDate startDate,
                               java.time.LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Car is not available");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setStatus(RentalStatus.PENDING);

        // Samochód wciąż dostępny, admin musi zatwierdzić
        return rentalRepository.save(rental);
    }

    // Zatwierdzenie wypożyczenia przez admina
    public Rental approveRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setStatus(RentalStatus.APPROVED);
        Car car = rental.getCar();
        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        return rentalRepository.save(rental);
    }

    // Odrzucenie wypożyczenia przez admina
    public Rental rejectRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setStatus(RentalStatus.REJECTED);
        return rentalRepository.save(rental);
    }
}
