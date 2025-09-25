package pl.marcinkielbik.car_rental_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinkielbik.car_rental_backend.model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
}
